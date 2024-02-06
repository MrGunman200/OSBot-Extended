package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Keyboard {

    public static org.osbot.rs07.api.Keyboard get() {
        return ExtraProviders.getContext().getKeyboard();
    }

}
