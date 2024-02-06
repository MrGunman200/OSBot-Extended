package api.wrapper.extra;

import api.provider.ExtraProviders;

public class Equipment {

    public static org.osbot.rs07.api.Equipment get() {
        return ExtraProviders.getContext().getEquipment();
    }

}
