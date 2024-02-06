package api.util;

import api.provider.ExtraProviders;

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

    /**
     * Only works when using ExtraProvider
     */
    public static void sleepTick() {
        sleepTicks(1);
    }

    /**
     * Only works when using ExtraProvider
     */
    public static void sleepTicks(int ticks) {
        try {
            ExtraProviders.getContext().sleepTicks(ticks);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Sleep Ticks only works when using ExtraProvider...");
            sleep(20);
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

    /**
     * Only works when using ExtraProvider
     */
    public static boolean untilTick(BooleanSupplier condition, int timeout) {
        return untilTick(condition, ()-> false, timeout);
    }

    /**
     * Only works when using ExtraProvider
     */
    public static boolean untilTick(int timeout, BooleanSupplier condition) {
        return untilTick(condition, ()-> false, timeout);
    }

    /**
     * Only works when using ExtraProvider
     */
    public static boolean untilTick(BooleanSupplier condition, BooleanSupplier resetCondition, int timeout) {
        return untilTick(condition, resetCondition, timeout, DEFAULT_CYCLE_LIMIT);
    }

    /**
     * Only works when using ExtraProvider
     */
    public static boolean untilTick(BooleanSupplier condition, BooleanSupplier resetCondition, int timeout, int cycleLimit) {
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

                sleepTick();
            }

            return condition.getAsBoolean();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

}
