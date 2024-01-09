package experimental.api.provider;

import org.osbot.rs07.Bot;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import api.util.Sleep;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;

public abstract class ExtraProvider extends Script {

    private ExtraBot extraBot = new ExtraBot();
    private Helpers helpers = new Helpers(this);

    public ExtraBot getExtraBot() {
        return extraBot;
    }

    public Helpers getHelpers() {
        return helpers;
    }

    @Override
    public MethodProvider exchangeContext(Bot bot) {
        if (bot.getMethods() instanceof ExtraProvider) {
            final ExtraProvider provider = (ExtraProvider) bot.getMethods();
            extraBot = provider.extraBot;
            helpers = provider.helpers;
        }

        super.exchangeContext(bot);
        return this;
    }

    public void sleepTick() {
        sleepTicks(1);
    }

    // Gross way to do it, oh well
    public void sleepTicks(int ticks) {
        final AtomicLong curTick = new AtomicLong(getExtraBot().getGameTick());
        final long endTicks = curTick.get() + ticks;
        final BooleanSupplier breakSupplier = ()-> getExtraBot().getGameTick() >= endTicks || !getClient().isLoggedIn();
        final BooleanSupplier resetSupplier = ()-> {
            final long t = getExtraBot().getGameTick();
            final boolean result = t != curTick.get();
            curTick.set(t);
            return result;
        };

        Sleep.until(breakSupplier, resetSupplier, 2_400, 10);
    }

}
