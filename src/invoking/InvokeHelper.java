package invoking;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import java.awt.event.MouseEvent;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class InvokeHelper extends MethodProvider {

    public InvokeHelper(MethodProvider context) {
        this.exchangeContext(context.getBot());
    }

    public InvokeHelper(MethodProvider context, boolean sendClick) {
        this.exchangeContext(context.getBot());
        this.sendClick = sendClick;
    }

    private boolean sendClick = true;

    public boolean isSendClick() {
        return sendClick;
    }

    public void setSendClick(boolean value) {
        sendClick = value;
    }

    private void fireEvent(int event) {
        final int screenX = 0;
        final int screenY = 0;
        getBot().getMouseEventHandler().generateBotMouseEvent(
                event,
                System.currentTimeMillis(),
                MouseEvent.BUTTON3_DOWN_MASK,
                screenX, screenY,
                1, false, MouseEvent.BUTTON3, true);
    }

    private void sendClick() {
        fireEvent(MouseEvent.MOUSE_PRESSED);
        fireEvent(MouseEvent.MOUSE_RELEASED);
        fireEvent(MouseEvent.MOUSE_CLICKED);
    }

    // Action and Target Name are irrelevant (Client sided)
    // Screen X and Y are just visual (Client sided)
    public boolean invoke(int param0, int param1, int opcode, int id, int itemId) {
        return invoke(param0, param1, opcode, id, itemId, "", "", -1, -1);
    }

    /*
     * If you want/need click here, invokeMenuAction doesn't send any mouse data
     * Jagex only tracks click's x, y, and time since last click
     * They do track if it was a middle mouse click, Jagex doesn't seem
     * to care if it's a right or left click only middle click or not
     *
     * Input data (mouse or key) is currently needed to reset 5 minute
     * client side logout timer and 15-20 minute server sided combat timer
     */
    public boolean invoke(int param0, int param1, int opcode, int id, int itemId, String action, String targetName, int screenX, int screenY) {
        try {
            if (isSendClick()) {
                sendClick();
            }

            getClient().accessor.invokeMenuAction(param0, param1, opcode, id, itemId, action, targetName, screenX, screenY);
            return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
            log(e);
        }

        return false;
    }

    public boolean invokeWalking(int sceneX, int sceneY) {
        try {
            if (isSendClick()) {
                sendClick();
            }

            getClient().accessor.invokeWalking(sceneX, sceneY);
            return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
            log(e);
        }

        return false;
    }

    public boolean invokeWalking(Entity entity, int radius) {
        return invokeWalking(entity.getArea(radius));
    }

    public boolean invokeWalking(Entity entity) {
        return invokeWalking(entity.getPosition());
    }

    public boolean invokeWalking(Area area) {
        return invokeWalking(area.getRandomPosition());
    }

    public boolean invokeWalking(Position position) {
        final int sceneX = position.getLocalX(getBot());
        final int sceneY = position.getLocalY(getBot());
        return invokeWalking(sceneX, sceneY);
    }

    /**
     * Sends a generic close action
     * Works for Bank, GE, and Dialogue. Doesn't work for windows such as settings window
     */
    public boolean invokeCloseWidget() {
        return invoke(0, 0, MenuAction.WIDGET_CLOSE.getId(), 0, -1);
    }

    /**
     * Looks for X button widget and interacts with that
     */
    public boolean invokeCloseButton() {
        final RS2Widget closeButton = getWidgets().getWidgetContainingAction("Close");
        return closeButton != null && invoke(closeButton, "Close");
    }

    /**
     * Mixture of both invokeCloseButton and invokeCloseWidget
     */
    public boolean closeInterface() {
        return invokeCloseButton() || invokeCloseWidget();
    }

    /**
     * Use invoke(RS2Widget widget, int index)
     */
    @Deprecated
    public boolean invokeDialogue(RS2Widget widget) {
        final int id = 0;
        final int opcode = MenuAction.WIDGET_CONTINUE.getId();
        return invoke(widget, opcode, id);
    }

    @Deprecated
    public boolean invokeDialogue(int widgetIndex, int widgetId) {
        return invoke(widgetIndex, widgetId, MenuAction.WIDGET_CONTINUE.getId(), 0, -1);
    }

    @Deprecated
    public boolean invokeDialogue(int widgetIndex, int widgetId, int itemId) {
        return invoke(widgetIndex, widgetId, MenuAction.WIDGET_CONTINUE.getId(), 0, itemId);
    }

    @Deprecated
    public boolean invokeDialogue(int widgetIndex, int widgetId, int identifier, int itemId) {
        return invoke(widgetIndex, widgetId, MenuAction.WIDGET_CONTINUE.getId(), identifier, itemId);
    }

    public boolean invokeOn(RS2Widget widget, GroundItem groundItem) {
        return invokeUse(widget) && invoke(groundItem, -5);
    }

    public boolean invokeOn(RS2Widget widget, RS2Object object) {
        return invokeUse(widget) && invoke(object, -5);
    }

    public boolean invokeOn(RS2Widget widget, NPC npc) {
        return invokeUse(widget) && invoke(npc, -5);
    }

    public boolean invokeOn(RS2Widget widget, Player player) {
        return invokeUse(widget) && invoke(player, -5);
    }

    public boolean invokeOn(RS2Widget widget, RS2Widget otherWidget) {
        return invokeUse(widget) && invokeOn(otherWidget);
    }

    public boolean invokeOn(RS2Widget otherWidget) {
        final int id = 0;
        final int opcode = MenuAction.WIDGET_TARGET_ON_WIDGET.getId();
        return invoke(otherWidget, opcode, id);
    }

    public boolean invokeUse(RS2Widget widget) {
        final int id = 0;
        final int opcode = MenuAction.WIDGET_TARGET.getId();
        return invoke(widget, opcode, id);
    }

    public boolean invoke(RS2Widget widget, String action) {
        if (action.equalsIgnoreCase("use") || action.equalsIgnoreCase("cast")) {
            if (getInventory().isItemSelected() || getMagic().isSpellSelected()) {
                return invokeOn(widget);
            }

            return invokeUse(widget);
        }

        return invoke(widget, getIndexForAction(action, widget.getInteractActions()));
    }

    public boolean invoke(RS2Widget widget, int index) {
        final int opcode = getWidgetOpcode(widget, index);
        final int identifier = getWidgetIdentifier(widget, index);
        return invoke(widget, opcode, identifier);
    }

    public boolean invoke(RS2Widget widget, int opcode, int identifier) {
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        return invoke(param0, param1, opcode, identifier, widget.getItemId());
    }

    public boolean invoke(RS2Widget widget, int opcode, int identifier, int itemId) {
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        return invoke(param0, param1, opcode, identifier, itemId);
    }

    public boolean invoke(Item item, String action) {
        return invoke(item.getOwner(), action);
    }

    public boolean invoke(Item item, int opcode, int identifier) {
        return invoke(item.getOwner(), opcode, identifier);
    }

    public boolean invoke(Item item, int index) {
        return invoke(item.getOwner(), index);
    }

    public boolean invoke(GroundItem groundItem, String action) {
        if (action.equalsIgnoreCase("use") || action.equalsIgnoreCase("cast")) {
            return invoke(groundItem, -5);
        }

        return invoke(groundItem, getIndexForAction(action, groundItem.getActions()));
    }

    public boolean invoke(GroundItem groundItem, int opcode, int identifier)  {
        final int param0 = groundItem.getLocalX();
        final int param1 = groundItem.getLocalY();
        return groundItem.exists() && invoke(param0, param1, opcode, identifier, -1);
    }

    public boolean invoke(GroundItem groundItem, int index) {
        final int id = groundItem.getId();
        final int param0 = groundItem.getLocalX();
        final int param1 = groundItem.getLocalY();
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_GROUND_ITEM.getId();
                break;
            case 0:
                opcode = MenuAction.GROUND_ITEM_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.GROUND_ITEM_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.GROUND_ITEM_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.GROUND_ITEM_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.GROUND_ITEM_FIFTH_OPTION.getId();
                break;
        }

        return groundItem.exists() && invoke(param0, param1, opcode, id, -1);
    }

    public boolean invoke(Player player, String action) {
        // These actions are independent of getActions
        if (action.equalsIgnoreCase("trade")) {
            return invoke(player, -2);
        } else if (action.equalsIgnoreCase("follow")) {
            return invoke(player, -3);
        } else if (action.equalsIgnoreCase("use") || action.equalsIgnoreCase("cast")) {
            return invoke(player, -5);
        }

        return invoke(player, getIndexForAction(action, player.getActions()));
    }

    public boolean invoke(Player player, int opcode, int identifier) {
        return player.exists() && invoke(0, 0, opcode, identifier, -1);
    }

    public boolean invoke(Player player, int index) {
        final int id = player.getId();
        final int param0 = 0;
        final int param1 = 0;
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_PLAYER.getId();
                break;
            case -3:
                opcode = MenuAction.FOLLOW.getId();
                break;
            case -2:
                opcode = MenuAction.TRADE.getId();
                break;
            case 0:
                opcode = MenuAction.PLAYER_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.PLAYER_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.PLAYER_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.PLAYER_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.PLAYER_FIFTH_OPTION.getId();
                break;
            case 5:
                opcode = MenuAction.PLAYER_SIXTH_OPTION.getId();
                break;
            case 6:
                opcode = MenuAction.PLAYER_SEVENTH_OPTION.getId();
                break;
            case 7:
                opcode = MenuAction.PLAYER_EIGHTH_OPTION.getId();
                break;
        }

        return player.exists() && invoke(param0, param1, opcode, id, -1);
    }

    public boolean invoke(NPC npc, String action) {
        if (action.equalsIgnoreCase("use") || action.equalsIgnoreCase("cast")) {
            return invoke(npc, -5);
        }

        return invoke(npc, getIndexForAction(action, npc.getActions()));
    }

    public boolean invoke(NPC npc, int opcode, int identifier) {
        return npc.exists() && invoke(0, 0, opcode, identifier, -1);
    }

    public boolean invoke(NPC npc, int index) {
        final int id = npc.getIndex();
        final int param0 = 0;
        final int param1 = 0;
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_NPC.getId();
                break;
            case 0:
                opcode = MenuAction.NPC_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.NPC_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.NPC_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.NPC_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.NPC_FIFTH_OPTION.getId();
                break;
        }

        return npc.exists() && invoke(param0, param1, opcode, id, -1);
    }

    public boolean invoke(RS2Object object, String action) {
        if (action.equalsIgnoreCase("use") || action.equalsIgnoreCase("cast")) {
            return invoke(object, -5);
        }

        return invoke(object, getIndexForAction(action, object.getActions()));
    }

    public boolean invoke(RS2Object object, int opcode, int identifier) {
        final int param0 = object.getLocalX();
        final int param1 = object.getLocalY();
        return object.exists() && invoke(param0, param1, opcode, identifier, -1);
    }

    public boolean invoke(RS2Object object, int index) {
        final int id = object.getId();
        final int param0 = object.getLocalX();
        final int param1 = object.getLocalY();
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_GAME_OBJECT.getId();
                break;
            case 0:
                opcode = MenuAction.GAME_OBJECT_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.GAME_OBJECT_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.GAME_OBJECT_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.GAME_OBJECT_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.GAME_OBJECT_FIFTH_OPTION.getId();
                break;
        }

        return object.exists() && invoke(param0, param1, opcode, id, -1);
    }

    public int getIndexForAction(String targetAction, String[] actions) throws IndexOutOfBoundsException {
        for (int i = 0; i < actions.length; i++) {
            final String action = actions[i];
            if (action != null) {
                if (action.equals(targetAction)) {
                    return i;
                }
            }
        }

        throw new IndexOutOfBoundsException("Action index couldn't be found");
    }

    public int getWidgetOpcode(RS2Widget widget, int index) {
        return getWidgetOpcode(widget.getType(), index, widget.getSelectedActionName());
    }

    public int getWidgetOpcode(int widgetType, int index, String selectedActionName) {
        switch (widgetType) {
            case WidgetType.GRAPHIC:
                return index < 6 ? MenuAction.CC_OP.getId() : MenuAction.CC_OP_LOW_PRIORITY.getId();
            case WidgetType.TEXT:
                return MenuAction.WIDGET_CONTINUE.getId();
            default:
                return MenuAction.CC_OP.getId();
        }
    }

    public int getWidgetIdentifier(RS2Widget widget, int index) {
        return getWidgetIdentifier(widget.getType(), index, widget.getSelectedActionName());
    }

    public int getWidgetIdentifier(int widgetType, int index, String selectedActionName) {
        if (widgetType == WidgetType.TEXT) {
            return 0;
        }

        return index + 1;
    }

}
