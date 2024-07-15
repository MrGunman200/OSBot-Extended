package api.invoking;

import org.osbot.rs07.Bot;
import org.osbot.rs07.api.Trade;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Interactable;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.event.InteractionEvent;
import org.osbot.rs07.input.mouse.*;
import org.osbot.rs07.utility.ConditionalSleep2;

import java.util.Arrays;
import java.util.List;

public class InvokeInteractionEvent extends InteractionEvent {

    private InvokeHelper invokeHelper = null;
    private boolean useMouse = false;

    private MouseDestination mouseDestination = null;
    private Interactable interactable = null;
    private Position position = null;
    private String[] actions;

    public InvokeInteractionEvent(RS2Widget widget, String... actions) {
        super(widget, actions);
        this.interactable = widget;
        this.actions = actions;
    }

    public InvokeInteractionEvent(Entity entity, String... actions) {
        super(entity, actions);
        this.interactable = entity;
        this.actions = actions;
    }

    public InvokeInteractionEvent(Bot bot, Position position, String... actions) {
        super(bot, position, actions);
        this.position = position;
        this.actions = actions;
    }

    public InvokeInteractionEvent(MouseDestination destination, String... actions) {
        super(destination, actions);
        this.mouseDestination = destination;
        this.actions = actions;
    }

    @Override
    public void onStart() {
        super.onStart();

        parseMouseDestination(mouseDestination);
        checkBadWidget();

        if (actions == null || actions.length == 0 || getBot().isMirrorMode()) {
            useMouse = true;
        }

        if (invokeHelper == null) {
            invokeHelper = new InvokeHelper(this);
        }
    }

    @Override
    public int execute() throws InterruptedException {
        try {
            if (useMouse) {
                return super.execute();
            } else if (getBot().getScriptExecutor().isPaused() || !getBot().getScriptExecutor().isRunning()) {
                setFailed();
            } else {
                invoke();
            }
        } catch (Exception | Error e) {
            setFailed();
        }

        return 0;
    }

    private void invoke() {
        final List<String> actionsAsList = Arrays.asList(actions);

        if (actionsAsList.contains("Walk here")) {
            Entity entity = null;

            if (interactable != null && interactable instanceof Entity) {
                entity = (Entity) interactable;
            }

            if (position == null && entity != null) {
                position = entity.getPosition();
            }

            if (invokeHelper.invokeWalking(position)) {
                setFinished();
                return;
            }
        }

        if (isWidgetSelected()) {
            if (actionsAsList.contains("Use") || actionsAsList.contains("Cast")) {
                if (invokeHelper.invokeOn(interactable)) {
                    setFinished();
                    ConditionalSleep2.sleep(1000, 10, ()-> !isWidgetSelected());
                    return;
                }
            }
        }

        for (String action : actions) {
            try {
                if (invokeHelper.invoke(interactable, action)) {
                    setFinished();

                    if (action.equals("Use") || action.equals("Cast")) {
                        ConditionalSleep2.sleep(1000, 10, this::isWidgetSelected);
                    }

                    return;
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }

        setFailed();
    }

    private boolean isWidgetSelected() {
        return getInventory().isItemSelected() || getMagic().isSpellSelected();
    }

    public InvokeHelper getInvokeHelper() {
        return invokeHelper;
    }

    public boolean isUseMouse() {
        return useMouse;
    }

    public InvokeInteractionEvent setInvokeHelper(InvokeHelper invokeHelper) {
        this.invokeHelper = invokeHelper;
        return this;
    }

    public InvokeInteractionEvent setUseMouse(boolean useMouse) {
        this.useMouse = useMouse;
        return this;
    }

    @Override
    public InteractionEvent setAction(String... actions) {
        this.actions = actions;
        return super.setAction(actions);
    }

    private void parseMouseDestination(MouseDestination destination) {
        if (destination == null) {
            return;
        }

        if (destination instanceof PointDestination) {
            useMouse = true;
        } else if (destination instanceof AreaDestination) {
            useMouse = true;
        } else if (destination instanceof RectangleDestination) {
            useMouse = true;
        } else if (destination instanceof InventorySlotDestination) {
            interactable = getInventory().getItemInSlot(((InventorySlotDestination) destination).getSlot());
        } else if (destination instanceof EquipmentSlotDestination) {
            if (!getEquipment().openTab()) {
                setFailed();
            }

            interactable = getEquipment().getItemInSlot(((EquipmentSlotDestination) destination).getSlot().slot);
        } else if (destination instanceof MiniMapTileDestination) {
            this.position = ((MiniMapTileDestination) destination).getPosition();
            this.actions = new String[] {"Walk here"};
        } else if (destination instanceof WidgetDestination) {
            this.interactable = ((WidgetDestination) destination).getWidget();
        } else if (destination instanceof TradeOfferItemDestination) {
            final int slot = ((TradeOfferItemDestination) destination).getSlot();
            final Trade.OfferOwner oo = ((TradeOfferItemDestination) destination).getOwner();

            if (oo == Trade.OfferOwner.OURS) {
                interactable = getTrade().getOurOffers().getItemInSlot(slot);
            } else if (oo == Trade.OfferOwner.THEIRS) {
                interactable = getTrade().getTheirOffers().getItemInSlot(slot);
            }
        } else if (destination instanceof BankSlotDestination) {
            interactable = getBank().getItemInSlot(((BankSlotDestination) destination).getWidgetSlotId());
        } else if (destination instanceof DepositBoxSlotDestination) {
            interactable = getDepositBox().getItemInSlot(((DepositBoxSlotDestination) destination).getSlotId());
        } else if (destination instanceof StoreSlotDestination) {
            interactable = getStore().getItemInSlot(((StoreSlotDestination) destination).getSlot());
        } else if (destination instanceof MainScreenTileDestination) {
            position = ((MainScreenTileDestination) destination).getPosition();
            this.actions = new String[] {"Walk here"};
        } else if (destination instanceof EntityDestination) {
            interactable = ((EntityDestination) destination).getEntity();
        } else {
            log("[InvokeInteractionEvent] Unknown Mouse Destination...");
        }

    }

    private void checkBadWidget() {
        if (interactable != null && this.actions != null && interactable instanceof RS2Widget) {
            final RS2Widget widget = (RS2Widget) interactable;
            if (widget.getInteractActions() == null || !widget.hasAction(this.actions)) {
                useMouse = true;
            }
        }
    }

}
