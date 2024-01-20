package examples;

import experimental.api.Interaction;
import experimental.api.Inventory;
import experimental.api.Movement;
import experimental.provider.InteractionType;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import experimental.script.LoopScript;
import api.util.Sleep;

//@ScriptManifest(info = "", logo = "", name = "ExampleLoopedScript", author = "", version = 0.0)
public class ExampleLoopedScript extends LoopScript {

    private final Area chopArea = new Area(3206, 3238, 3184, 3252);
    private final Filter<Item> axeFilter = i -> i != null && i.getName() != null && i.getName().contains("axe");

    public void onStart() throws InterruptedException {
        super.onStart();
        getExtraBot().setFpsTarget(3);
        getExtraBot().setInteractionType(InteractionType.INVOKE);
    }

    public int onLoop() throws InterruptedException {
        final RS2Object tree = getTree();

        if (!hasAxe()) {
            getBot().getScriptExecutor().stop();
        } else if (getInventory().isFull()) {
            dropLogs();
        } else if (tree != null) {
            chopTree(tree);
        } else {
            getWalking().webWalk(chopArea);
        }

        return 600;
    }

    private RS2Object getTree() {
        final String treeName = getTreeName();
        return getObjects().closest(o ->
                o != null
                && o.getName() != null
                && o.getName().equals(treeName)
                && chopArea.contains(o.getPosition())
                && Movement.canReach(o)
        );
    }

    private String getTreeName() {
        return getSkills().getStatic(Skill.WOODCUTTING) >= 15 ? "Oak tree" : "Tree";
    }

    private boolean hasAxe() {
        return getInventory().contains(axeFilter) || getEquipment().contains(axeFilter);
    }

    private void dropLogs() {
        for (Item item : getInventory().getItems()) {
            if (item.getName().contains("ogs")) {
                Inventory.interact(item, "Drop");
            }
        }
    }

    private void chopTree(RS2Object tree) {
        if (Interaction.interact(tree, "Chop down")) {
            Sleep.untilTick(()-> !tree.exists(), ()-> myPlayer().isAnimating(), 1_800);
        }
    }

}
