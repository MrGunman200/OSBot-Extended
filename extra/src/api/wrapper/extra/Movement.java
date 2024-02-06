package api.wrapper.extra;

import api.movement.Reachable;
import api.provider.ExtraProviders;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;

@SuppressWarnings("unused")
public class Movement extends Walking {

    public static boolean canReach(Entity entity) {
        return canReach(entity, true);
    }

    public static boolean canReach(Entity entity, boolean useTravel) {
        return Reachable.canReach(ExtraProviders.getContext(), entity, useTravel);
    }

    public static boolean canReach(Position position) {
        return canReach(position, true);
    }

    public static boolean canReach(Position position, boolean useTravel) {
        final Area area = new Area(position, position);
        return Reachable.canReach(ExtraProviders.getContext(), area, useTravel);
    }

    public static boolean canReach(Area area) {
        return canReach(area, true);
    }

    public static boolean canReach(Area area, boolean useTravel) {
        return Reachable.canReach(ExtraProviders.getContext(), area, useTravel);
    }

}
