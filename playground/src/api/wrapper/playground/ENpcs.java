package api.wrapper.playground;

import api.wrapper.playground.model.ENpc;
import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;

import java.util.LinkedList;
import java.util.List;

public class ENpcs extends NPCS {

    public ENpcs(MethodProvider methodProvider) {
        this.exchangeContext(methodProvider.getBot());
    }

    @Override
    public List<NPC> getAll() {
        final List<NPC> raws = super.getAll();
        final List<NPC> extras = new LinkedList<>();

        for (NPC raw : raws) {
            if (raw != null) {
                extras.add(new ENpc(raw));
            }
        }

        return extras;
    }

    @Override
    public List<NPC> get(int x, int y) {
        final List<NPC> raws = super.get(x, y);
        final List<NPC> extras = new LinkedList<>();

        for (NPC raw : raws) {
            if (raw != null) {
                extras.add(new ENpc(raw));
            }
        }

        return extras;
    }

    @Override
    public NPC getLocalNPC(int index) {
        NPC npc = super.getLocalNPC(index);

        if (npc != null) {
            npc = new ENpc(npc);
        }

        return npc;
    }

}
