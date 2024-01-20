package experimental.data.states;

import experimental.data.vars.Varbits;

public class Game {

    public static boolean inCutScene() {
        return Varbits.CUT_SCENE.getValue() > 0;
    }

}
