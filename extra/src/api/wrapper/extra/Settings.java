package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Settings {

    public static org.osbot.rs07.api.Settings get() {
        return ExtraProviders.getContext().getSettings();
    }

}
