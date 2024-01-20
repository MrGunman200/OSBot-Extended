package experimental.data.widgets;

import experimental.api.Widgets;
import org.osbot.rs07.api.ui.RS2Widget;

public enum WidgetID {

    DIALOGUE_OPTION_CONTAINER(219, 1, -1),

    EQUIPMENT_VIEW(387, 1, -1),

    MUSIC_VOLUME(116, 93, -1),
    SOUND_EFFECT_VOLUME(116, 107, -1),
    AREA_EFFECT_VOLUME(116, 122, -1),

    TUTORIAL_ISLAND_RANDOM_WINDOW(119, 180, -1),
    TUTORIAL_ISLAND_SMITH_DAGGER(312, 9, -1),
    TUTORIAL_ISLAND_ENTER_DISPLAY_NAME(558, 7, -1),
    TUTORIAL_ISLAND_SET_NAME_BUTTON(558, 19, -1),

    DESIGN_PLAYER_FEMALE_BUTTON(679, 66, -1),
    DESIGN_PLAYER_ACCEPT_BUTTON(679, 68, -1);

    WidgetID(int root, int child, int grandChild) {
        this.root = root;
        this.child = child;
        this.grandChild = grandChild;
    }

    private final int root;
    private final int child;
    private final int grandChild;

    public int getRoot() {
        return root;
    }

    public int getChild() {
        return child;
    }

    public int getGrandChild() {
        return grandChild;
    }

    public RS2Widget getWidget() {
        if (grandChild == -1) {
            return Widgets.get().get(root, child);
        }

        return Widgets.get().get(root, child, grandChild);
    }

}
