package api.data.states;

import api.data.vars.Varbits;

public class Game {

    public static boolean inCutScene() {
        return Varbits.CUT_SCENE.getValue() > 0;
    }

}
