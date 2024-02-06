package api.wrapper.playground;

import api.wrapper.playground.model.ERS2Widget;
import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EWidgets extends Widgets {

    public EWidgets(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public List<RS2Widget> getAll() {
        final List<RS2Widget> raws = super.getAll();
        final List<RS2Widget> extras = new LinkedList<>();

        for (RS2Widget raw : raws) {
            if (raw != null) {
                extras.add(new ERS2Widget(raw, raw.getThirdLevelId()));
            }
        }

        return extras;
    }

    @Override
    public RS2Widget get(int rootId, int childId) {
        RS2Widget widget = super.get(rootId, childId);

        if (widget != null) {
            widget = new ERS2Widget(widget, widget.getThirdLevelId());
        }

        return widget;
    }

    @Override
    public RS2Widget[] getWidgets(int rootId, int childId) {
        RS2Widget[] widget = super.getWidgets(rootId, childId);

        if (widget != null) {
            final List<RS2Widget> list = new ArrayList<>();

            for (RS2Widget rs2Widget : widget) {
                list.add(new ERS2Widget(rs2Widget, rs2Widget.getThirdLevelId()));
            }

            widget = list.toArray(new RS2Widget[0]);
        }

        return widget;
    }

    @Override
    public RS2Widget get(int rootId, int childId, int subChildId) {
        RS2Widget widget = super.get(rootId, childId, subChildId);

        if (widget != null) {
            widget = new ERS2Widget(widget, widget.getThirdLevelId());
        }

        return widget;
    }

}
