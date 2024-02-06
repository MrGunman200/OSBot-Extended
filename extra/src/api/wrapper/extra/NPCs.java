package api.wrapper.extra;

import api.provider.ExtraProviders;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;

import java.util.Arrays;
import java.util.Comparator;

public class NPCs {

    public static org.osbot.rs07.api.NPCS get() {
        return ExtraProviders.getContext().getNpcs();
    }

    public static NPC getNearest(Position to, String... names) {
        return get().getAll().stream()
                .filter(n -> Arrays.asList(names).contains(n.getName()))
                .min(Comparator.comparingDouble(t -> t.getPosition().distance(to)))
                .orElse(null);
    }

}
