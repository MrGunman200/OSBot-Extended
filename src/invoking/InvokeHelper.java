package invoking;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import java.awt.event.MouseEvent;

@SuppressWarnings("unused")
public class InvokeHelper extends MethodProvider {

    public InvokeHelper(MethodProvider context) {
        this.exchangeContext(context.getBot());
    }

    public InvokeHelper(MethodProvider context, boolean sendClick) {
        this.exchangeContext(context.getBot());
        this.sendClick = sendClick;
    }

    private boolean sendClick = true;

    public boolean isSendClick() {
        return sendClick;
    }

    public void setSendClick(boolean value) {
        sendClick = value;
    }

    private void fireEvent(int event) {
        final int screenX = 0;
        final int screenY = 0;
        getBot().getMouseEventHandler().generateBotMouseEvent(
                event,
                System.currentTimeMillis(),
                MouseEvent.BUTTON1_DOWN_MASK,
                screenX, screenY,
                1, false, MouseEvent.BUTTON1, true);
    }

    private void sendClick() {
        getMouse().move(0, 0);
        fireEvent(MouseEvent.MOUSE_PRESSED);
        fireEvent(MouseEvent.MOUSE_RELEASED);
        fireEvent(MouseEvent.MOUSE_CLICKED);
    }

    // Action and Target Name are irrelevant (Client sided)
    // Screen X and Y are just visual (Client sided)
    public boolean invoke(int param0, int param1, int opcode, int id, int itemId) {
        return invoke(param0, param1, opcode, id, itemId, "", "", -1, -1);
    }

    /*
     * If you want/need click here, invokeMenuAction doesn't send any mouse data
     * Jagex only tracks click's x, y, and time since last click
     * They do track if it was a middle mouse click, Jagex doesn't seem
     * to care if it's a right or left click only middle click or not
     *
     * Input data (mouse or key) is currently needed to reset 5 minute
     * client side logout timer and 15-20 minute server sided combat timer
     */
    public boolean invoke(int param0, int param1, int opcode, int id, int itemId, String action, String targetName, int screenX, int screenY) {
        try {
            if (isSendClick()) {
                sendClick();
            }

            getClient().accessor.invokeMenuAction(param0, param1, opcode, id, itemId, action, targetName, screenX, screenY);
            return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
            log(e);
        }

        return false;
    }

    public boolean invokeWalking(int sceneX, int sceneY) {
        try {
            if (isSendClick()) {
                sendClick();
            }

            getClient().accessor.invokeWalking(sceneX, sceneY);
            return true;
        } catch (Exception | Error e) {
            e.printStackTrace();
            log(e);
        }

        return false;
    }

    public boolean invokeWalking(Area area) {
        return invokeWalking(area.getRandomPosition());
    }

    public boolean invokeWalking(Position position) {
        final int sceneX = position.getLocalX(getBot());
        final int sceneY = position.getLocalY(getBot());
        return invokeWalking(sceneX, sceneY);
    }

    public boolean invokeDialogue(RS2Widget widget) {
        final int id = 0;
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        final int opcode = MenuAction.WIDGET_CONTINUE.getId();
        return invoke(param0, param1, opcode, id, widget.getItemId());
    }

    public boolean invokeDialogue(RS2Widget widget, int opcode, int identifier) {
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        return invoke(param0, param1, opcode, identifier, widget.getItemId());
    }

    public boolean invokeDialogue(int widgetIndex, int widgetId) {
        return invoke(widgetIndex, widgetId, MenuAction.WIDGET_CONTINUE.getId(), 0, -1);
    }

    public boolean invokeDialogue(int widgetIndex, int widgetId, int itemId) {
        return invoke(widgetIndex, widgetId, MenuAction.WIDGET_CONTINUE.getId(), 0, itemId);
    }

    public boolean invokeDialogue(int widgetIndex, int widgetId, int identifier, int itemId) {
        return invoke(widgetIndex, widgetId, MenuAction.WIDGET_CONTINUE.getId(), identifier, itemId);
    }

    public boolean invokeOn(RS2Widget widget, GroundItem groundItem) {
        return invokeUse(widget) && invoke(groundItem, -5);
    }

    public boolean invokeOn(RS2Widget widget, RS2Object object) {
        return invokeUse(widget) && invoke(object, -5);
    }

    public boolean invokeOn(RS2Widget widget, NPC npc) {
        return invokeUse(widget) && invoke(npc, -5);
    }

