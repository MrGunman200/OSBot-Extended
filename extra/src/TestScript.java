import api.invoking.InvokeProvider;
import api.script.LoopScript;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.ScriptManifest;

import java.util.Arrays;

@ScriptManifest(info = "", logo = "", name = "ExtraTestScript", author = "", version = 0.0)
public class TestScript extends LoopScript {

    @Override
    public void onStart() throws InterruptedException {
        super.onStart();
        getBot().setInteractionProvider(new InvokeProvider(this));
    }

    public int onLoop() throws InterruptedException {
        try {
            log("Main Loop");

            getWalking().webWalk(Banks.VARROCK_WEST);

        } catch (Exception e) {
            log(e);
            log(Arrays.asList(e.getStackTrace()));
            e.printStackTrace();
        }

        return 3000;
    }

}
