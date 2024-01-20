package experimental.api;

import api.invoking.InvokeHelper;
import experimental.provider.ExtraProvider;
import experimental.provider.ExtraProviders;
import experimental.provider.InteractionType;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.RS2Widget;

public class Magic {

    public static org.osbot.rs07.api.Magic get() {
        return ExtraProviders.getContext().getMagic();
    }

    public static boolean castOn(MagicSpell magicSpell, Entity entity) {
        if (magicSpell == null || entity == null || !entity.exists()) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefault(magicSpell, entity);
        } else if (InteractionType.INVOKE == interactionType) {
            return invoke(magicSpell, entity);
        }

        return false;
    }

    private static boolean invoke(MagicSpell magicSpell, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final InvokeHelper invokeHelper = ctx.getHelpers().getInvokeHelper();
        final RS2Widget spell = ctx.getMagic().getSpellWidget(magicSpell);
        return spell != null && invokeHelper.invokeOn(spell, entity);
    }

    private static boolean osbotDefault(MagicSpell magicSpell, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getMagic().castSpellOnEntity(magicSpell, entity);
    }

}
