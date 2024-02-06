package api.provider;

import org.osbot.rs07.script.MethodProvider;

public class ExtraBot {

    public MethodProvider methodProvider = null;
    private InteractionType interactionType = InteractionType.DEFAULT;
    private long gameTick = 0;

    public InteractionType getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(InteractionType interactionType) {
        if (methodProvider.getBot().isMirrorMode()) {
            return;
        }

        this.interactionType = interactionType;
    }

    public long getGameTick() {
        return gameTick;
    }

    public void setGameTick(long gameTick) {
        this.gameTick = gameTick;
    }

}
