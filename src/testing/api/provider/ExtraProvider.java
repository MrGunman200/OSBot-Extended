package testing.api.provider;

import org.osbot.rs07.Bot;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import api.util.Sleep;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;

public abstract class ExtraProvider extends Script {

    private ExtraVars extraVars = new ExtraVars();
    private Helpers helpers = new Helpers(this);

    public ExtraVars getExtraVars() {
        return extraVars;
    }

    public Helpers getHelpers() {
        return helpers;
    }

    /*
    public ExtraProvider exchangeContext(ExtraProvider provider) {
        extraVars = provider.extraVars;
        helpers = provider.helpers;
        exchangeContext(provider.getBot());
        return this;
    }
     */

    @Override
    public MethodProvider exchangeContext(Bot bot) {
        if (bot.getMethods() instanceof ExtraProvider) {
            final ExtraProvider provider = (ExtraProvider) bot.getMethods();
            extraVars = provider.extraVars;
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
        final AtomicLong curTick = new AtomicLong(getExtraVars().getGameTick());
        final long endTicks = curTick.get() + ticks;
        final BooleanSupplier breakSupplier = ()-> getExtraVars().getGameTick() >= endTicks || !getClient().isLoggedIn();
        final BooleanSupplier resetSupplier = ()-> {
            final long t = getExtraVars().getGameTick();
            final boolean result = t != curTick.get();
            curTick.set(t);
            return result;
        };

        Sleep.until(breakSupplier, resetSupplier, 2_400, 10);
    }

}
