package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import api.wrapper.playground.util.EItemContainer;
import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep2;

import java.util.function.Supplier;

public class EBank extends Bank implements EItemContainer {

    private final Supplier<RS2Widget> DEPOSIT_INVENTORY = ()-> getWidgets().get(12, 42);

    public EBank(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
        initializeModule();
    }

    @Override
    public Item[] getItems() {
        return getItems(super.getItems());
    }

    @Override
    public boolean interact(int slot, String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.interact(slot, actions);
        } else if (interactionType == InteractionType.INVOKE) {
            final Item item = getItemInSlot(slot);
            return item != null && item.interact(actions);
        }

        return false;
    }

    @Override
    public boolean withdraw(Filter<Item> filter, int amount_, boolean checkResults) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.withdraw(filter, amount_, checkResults);
        } else if (interactionType == InteractionType.INVOKE) {
            final Item item = getItem(filter);

            if (item != null) {
                final boolean actionResult = amount_ >= item.getAmount() || amount_ == 0;
                final String action = actionResult ? "Withdraw-All" : "Withdraw-" + amount_;
                return (item.hasAction(action) && item.interact(action))
                        || (item.interact("Withdraw-X") && handleXValue(amount_));
            }
        }

        return false;
    }

    @Override
    public boolean deposit(Filter<Item> filter, int amount_) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.deposit(filter, amount_);
        } else if (interactionType == InteractionType.INVOKE) {
            final Item item = getInventory().getItem(filter);

            if (item != null) {
                final boolean actionResult = amount_ >= item.getAmount() || amount_ == 0;
                final String action = actionResult ? "Deposit-All" : "Deposit-" + amount_;
                return (item.hasAction(action) && item.interact(action))
                        || (item.interact("Deposit-X") && handleXValue(amount_));
            }
        }

        return false;
    }

    @Override
    public boolean depositAll() {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.depositAll();
        } else if (interactionType == InteractionType.INVOKE) {
            final RS2Widget widget = DEPOSIT_INVENTORY.get();
            return widget != null && widget.interact();
        }

        return false;
    }

    private boolean handleXValue(int amount) {
        return ConditionalSleep2.sleep(3_000, ()-> getEnterAmountBox() != null)
                && getKeyboard().typeString(String.valueOf(amount))
                && ConditionalSleep2.sleep(3_000, ()-> getEnterAmountBox() == null);
    }

    private RS2Widget getEnterAmountBox() {
        return getWidgets().getWidgetContainingText(162, "Enter amount:");
    }

}
