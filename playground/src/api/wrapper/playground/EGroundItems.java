package api.wrapper.playground;

import api.wrapper.playground.model.EGroundItem;
import org.osbot.rs07.api.GroundItems;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.script.MethodProvider;

import java.util.LinkedList;
import java.util.List;

public class EGroundItems extends GroundItems {

    public EGroundItems(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public List<GroundItem> getAll() {
        final List<GroundItem> raws = super.getAll();
        final List<GroundItem> extras = new LinkedList<>();

        for (GroundItem raw : raws) {
            if (raw != null) {
                extras.add(new EGroundItem(raw));
            }
        }

        return extras;
    }

    @Override
    public List<GroundItem> get(int x, int y) {
        final List<GroundItem> raws = super.get(x, y);
        final List<GroundItem> extras = new LinkedList<>();

        for (GroundItem raw : raws) {
            if (raw != null) {
                extras.add(new EGroundItem(raw));
            }
        }

        return extras;
    }

}
