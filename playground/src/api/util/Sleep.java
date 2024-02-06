package api.util;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

public class Sleep {

    private static final int DEFAULT_POLLING = 600;
    private static final int DEFAULT_CYCLE_LIMIT = 100;

    public static void sleep(int time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            // I don't wanna deal with Interrupted Exception handling everywhere
            // but I need it to reset the execution when it happens
            throw new IndexOutOfBoundsException("Sleep Interrupted*");
        }
    }

    public static boolean until(BooleanSupplier condition, int timeout) {
        return until(condition, ()-> false, timeout, DEFAULT_POLLING);
    }

    public static boolean until(BooleanSupplier condition, int timeout, int polling) {
        return until(condition, ()-> false, timeout, polling);
    }

    public static boolean until(BooleanSupplier condition, BooleanSupplier resetCondition, int timeout) {
        return until(condition, resetCondition, timeout, DEFAULT_POLLING, DEFAULT_CYCLE_LIMIT);
    }

    public static boolean until(BooleanSupplier condition, BooleanSupplier resetCondition, int timeout, int polling) {
        return until(condition, resetCondition, timeout, polling, DEFAULT_CYCLE_LIMIT);
    }

    public static boolean until(BooleanSupplier condition, BooleanSupplier resetCondition, int timeout, int polling, int cycleLimit) {
        try {
            int resetCounter = 0;
            long startTime = System.currentTimeMillis();

            while ((System.currentTimeMillis() - startTime) < timeout && !condition.getAsBoolean()) {
                if (resetCounter >= cycleLimit) {
                    break;
                } else if (resetCondition.getAsBoolean()) {
                    startTime = System.currentTimeMillis();
                    resetCounter ++;
                }

                Sleep.sleep(polling);
            }

            return condition.getAsBoolean();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

}
