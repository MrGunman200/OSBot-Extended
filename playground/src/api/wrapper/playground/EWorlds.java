package api.wrapper.playground;

import api.invoking.InvokeHelper;
import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.Client;
import org.osbot.rs07.api.Worlds;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep2;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class EWorlds extends Worlds {

    private final Supplier<Boolean> DEFAULT_BREAK = ()->
            Thread.interrupted()
            || !getBot().getScriptExecutor().isRunning()
            || getCombat().isFighting();

    private final Supplier<RS2Widget> WORLD_SWITCH = ()-> getWidgets().get(182, 3);
    private final Supplier<RS2Widget> SWITCH_WORLD_WARNING = ()-> getWidgets().get(193, 0, 3);

    public EWorlds(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public boolean hop(int world, boolean allowedWorldsOnly, Callable<Boolean> breakCondition) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();
        world = world < 300 ? world + 300 : world;

        if (interactionType == InteractionType.DEFAULT) {
            return super.hop(world, allowedWorldsOnly, breakCondition);
        } else if (interactionType == InteractionType.INVOKE) {
            return invokeHop(world, breakCondition) && loggedBackIn(world);
        }

        return false;
    }

    @Override
    public boolean hop(int world, Callable<Boolean> breakCondition) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();
        world = world < 300 ? world + 300 : world;

        if (interactionType == InteractionType.DEFAULT) {
            return super.hop(world, breakCondition);
        } else if (interactionType == InteractionType.INVOKE) {
            return invokeHop(world, breakCondition) && loggedBackIn(world);
        }

        return false;
    }

    @Override
    public boolean hop(int world, boolean allowedWorldsOnly) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();
        world = world < 300 ? world + 300 : world;

        if (interactionType == InteractionType.DEFAULT) {
            return super.hop(world, allowedWorldsOnly);
        } else if (interactionType == InteractionType.INVOKE) {
            return invokeHop(world) && loggedBackIn(world);
        }

        return false;
    }

    @Override
    public boolean hop(int world) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();
        world = world < 300 ? world + 300 : world;

        if (interactionType == InteractionType.DEFAULT) {
            return super.hop(world);
        } else if (interactionType == InteractionType.INVOKE) {
            return invokeHop(world) && loggedBackIn(world);
        }

        return false;
    }

    private boolean loggedBackIn(int world) {
        return ConditionalSleep2.sleep(30_000, ()->
                client.getGameState() == Client.GameState.LOGGED_IN && getCurrentWorld() == world);
    }

    private boolean invokeHop(int world) {
        return invokeHop(world, ()-> false);
    }

    private boolean invokeHop(int world, Callable<Boolean> breakCondition) {
        final ExtraProvider ctx = getExtraProvider();
        final InvokeHelper ih = ctx.getHelpers().getInvokeHelper();

        try {
            for (int i = 0; i < 8; i++) {
                final RS2Widget switchWorldWarning = SWITCH_WORLD_WARNING.get();
                final RS2Widget worldSwitcher = WORLD_SWITCH.get();
                final RS2Widget worldWidget = getWidgets().get(69, 18, world);

                if (DEFAULT_BREAK.get() || breakCondition.call()) {
                    return false;
                } else if (client.getGameState() == Client.GameState.HOPPING || getCurrentWorld() == world) {
                    return true;
                } else if (getWidgets().getCloseInterface() != null && getWidgets().closeOpenInterface()) {
                    ConditionalSleep2.sleep(1200, ()-> getWidgets().getCloseInterface() == null);
                } else if (switchWorldWarning != null && getKeyboard().typeString("2")) {
                    ConditionalSleep2.sleep(1200, ()-> client.getGameState() != Client.GameState.LOGGED_IN);
                } else if (getDialogues().inDialogue() && ih.invokeCloseWidget()) {
                    ConditionalSleep2.sleep(1200, ()-> !getDialogues().inDialogue());
                } else if (worldSwitcher != null && worldSwitcher.interact()) {
                    ConditionalSleep2.sleep(1200, ()-> WORLD_SWITCH.get() == null);
                } else if (worldWidget != null && worldWidget.interact()) {
                    ConditionalSleep2.sleep(1200, ()-> SWITCH_WORLD_WARNING.get() != null
                            || client.getGameState() == Client.GameState.HOPPING);
                }

            }
        } catch (Exception e) {
            log(e);
            log(Arrays.asList(e.getStackTrace()));
            e.printStackTrace();
        }

        return client.getGameState() == Client.GameState.HOPPING || getCurrentWorld() == world;
    }

    private ExtraProvider getExtraProvider() {
        return ((EObjects) objects).extraProvider;
    }

}
