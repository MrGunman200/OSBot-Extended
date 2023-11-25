package testing.api.script;

import org.osbot.rs07.listener.GameTickListener;
import testing.api.provider.ExtraProvider;
import api.util.Sleep;

import java.awt.Graphics2D;

public abstract class LoopScript extends ExtraProvider {

    private final GameTickListener gameTickListener = () -> {
        final long curTick = getExtraVars().getGameTick();
        getExtraVars().setGameTick(curTick + 1);
    };

    @Override
    public void onStart() throws InterruptedException {
        getHelpers().setHelpers();
        getBot().addGameTickListener(gameTickListener);
    }

    @Override
    public void onExit() throws InterruptedException {
        getBot().removeGameTickListener(gameTickListener);
    }

    @Override
    public void onPaint(Graphics2D g) {
        Sleep.sleep(1000 / getExtraVars().getFpsTarget() - 13);
    }

}
