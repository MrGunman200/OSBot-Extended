package api.wrapper.playground;

import api.wrapper.playground.model.EPlayer;
import org.osbot.rs07.api.Players;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

import java.util.LinkedList;
import java.util.List;

public class EPlayers extends Players {

    public EPlayers(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public List<Player> getAll() {
        final List<Player> raws = super.getAll();
        final List<Player> extras = new LinkedList<>();

        for (Player raw : raws) {
            if (raw != null) {
                extras.add(new EPlayer(raw));
            }
        }

        return extras;
    }

    @Override
    public List<Player> get(int x, int y) {
        final List<Player> raws = super.get(x, y);
        final List<Player> extras = new LinkedList<>();

        for (Player raw : raws) {
            if (raw != null) {
                extras.add(new EPlayer(raw));
            }
        }

        return extras;
    }

    @Override
    public Player getLocalPlayer(int index) {
        Player player = super.getLocalPlayer(index);

        if (player != null) {
            player = new EPlayer(player);
        }

        return player;
    }

}
