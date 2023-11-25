package testing;

import api.movement.Pathing;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.ScriptManifest;
import api.movement.Reachable;
import testing.api.script.LoopScript;

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

        final Position p = new Position(3729, 5686, 0);
        System.out.println(Pathing.pathTo(this, p).contains(p));

        return 3000;
    }

}
