package api.wrapper.extra;

import api.util.Sleep;
import api.data.widgets.WidgetID;
import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.utility.Condition;

import java.util.Arrays;
import java.util.function.Predicate;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Dialogues {

    private static final Predicate<RS2Widget> continueFilter = w ->
            ("Click here to continue".equals(w.getMessage()) || "Please wait...".equals(w.getMessage()))
                    && w.getTextColor() == 255;

    public static org.osbot.rs07.api.Dialogues get() {
        return ExtraProviders.getContext().getDialogues();
    }

    public static boolean inDialogue() {
        return canContinue() || isOption();
    }

    public static boolean isOption() {
        return WidgetID.DIALOGUE_OPTION_CONTAINER.getWidget() != null;
    }

    public static boolean canContinue() {
        return Widgets.get(continueFilter) != null;
    }

    public static boolean sendContinue() {
        return ExtraProviders.getContext().getKeyboard().typeString(" ");
    }

    public static boolean selectOption(String... options) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final RS2Widget parent = WidgetID.DIALOGUE_OPTION_CONTAINER.getWidget();

        if (parent != null) {
            final RS2Widget[] children = parent.getChildWidgets();

            if (children != null) {

                for (RS2Widget child : children) {
                    if (child != null && Arrays.asList(options).contains(child.getMessage())) {
                        return ctx.getKeyboard().typeString(String.valueOf(child.getThirdLevelId()), false);
                    }
                }

            }

        }

        return false;
    }

    public static boolean completeDialogue(String... options) {
        final ExtraProvider ctx = ExtraProviders.getContext();

        for (int i = 0; i < 100; i++) {
            if (!Dialogues.inDialogue()) {
                break;
            } else if (Dialogues.isOption()) {
                Dialogues.selectOption(options);
            } else if (Dialogues.canContinue()) {
                ctx.getKeyboard().typeContinualKey(' ', new Condition() {
                    @Override
                    public boolean evaluate() {
                        return !Dialogues.canContinue() || !ctx.getBot().getScriptExecutor().isRunning();
                    }
                });
            } else {
                Sleep.sleepTick();
            }

        }

        return !inDialogue();
    }

}
