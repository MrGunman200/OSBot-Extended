package api.data.vars;

import api.provider.ExtraProviders;
import org.osbot.rs07.script.MethodProvider;

public enum Varbits {

    /*
     Run the follow code in RuneLite's dev tools Shell to retrieve the data
     VarbitComposition comp = client.getVarbit(varbit);

     int config = comp.getIndex();
     int msb = comp.getMostSignificantBit();
     int lsb = comp.getLeastSignificantBit();

     log.info("Config: {} | MSB: {} | LSB: {}", config, msb, lsb);
    */

    CUT_SCENE(1021, 6, 6, 542),
    BLAST_FURNACE_MINI_GAME_TELEPORT(0, 0, 0, 575),
    DRAYNOR_MANOR_BASEMENT_DOOR_1_STATE(668, 7, 7, 1801),
    DRAYNOR_MANOR_BASEMENT_DOOR_2_STATE(668, 2, 2, 1796),
    DRAYNOR_MANOR_BASEMENT_DOOR_3_STATE(668, 8, 8, 1802),
    DRAYNOR_MANOR_BASEMENT_DOOR_4_STATE(668, 3, 3, 1797),
    DRAYNOR_MANOR_BASEMENT_DOOR_5_STATE(668, 6, 6, 1800),
    DRAYNOR_MANOR_BASEMENT_DOOR_6_STATE(668, 1, 1, 1795),
    DRAYNOR_MANOR_BASEMENT_DOOR_7_STATE(668, 0, 0, 1794),
    DRAYNOR_MANOR_BASEMENT_DOOR_8_STATE(668, 5, 5, 1799),
    DRAYNOR_MANOR_BASEMENT_DOOR_9_STATE(668, 4, 4, 1798),
    HOUSE_LOCATION(738, 4, 0, 2187),
    BARBARIAN_ASSAULT_MINI_GAME_TELEPORT(0, 0, 0, 3251),
    KUDOS(1010, 10, 0, 3637),
    FAIRY_RING_DIAL_ADCB(816, 1, 0, 3985), //Left dial
    FAIRY_RIGH_DIAL_ILJK(816, 3, 2, 3986), //Middle dial
    FAIRY_RING_DIAL_PSRQ(816, 5, 4, 3987), //Right dial
    DIARY_FREMENNIK_HARD(1189, 12, 12, 4493),
    DIARY_FREMENNIK_ELITE(1189, 13, 13, 4494),
    VEOS_HAS_TALKED_TO_BEFORE(1317, 31, 31, 4897),
    QUEST_THE_CORSAIR_CURSE(1677, 5, 0, 6071),
    QUEST_X_MARKS_THE_SPOT(2111, 5, 0, 8063);

    private final int configId;
    private final int msb;
    private final int lsb;
    private final int varbit;

    Varbits(int configId, int msb, int lsb, int varbit) {
        this.configId = configId;
        this.msb = msb;
        this.lsb = lsb;
        this.varbit = varbit;
    }

    public int getValue() {
        return getValue(ExtraProviders.getContext());
    }

    public int getValue(MethodProvider ctx) {
        final int mask = (1 << ((msb - lsb) + 1)) - 1;
        final int configValue = ctx.getConfigs().get(configId);
        return (configValue >> lsb) & mask;
    }

}
