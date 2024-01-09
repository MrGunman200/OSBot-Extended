package experimental.api.script;

import org.osbot.rs07.listener.GameTickListener;
import experimental.api.provider.ExtraProvider;
import api.util.Sleep;
import experimental.api.provider.ExtraProviders;
import experimental.api.provider.MethodProviders;

import java.awt.Graphics2D;

public abstract class LoopScript extends ExtraProvider {

    private final GameTickListener gameTickListener = () -> {
        final long curTick = getExtraBot().getGameTick();
        getExtraBot().setGameTick(curTick + 1);
    };

    @Override
    public void onStart() throws InterruptedException {
        getHelpers().setHelpers();
        getBot().addGameTickListener(gameTickListener);
        ExtraProviders.register(getBot().getScriptExecutor(), this);
        MethodProviders.register(getBot().getScriptExecutor(), this);
        ExtraProviders.register(Thread.currentThread(), this);
        MethodProviders.register(Thread.currentThread(), this);
    }

    @Override
    public void onExit() throws InterruptedException {
        getBot().removeGameTickListener(gameTickListener);
        ExtraProviders.unregister(this);
        MethodProviders.unregister(this);
    }

    @Override
    public void onPaint(Graphics2D g) {
        Sleep.sleep(1000 / getExtraBot().getFpsTarget() - 13);
    }

}
