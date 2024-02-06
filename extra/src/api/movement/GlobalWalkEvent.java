package api.movement;

import api.movement.pathfinding.collision.map.global.GlobalCollisionMap;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO Finish importing global walking
public class GlobalWalkEvent extends Event {

    private final List<Position> start = new ArrayList<>();
    private final GlobalCollisionMap map = GlobalCollisionMap.get();
    private Position source = null;
    private Area[] targets = null;

    private boolean useTeleports = true;
    private boolean useMiniGameTeleports = false;
    private boolean useCharterShip = false;
    private boolean useFairyRing = false;
    private boolean useGnomeGlider = false;

    public void onStart() throws InterruptedException {
        super.onStart();
        checkSource();
        addStartPositions();
        // Load Transports
        // Load Teleports
    }

    @Override
    public int execute() throws InterruptedException {

        return 100;
    }

    // Not allowed to set this event async
    @Override
    public Event setAsync() {
        return this;
    }

    public GlobalWalkEvent setUseTeleports(boolean useTeleports) {
        this.useTeleports = useTeleports;
        return this;
    }

    public GlobalWalkEvent setUseMiniGameTeleports(boolean useMiniGameTeleports) {
        this.useMiniGameTeleports = useMiniGameTeleports;
        return this;
    }

    public GlobalWalkEvent setUseCharterShip(boolean useCharterShip) {
        this.useCharterShip = useCharterShip;
        return this;
    }

    public GlobalWalkEvent setUseFairyRing(boolean useFairyRing) {
        this.useFairyRing = useFairyRing;
        return this;
    }

    public GlobalWalkEvent setUseGnomeGlider(boolean useGnomeGlider) {
        this.useGnomeGlider = useGnomeGlider;
        return this;
    }

    public GlobalWalkEvent addTargets(Area... targets) {
        if (this.targets == null || targets.length == 0) {
            return setTargets(targets);
        }

        final List<Area> t = new ArrayList<>();
        t.addAll(Arrays.asList(this.targets));
        t.addAll(Arrays.asList(targets));

        this.targets = t.toArray(new Area[0]);
        return this;
    }

    public GlobalWalkEvent setTargets(Area... targets) {
        this.targets = targets;
        return this;
    }

    private void checkSource() {
        if (source == null) {
            source = myPosition();
        }
    }

    private void addStartPositions() {
        start.add(source);
    }

}
