package api.wrapper.extra;

import api.provider.ExtraProviders;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;

public class RS2Objects {

    public static org.osbot.rs07.api.Objects get() {
        return ExtraProviders.getContext().getObjects();
    }

    public static RS2Object getFirstAt(Position position, int id) {
        return get().get(position.getX(), position.getY())
                .stream().filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
