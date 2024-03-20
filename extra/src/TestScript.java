import api.script.LoopScript;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.InteractionEvent;
import org.osbot.rs07.script.ScriptManifest;

import java.util.Arrays;

//@ScriptManifest(info = "", logo = "", name = "ExtraTestScript", author = "", version = 0.0)
public class TestScript extends LoopScript {

    public int onLoop() throws InterruptedException {
        try {

        } catch (Exception e) {
            log(e);
            log(Arrays.asList(e.getStackTrace()));
            e.printStackTrace();
        }

        return 3000;
    }

}
