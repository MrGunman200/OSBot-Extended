package experimental.data.vars;

import experimental.provider.MethodProviders;
import org.osbot.rs07.script.MethodProvider;

public enum Varps {

    QUEST_TREE_GNOME_VILLAGE(111),
    UNKNOWN_ARDOUGNE_TELEPORT_TAB(165),
    MUSIC_VOLUME(168),
    SOUND_EFFECT_VOLUME(169),
    TUTORIAL_ISLAND_PROGRESS(281),
    UNKNOWN_SALVE_GRAVEYARD_TELEPORT_TAB(302),
    AREA_EFFECT_VOLUME(872),
    EAGLE_PEAK_CAVE(934),
    ADVENTURE_PATHS(2854);

    Varps(int varp) {
        this.varp = varp;
    }

    private final int varp;

    public int getVarp() {
        return varp;
    }

    public int getValue() {
        return getValue(MethodProviders.getContext());
    }

    public int getValue(MethodProvider ctx) {
        return ctx.getConfigs().get(varp);
    }

}
