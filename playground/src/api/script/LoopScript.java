package api.script;

import api.provider.ExtraProvider;
import api.wrapper.playground.*;
import org.osbot.rs07.listener.GameTickListener;
import org.osbot.rs07.script.MethodProvider;

public abstract class LoopScript extends ExtraProvider {

    private final GameTickListener gameTickListener = () -> {
        final long curTick = getExtraBot().getGameTick();
        getExtraBot().setGameTick(curTick + 1);
    };

    @Override
    public void onStart() throws InterruptedException {
        wrapMethods();
        getHelpers().setHelpers();
        getBot().addGameTickListener(gameTickListener);
        getExtraBot().methodProvider = this;
    }

    @Override
    public void onExit() throws InterruptedException {
        //unwrapMethods();
        getBot().removeGameTickListener(gameTickListener);
    }

    private void wrapMethods() {
        getBot().getMethods().objects = new EObjects(getBot().getMethods(), this);
        getBot().getMethods().groundItems = new EGroundItems(this);
        getBot().getMethods().npcs = new ENpcs(this);
        getBot().getMethods().players = new EPlayers(this);
        getBot().getMethods().widgets = new EWidgets(this);
        getBot().getMethods().inventory = new EInventory(this);
        getBot().getMethods().bank = new EBank(this);
        getBot().getMethods().depositBox = new EDepositBox(this);
        getBot().getMethods().prayer = new EPrayer(this);
        getBot().getMethods().logoutTab = new ELogoutTab(this);
        getBot().getMethods().worlds = new EWorlds(this);
        getBot().getMethods().magic = new EMagic(this);
        getBot().getMethods().tabs = new ETabs(this);
        context();
    }

    // Idk if this even works
    private void unwrapMethods() {
        final MethodProvider mp = ((EObjects) getBot().getMethods().objects).originalProvider;

        getBot().getMethods().objects = mp.objects;
        getBot().getMethods().groundItems = mp.groundItems;
        getBot().getMethods().npcs = mp.npcs;
        getBot().getMethods().players = mp.players;
        getBot().getMethods().widgets = mp.widgets;
        getBot().getMethods().inventory = mp.inventory;
        getBot().getMethods().bank = mp.bank;
        getBot().getMethods().depositBox = mp.depositBox;
        getBot().getMethods().prayer = mp.prayer;
        getBot().getMethods().logoutTab = mp.logoutTab;
        getBot().getMethods().worlds = mp.worlds;
        getBot().getMethods().magic = mp.magic;
        getBot().getMethods().tabs = mp.tabs;
        context();
    }

    // Might be excessive
    private void context() {
        objects.exchangeContext(getBot());
        groundItems.exchangeContext(getBot());
        npcs.exchangeContext(getBot());
        players.exchangeContext(getBot());
        widgets.exchangeContext(getBot());
        inventory.exchangeContext(getBot());
        bank.exchangeContext(getBot());
        depositBox.exchangeContext(getBot());
        prayer.exchangeContext(getBot());
        logoutTab.exchangeContext(getBot());
        worlds.exchangeContext(getBot());
        magic.exchangeContext(getBot());
        tabs.exchangeContext(getBot());
        exchangeContext(getBot());
    }

}
