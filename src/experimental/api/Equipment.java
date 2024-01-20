package experimental.api;

import experimental.provider.ExtraProviders;

public class Equipment {

    public static org.osbot.rs07.api.Equipment get() {
        return ExtraProviders.getContext().getEquipment();
    }

}
