package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Skills {

    public static org.osbot.rs07.api.Skills get() {
        return ExtraProviders.getContext().getSkills();
    }

}
