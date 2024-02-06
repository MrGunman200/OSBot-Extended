package api.data.states;

import api.wrapper.extra.Widgets;
import api.data.vars.Varps;
import api.data.widgets.WidgetID;
import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import org.osbot.rs07.api.Settings;
import org.osbot.rs07.api.ui.Tab;

public class Sound {

    public static boolean isMusicOn() {
        return Varps.MUSIC_VOLUME.getValue() != 0;
    }

    public static boolean isSoundEffectOn() {
        return Varps.SOUND_EFFECT_VOLUME.getValue() != 0;
    }

    public static boolean isAreaEffectOn() {
        return Varps.AREA_EFFECT_VOLUME.getValue() != 0;
    }

    public static boolean toggleMusic() {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final boolean isOn = isMusicOn();

        ctx.getTabs().open(Tab.SETTINGS);
        ctx.getSettings().open(Settings.BasicSettingsTab.AUDIO);
        Widgets.interact(WidgetID.MUSIC_VOLUME.getWidget());
        ctx.sleepTick();

        return isOn != isMusicOn();
    }

    public static boolean toggleSoundEffect() {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final boolean isOn = isSoundEffectOn();

        ctx.getTabs().open(Tab.SETTINGS);
        ctx.getSettings().open(Settings.BasicSettingsTab.AUDIO);
        Widgets.interact(WidgetID.SOUND_EFFECT_VOLUME.getWidget());
        ctx.sleepTick();

        return isOn != isSoundEffectOn();
    }

    public static boolean toggleAreaEffect() {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final boolean isOn = isAreaEffectOn();

        ctx.getTabs().open(Tab.SETTINGS);
        ctx.getSettings().open(Settings.BasicSettingsTab.AUDIO);
        Widgets.interact(WidgetID.AREA_EFFECT_VOLUME.getWidget());
        ctx.sleepTick();

        return isOn != isAreaEffectOn();
    }

    public static boolean disableVolume() {
        if (isMusicOn()) {
            toggleMusic();
        }

        if (isSoundEffectOn()) {
            toggleSoundEffect();
        }

        if (isAreaEffectOn()) {
            toggleAreaEffect();
        }

        return !isMusicOn() && !isSoundEffectOn() && !isAreaEffectOn();
    }

}
