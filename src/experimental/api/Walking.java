package experimental.api;

import experimental.provider.ExtraProvider;
import experimental.provider.ExtraProviders;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;

public class Walking {

    private static final PathPreferenceProfile ppp = new PathPreferenceProfile()
            .setAllowCharters(true)
            .setAllowGliders(true)
            .setAllowFairyRings(true)
            .setAllowObstacles(true)
            .setAllowQuestLinks(true)
            .setAllowSpiritTrees(true)
            .setAllowTeleports(true)
            .checkInventoryForItems(true)
            .checkEquipmentForItems(true);

    public static org.osbot.rs07.api.Walking get() {
        return ExtraProviders.getContext().getWalking();
    }

    public static void webWalk(Position... positions) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final WebWalkEvent event = build(new WebWalkEvent(positions), ctx);
        ctx.execute(event).hasFinished();
    }

    public static void webWalk(Area... areas) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final WebWalkEvent event = build(new WebWalkEvent(areas), ctx);
        ctx.execute(event).hasFinished();
    }

    private static WebWalkEvent build(WebWalkEvent event, ExtraProvider ctx) {
        ctx.getCamera().toTop();

        event.useSimplePath();
        event.setMinDistanceThreshold(0);
        event.setMisclickThreshold(0);
        event.setMoveCameraDuringWalking(false);
        event.setPathPreferenceProfile(ppp);

        return event;
    }

}
