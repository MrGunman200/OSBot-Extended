package api.data.widgets;

import api.wrapper.extra.Widgets;
import org.osbot.rs07.api.ui.RS2Widget;

import java.util.function.Predicate;

public enum WidgetFilter {

    TUTORIAL_ISLAND_AVAILABLE_NAME(w ->
            w.getMessage() != null
            && w.getMessage().contains("Great! The display name")
    ),

    TUTORIAL_ISLAND_UNAVAILABLE_NAME(w ->
            w.getMessage() != null
            && w.getMessage().contains("not available")
    ),

    TUTORIAL_ISLAND_UNAVAILABLE_SYSTEM(w ->
            w.getMessage() != null
            && w.getMessage().contains("This system is currently <col=ff0000>unavailable</col>. Please try again.")
    );

    WidgetFilter(Predicate<RS2Widget> widgetFilter) {
        this.widgetFilter = widgetFilter;
    }

    private final Predicate<RS2Widget> widgetFilter;

    public Predicate<RS2Widget> getWidgetFilter() {
        return widgetFilter;
    }

    public RS2Widget getWidget() {
        return Widgets.get(widgetFilter);
    }

}
