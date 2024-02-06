package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.wrapper.playground.EObjects;
import org.osbot.rs07.script.MethodProvider;

import java.util.Arrays;
import java.util.List;

public interface EIdentifiable {

    MethodProvider getMethods();

    String[] getActions();

    default ExtraProvider getExtraProvider() {
        return ((EObjects) getMethods().objects).extraProvider;
    }

    default int getActionIndex(String... actions) {
        final String[] rawActions = getActions();

        if (rawActions != null && actions != null) {
            final List<String> actionList = Arrays.asList(actions);
            for (int i = 0; i < rawActions.length; i++) {
                if (actionList.contains(rawActions[i])) {
                    return i;
                }
            }
        }

        return 0;
    }

}