    public boolean invokeOn(RS2Widget widget, Player player) {
        return invokeUse(widget) && invoke(player, -5);
    }

    public boolean invokeOn(RS2Widget widget, RS2Widget otherWidget) {
        final int id = 0;
        final int param0 = otherWidget.getThirdLevelId();
        final int param1 = otherWidget.getId();
        final int opcode = MenuAction.WIDGET_TARGET_ON_WIDGET.getId();
        final int itemId = otherWidget.getItemId();
        return invokeUse(widget) && invoke(param0, param1, opcode, id, itemId);
    }

    public boolean invokeUse(RS2Widget widget) {
        final int id = 0;
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        final int opcode = MenuAction.WIDGET_TARGET.getId();
        final int itemId = widget.getItemId();
        return invoke(param0, param1, opcode, id, itemId);
    }

    public boolean invoke(RS2Widget widget, int opcode, int identifier) {
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        return invoke(param0, param1, opcode, identifier, widget.getItemId());
    }

    public boolean invoke(Item item, String action) {
        if (action.equalsIgnoreCase("drop")) {
            return invoke(item, -1);
        } else if (action.equalsIgnoreCase("equip")) {
            return invoke(item, -2);
        } else if (action.equalsIgnoreCase("examine")) {
            return invoke(item, -3);
        } else if (action.equalsIgnoreCase("use") || action.equalsIgnoreCase("cast")) {
            return invoke(item, -5);
        }

        return invoke(item, getIndexForAction(action, item.getOwner().getInteractActions()));
    }

    public boolean invoke(Item item, int opcode, int identifier) {
        final RS2Widget widget = item.getOwner();
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        return invoke(param0, param1, opcode, identifier, -1);
    }

    public boolean invoke(Item item, int index) {
        final RS2Widget widget = item.getOwner();
        final int param0 = widget.getThirdLevelId();
        final int param1 = widget.getId();
        int opcode = -1;
        int id = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_WIDGET.getId();
                id = 0;
                break;
            case -3:
                opcode = ItemOpCodes.EXAMINE.getMenuAction().getId();
                id = ItemOpCodes.EXAMINE.getIdentifier();
                break;
            case -2:
                // I think this is old/outdated I can't remember or find anything with Equip action
                opcode = ItemOpCodes.EQUIP.getMenuAction().getId();
                id = ItemOpCodes.EQUIP.getIdentifier();
                break;
            case -1:
                opcode = ItemOpCodes.DROP.getMenuAction().getId();
                id = ItemOpCodes.DROP.getIdentifier();
                break;
            case 0:
                opcode = ItemOpCodes.FIRST_ACTION.getMenuAction().getId();
                id = ItemOpCodes.FIRST_ACTION.getIdentifier();
                break;
            case 1:
                opcode = ItemOpCodes.SECOND_ACTION.getMenuAction().getId();
                id = ItemOpCodes.SECOND_ACTION.getIdentifier();
                break;
            case 2:
                opcode = ItemOpCodes.THIRD_ACTION.getMenuAction().getId();
                id = ItemOpCodes.THIRD_ACTION.getIdentifier();
                break;
            case 3:
                opcode = ItemOpCodes.FOURTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.FOURTH_ACTION.getIdentifier();
                break;
            case 4:
                opcode = ItemOpCodes.FIFTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.FIFTH_ACTION.getIdentifier();
                break;
            case 5:
                opcode = ItemOpCodes.SIXTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.SIXTH_ACTION.getIdentifier();
                break;
            case 6:
                opcode = ItemOpCodes.SEVENTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.SEVENTH_ACTION.getIdentifier();
                break;
            case 7:
                opcode = ItemOpCodes.EIGHTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.EIGHTH_ACTION.getIdentifier();
                break;
            case 8:
                opcode = ItemOpCodes.NINTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.NINTH_ACTION.getIdentifier();
                break;
            case 9:
                opcode = ItemOpCodes.TENTH_ACTION.getMenuAction().getId();
                id = ItemOpCodes.TENTH_ACTION.getIdentifier();
                break;
        }

