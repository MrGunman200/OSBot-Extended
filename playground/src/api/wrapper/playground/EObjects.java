package api.wrapper.playground;

import api.provider.ExtraProvider;
import api.wrapper.playground.model.ERS2Object;
import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import java.util.LinkedList;
import java.util.List;

public class EObjects extends Objects {

    public ExtraProvider extraProvider;
    public MethodProvider originalProvider;

    public EObjects(MethodProvider methodProvider, ExtraProvider extraProvider) {
        this.exchangeContext(methodProvider.getBot());
        this.originalProvider = methodProvider;
        this.extraProvider = extraProvider;
    }

    @Override
    public List<RS2Object> getAll() {
        final List<RS2Object> raws = super.getAll();
        final List<RS2Object> extras = new LinkedList<>();

        for (RS2Object raw : raws) {
            if (raw != null) {
                extras.add(new ERS2Object(raw));
            }
        }

        return extras;
    }

    @Override
    public List<RS2Object> get(int x, int y) {
        final List<RS2Object> raws = super.get(x, y);
        final List<RS2Object> extras = new LinkedList<>();

        for (RS2Object raw : raws) {
            if (raw != null) {
                extras.add(new ERS2Object(raw));
            }
        }

        return extras;
    }

}
