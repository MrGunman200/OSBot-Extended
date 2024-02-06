package api.wrapper.extra;

import api.invoking.InvokeHelper;
import api.provider.ExtraProvider;
import api.provider.ExtraProviders;
import api.provider.InteractionType;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.RS2Widget;

public class Magic {

    public static org.osbot.rs07.api.Magic get() {
        return ExtraProviders.getContext().getMagic();
    }

    public static boolean cast(MagicSpell magicSpell) {
        if (magicSpell == null) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefaultCast(magicSpell);
        } else if (InteractionType.INVOKE == interactionType) {
            return invokeCast(magicSpell);
        }

        return false;
    }

    public static boolean castOn(MagicSpell magicSpell, Entity entity) {
        if (magicSpell == null || entity == null || !entity.exists()) {
            return false;
        }

        final InteractionType interactionType = ExtraProviders.getContext().getExtraBot().getInteractionType();

        if (InteractionType.DEFAULT == interactionType) {
            return osbotDefaultCastOn(magicSpell, entity);
        } else if (InteractionType.INVOKE == interactionType) {
            return invokeCastOn(magicSpell, entity);
        }

        return false;
    }

    private static boolean invokeCastOn(MagicSpell magicSpell, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final InvokeHelper invokeHelper = ctx.getHelpers().getInvokeHelper();
        final RS2Widget spell = ctx.getMagic().getSpellWidget(magicSpell);
        return spell != null && invokeHelper.invokeOn(spell, entity);
    }

    private static boolean osbotDefaultCastOn(MagicSpell magicSpell, Entity entity) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getMagic().castSpellOnEntity(magicSpell, entity);
    }

    private static boolean invokeCast(MagicSpell magicSpell) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        final InvokeHelper invokeHelper = ctx.getHelpers().getInvokeHelper();
        final RS2Widget spell = ctx.getMagic().getSpellWidget(magicSpell);
        return spell != null && invokeHelper.invokeOn(spell);
    }

    private static boolean osbotDefaultCast(MagicSpell magicSpell) {
        final ExtraProvider ctx = ExtraProviders.getContext();
        return ctx.getMagic().castSpell(magicSpell);
    }

}
