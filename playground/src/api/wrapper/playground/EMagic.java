package api.wrapper.playground;

import api.invoking.InvokeHelper;
import api.provider.ExtraProvider;
import api.provider.InteractionType;
import org.osbot.rs07.api.Magic;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

public class EMagic extends Magic {

    public EMagic(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public boolean castSpell(MagicSpell spell, String action) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.castSpell(spell, action);
        } else if (interactionType == InteractionType.INVOKE) {
            final RS2Widget spellWidget = ctx.getMagic().getSpellWidget(spell);
            return spellWidget != null && (spellWidget.interact(action) || super.castSpell(spell, action));
        }

        return false;
    }

    @Override
    public boolean castSpellOnEntity(MagicSpell spell, Entity entity) {
        final ExtraProvider ctx = getExtraProvider();
        final InteractionType interactionType = ctx.getExtraBot().getInteractionType();

        if (interactionType == InteractionType.DEFAULT) {
            return super.castSpellOnEntity(spell, entity);
        } else if (interactionType == InteractionType.INVOKE) {
            final InvokeHelper invokeHelper = ctx.getHelpers().getInvokeHelper();
            final RS2Widget spellWidget = ctx.getMagic().getSpellWidget(spell);
            return spellWidget != null && invokeHelper.invokeOn(spellWidget, entity);
        }

        return false;
    }

    private ExtraProvider getExtraProvider() {
        return ((EObjects) objects).extraProvider;
    }

}
