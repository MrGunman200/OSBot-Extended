package testing;

import api.invoking.InvokeHelper;
import api.movement.Reachable;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import testing.api.script.LoopScript;
import api.util.Sleep;

//@ScriptManifest(info = "", logo = "", name = "TestWCScript", author = "", version = 0.0)
public class TestWCScript extends LoopScript {

    private final Area chopArea = new Area(3206, 3238, 3184, 3252);
    private final Filter<Item> axeFilter = i -> i != null && i.getName() != null && i.getName().contains("axe");
    private InvokeHelper invokeHelper;

    public void onStart() throws InterruptedException {
        super.onStart();
        invokeHelper = getHelpers().getInvokeHelper();
        getExtraVars().setFpsTarget(3);
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
                && Reachable.canReach(this, o)
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
                invokeHelper.invoke(item, "Drop");
            }
        }
    }

    private void chopTree(RS2Object tree) {
        if (invokeHelper.invoke(tree, "Chop down")) {
            Sleep.until(()-> !tree.exists(), ()-> myPlayer().isAnimating(), 1_800);
        }
    }

}
