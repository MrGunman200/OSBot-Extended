package testing;

import api.movement.Pathing;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;
import org.osbot.rs07.input.mouse.EntityDestination;
import org.osbot.rs07.input.mouse.MouseDestination;
import org.osbot.rs07.script.ScriptManifest;
import api.movement.Reachable;
import testing.api.script.LoopScript;

import java.awt.*;

//@ScriptManifest(info = "", logo = "", name = "TestScript", author = "", version = 0.0)
public class TestScript extends LoopScript {

    public int onLoop() throws InterruptedException {

        /*
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
         */

        //final Position p = new Position(3729, 5686, 0);
        //System.out.println(Pathing.pathTo(this, p).contains(p));

        final Position start = new Position(3235, 3218, 0);
        final Position end = new Position(2964, 3381, 0);
        final WebWalkEvent webWalkEvent = new WebWalkEvent(end);
        webWalkEvent.setSourcePosition(start);
        execute(webWalkEvent);

        final Position dest = webWalkEvent.getDestination();
        final Position[] path = webWalkEvent.getPositions();

        if (dest != null && path != null) {
            log("Destination -> " + dest);
            log("Path size -> " + path.length);
        }

        return 3000;
    }

}
