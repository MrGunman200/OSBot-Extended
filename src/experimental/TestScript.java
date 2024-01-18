package experimental;

import experimental.api.script.LoopScript;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.InteractionEvent;

//@ScriptManifest(info = "", logo = "", name = "TestScript", author = "", version = 0.0)
public class TestScript extends LoopScript {

    public int onLoop() throws InterruptedException {

        getCamera().setUseMouse(true);

        final RS2Object e = getObjects().closest("Tree");
        final InteractionEvent event = new InteractionEvent(e, "Examine");
        //e.interact("Examine");
        event.setWalkTo(false);
        execute(event).hasFinished();

        return 3000;
    }

}