        return invoke(param0, param1, opcode, id, item.getId());
    }

    public boolean invoke(GroundItem groundItem, String action) {
        return invoke(groundItem, getIndexForAction(action, groundItem.getActions()));
    }

    public boolean invoke(GroundItem groundItem, int opcode, int identifier)  {
        final int param0 = groundItem.getLocalX();
        final int param1 = groundItem.getLocalY();
        return invoke(param0, param1, opcode, identifier, -1);
    }

    public boolean invoke(GroundItem groundItem, int index) {
        final int id = groundItem.getId();
        final int param0 = groundItem.getLocalX();
        final int param1 = groundItem.getLocalY();
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_GROUND_ITEM.getId();
                break;
            case 0:
                opcode = MenuAction.GROUND_ITEM_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.GROUND_ITEM_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.GROUND_ITEM_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.GROUND_ITEM_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.GROUND_ITEM_FIFTH_OPTION.getId();
                break;
        }

        return invoke(param0, param1, opcode, id, -1);
    }

    public boolean invoke(Player player, String action) {
        // These actions are independent of getActions
        if (action.equalsIgnoreCase("trade")) {
            return invoke(player, -2);
        } else if (action.equalsIgnoreCase("follow")) {
            return invoke(player, -3);
        }

        return invoke(player, getIndexForAction(action, player.getActions()));
    }

    public boolean invoke(Player player, int opcode, int identifier) {
        return invoke(0, 0, opcode, identifier, -1);
    }

    public boolean invoke(Player player, int index) {
        final int id = player.getId();
        final int param0 = 0;
        final int param1 = 0;
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_PLAYER.getId();
                break;
            case -3:
                opcode = MenuAction.FOLLOW.getId();
                break;
            case -2:
                opcode = MenuAction.TRADE.getId();
                break;
            case 0:
                opcode = MenuAction.PLAYER_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.PLAYER_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.PLAYER_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.PLAYER_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.PLAYER_FIFTH_OPTION.getId();
                break;
            case 5:
                opcode = MenuAction.PLAYER_SIXTH_OPTION.getId();
                break;
            case 6:
                opcode = MenuAction.PLAYER_SEVENTH_OPTION.getId();
                break;
            case 7:
                opcode = MenuAction.PLAYER_EIGHTH_OPTION.getId();
                break;
        }

        return invoke(param0, param1, opcode, id, -1);
    }

    public boolean invoke(NPC npc, String action) {
        return invoke(npc, getIndexForAction(action, npc.getActions()));
    }

    public boolean invoke(NPC npc, int opcode, int identifier) {
        return invoke(0, 0, opcode, identifier, -1);
    }

    public boolean invoke(NPC npc, int index) {
        final int id = npc.getIndex();
        final int param0 = 0;
        final int param1 = 0;
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_NPC.getId();
                break;
            case 0:
                opcode = MenuAction.NPC_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.NPC_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.NPC_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.NPC_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.NPC_FIFTH_OPTION.getId();
                break;
        }

        return invoke(param0, param1, opcode, id, -1);
    }

    public boolean invoke(RS2Object object, String action) {
        return invoke(object, getIndexForAction(action, object.getActions()));
    }

    public boolean invoke(RS2Object object, int opcode, int identifier) {
        final int param0 = object.getLocalX();
        final int param1 = object.getLocalY();
        return invoke(param0, param1, opcode, identifier, -1);
    }

    public boolean invoke(RS2Object object, int index) {
        final int id = object.getId();
        final int param0 = object.getLocalX();
        final int param1 = object.getLocalY();
        int opcode = -1;

        switch (index) {
            case -5:
                opcode = MenuAction.WIDGET_TARGET_ON_GAME_OBJECT.getId();
                break;
            case 0:
                opcode = MenuAction.GAME_OBJECT_FIRST_OPTION.getId();
                break;
            case 1:
                opcode = MenuAction.GAME_OBJECT_SECOND_OPTION.getId();
                break;
            case 2:
                opcode = MenuAction.GAME_OBJECT_THIRD_OPTION.getId();
                break;
            case 3:
                opcode = MenuAction.GAME_OBJECT_FOURTH_OPTION.getId();
                break;
            case 4:
                opcode = MenuAction.GAME_OBJECT_FIFTH_OPTION.getId();
                break;
        }

        return invoke(param0, param1, opcode, id, -1);
    }

    public int getIndexForAction(String targetAction, String[] actions) throws IndexOutOfBoundsException {
        for (int i = 0; i < actions.length; i++) {
            final String action = actions[i];
            if (action != null) {
                if (action.equals(targetAction)) {
                    return i;
                }
            }
        }

        throw new IndexOutOfBoundsException("Action index couldn't be found");
    }

}
