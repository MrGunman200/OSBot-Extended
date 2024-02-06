package api.wrapper.playground.model;

import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.Bot;
import org.osbot.rs07.api.Client;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

public class ERS2Object implements RS2Object, EIdentifiable {

    private final RS2Object object;

    public ERS2Object(RS2Object object) {
        this.object = object;
    }

    @Override
    public boolean interact(String... actions) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return RS2Object.super.interact(actions);
        } else if (interactionType == InteractionType.INVOKE) {
            return ctx.getHelpers().getInvokeHelper().invoke(this, getActionIndex(actions));
        }

        return false;
    }

    @Override
    public boolean exists() {
        return getBot().getMethods().objects.get(getX(), getY())
                .stream().anyMatch(o -> o.getUUID() == this.getUUID());
    }

    @Override
    public int getConfig() {
        return object.getConfig();
    }

    @Override
    public long getUUID() {
        return object.getUUID();
    }

    @Override
    public Client getClient() {
        return object.getClient();
    }

    @Override
    public Model getModel() {
        return object.getModel();
    }

    @Override
    public int getHeight() {
        return object.getHeight();
    }

    @Override
    public Bot getBot() {
        return object.getBot();
    }

    @Override
    public boolean isVisible() {
        return object.isVisible();
    }

    @Override
    public boolean equals(Object obj) {
        return object.equals(obj);
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }

    @Override
    public MethodProvider getMethods() {
        return getBot().getMethods();
    }

    @Override
    public String[] getActions() {
        return object.getActions();
    }

}
