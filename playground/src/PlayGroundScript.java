import api.provider.InteractionType;
import api.script.LoopScript;
import org.osbot.rs07.script.ScriptManifest;

import java.util.Arrays;

@ScriptManifest(
        info = "Attempts to Force Invokes Across the Entire Bot Tab",
        logo = "", name = "PlayGroundScript",
        author = "Gunman", version = 1.001
)
public class PlayGroundScript extends LoopScript {

    public int onLoop() throws InterruptedException {
        try {

            getExtraBot().setInteractionType(InteractionType.INVOKE);
            complete();

        } catch (Exception e) {
            log(e);
            log(Arrays.asList(e.getStackTrace()));
            e.printStackTrace();
        }

        return 1000;
    }

}
