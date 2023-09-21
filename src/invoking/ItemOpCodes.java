package invoking;

public enum ItemOpCodes {
    /*
     * Idk if any of this is correct 100%
     */

    DROP(MenuAction.CC_OP_LOW_PRIORITY, 7),
    EXAMINE(MenuAction.CC_OP_LOW_PRIORITY, 10),
    USE(MenuAction.WIDGET_TARGET, 0),
    ITEM_ON_ITEM(MenuAction.WIDGET_TARGET_ON_WIDGET, 0),
    EQUIP(MenuAction.CC_OP, 1),
    FIRST_ACTION(MenuAction.CC_OP, 1),
    SECOND_ACTION(MenuAction.CC_OP, 2),
    THIRD_ACTION(MenuAction.CC_OP, 3),
    FOURTH_ACTION(MenuAction.CC_OP, 4),
    FIFTH_ACTION(MenuAction.CC_OP, 5),
    SIXTH_ACTION(MenuAction.CC_OP_LOW_PRIORITY, 6),
    SEVENTH_ACTION(MenuAction.CC_OP_LOW_PRIORITY, 7),
    EIGHTH_ACTION(MenuAction.CC_OP_LOW_PRIORITY, 8),
    NINTH_ACTION(MenuAction.CC_OP_LOW_PRIORITY, 9),
    TENTH_ACTION(MenuAction.CC_OP_LOW_PRIORITY, 10);

    ItemOpCodes(MenuAction menuAction, int identifier) {
        this.menuAction = menuAction;
        this.identifier = identifier;
    }

    private final MenuAction menuAction;
    private final int identifier;

    public MenuAction getMenuAction() {
        return menuAction;
    }

    public int getIdentifier() {
        return identifier;
    }
}
