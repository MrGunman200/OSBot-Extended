package experimental;

import api.invoking.InvokeHelper;
import api.movement.Reachable;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.InteractionEvent;
import org.osbot.rs07.script.ScriptManifest;
import experimental.api.script.LoopScript;

import java.util.Arrays;

@ScriptManifest(info = "", logo = "", name = "TestScript", author = "", version = 0.0)
public class TestScript extends LoopScript {

    public int onLoop() throws InterruptedException {

        getCamera().setUseMouse(true);

        final RS2Object e = getObjects().closest("Tree");
        final InteractionEvent event = new InteractionEvent(e, "Examine");
        //e.interact("Examine");
        event.setWalkTo(false);
        execute(event).hasFinished();

/*
        try {

            final Position position = new Position(3203, 3244, 0);
            final Position myPos = myPosition();
            final long start = System.currentTimeMillis();
            //final boolean result = Pathing.canPath(this, myPos, position);
            final boolean result2 = getMap().canReach(position);
            final long end = System.currentTimeMillis();

            log("");
            log("Results - " + result2);
            log("Time - " + (end - start) + "ms");
            log("");

        } catch (Exception e) {
            log(e);
            e.printStackTrace();
        }


        try {
            final Entity entity = getObjects().closest("Hopper");

            if (entity != null && entity.exists()) {
                log("");
                log(Reachable.canReach(this, entity));
                log(getMap().canReach(entity));
                log("");
            } else {
                log("Null");
            }

        } catch (Exception e) {
            log(e);
            e.printStackTrace();
        }


        //final Position p = new Position(3729, 5686, 0);
        //System.out.println(Pathing.pathTo(this, p).contains(p));

        //new Position(2933, 3450, 0);

        final Position start = new Position(3161, 3488, 0);//new Position(3235, 3218, 0);
        final Position end = new Position(2701, 3728, 0);
        final long startTime = System.currentTimeMillis();
        final WebWalkEvent webWalkEvent = new WebWalkEvent(end);
        webWalkEvent.setSourcePosition(start);
        execute(webWalkEvent);

        final long endTime = System.currentTimeMillis();
        log("Exec time = " + (endTime - startTime) + "ms");

        final Position dest = webWalkEvent.getDestination();
        final Position[] path = webWalkEvent.getPositions();

        if (dest != null && path != null) {
            log("Destination -> " + dest);
            log("Path size -> " + path.length);
        }
*/

        return 3000;
    }

}
