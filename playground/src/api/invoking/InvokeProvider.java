package api.invoking;

import org.osbot.rs07.Bot;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.event.InteractionEvent;
import org.osbot.rs07.event.interaction.InteractionProvider;
import org.osbot.rs07.input.mouse.MouseDestination;
import org.osbot.rs07.script.MethodProvider;

/*
 * Add to onStart with
 * getBot().setInteractionProvider()
 */
public class InvokeProvider extends InteractionProvider {

    private InvokeHelper invokeHelper;
    private boolean useInvoke;

    public InvokeProvider(MethodProvider ctx) {
        this(ctx, true);
    }

    public InvokeProvider(MethodProvider ctx, boolean useInvoke) {
        this(new InvokeHelper(ctx, useInvoke));
    }

    public InvokeProvider(InvokeHelper invokeHelper) {
        this(invokeHelper, true);
    }

    public InvokeProvider(InvokeHelper invokeHelper, boolean useInvoke) {
        this.invokeHelper = invokeHelper;
        this.useInvoke = useInvoke;
    }

    @Override
    public InteractionEvent createInteractionEvent(MouseDestination mouseDestination, String... actions) {
        return new InvokeInteractionEvent(mouseDestination, actions)
                .setUseMouse(!useInvoke).setInvokeHelper(invokeHelper);
    }

    @Override
    public InteractionEvent createInteractionEvent(RS2Widget widget, String... actions) {
        return new InvokeInteractionEvent(widget, actions)
                .setUseMouse(!useInvoke).setInvokeHelper(invokeHelper);
    }

    @Override
    public InteractionEvent createInteractionEvent(Entity entity, String... actions) {
        return new InvokeInteractionEvent(entity, actions)
                .setUseMouse(!useInvoke).setInvokeHelper(invokeHelper);
    }

    @Override
    public InteractionEvent createInteractionEvent(Bot bot, Position position, String... actions) {
        return new InvokeInteractionEvent(bot, position, actions)
                .setUseMouse(!useInvoke).setInvokeHelper(invokeHelper);
    }

    public InvokeProvider setInvokeHelper(InvokeHelper invokeHelper) {
        this.invokeHelper = invokeHelper;
        return this;
    }

    public InvokeProvider setUseInvoke(boolean useInvoke) {
        this.useInvoke = useInvoke;
        return this;
    }

}
