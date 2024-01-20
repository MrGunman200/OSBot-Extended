package experimental.api;

import experimental.provider.ExtraProviders;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.Player;

import java.util.Arrays;
import java.util.List;

public class LocalPlayer {

    public static Player get() {
        return ExtraProviders.getContext().myPlayer();
    }

    public static String getName() {
        final Player myPlayer = get();
        return myPlayer != null ? myPlayer.getName() : null;
    }

    public static Position getPosition() {
        final Player myPlayer = get();
        return myPlayer != null ? myPlayer.getPosition() : null;
    }

    public static Entity getInteracting() {
        final Player myPlayer = get();
        return myPlayer != null ? myPlayer.getInteracting() : null;
    }

    public static int getCombatLvl() {
        final Player myPlayer = get();
        return myPlayer != null ? myPlayer.getCombatLevel() : -1;
    }

    public static int getAnimation() {
        final Player myPlayer = get();
        return myPlayer != null ? myPlayer.getAnimation() : -1;
    }

    public static boolean isMoving() {
        final Player myPlayer = get();
        return myPlayer != null && myPlayer.isMoving();
    }

    public static boolean isAnimating() {
        final Player myPlayer = get();
        return myPlayer != null && myPlayer.isAnimating();
    }

    public static boolean isIdle() {
        final Player myPlayer = get();
        return myPlayer != null
                && !myPlayer.isMoving()
                && !myPlayer.isAnimating()
                && myPlayer.getInteracting() == null;
    }

    public static boolean isInteracting() {
        final Player myPlayer = get();
        return myPlayer != null && myPlayer.getInteracting() != null;
    }

    public static boolean atPosition(Position position) {
        final Player myPlayer = get();
        return myPlayer != null && myPlayer.getPosition().equals(position);
    }

    public static boolean contains(Area area) {
        final Player myPlayer = get();
        return myPlayer != null && area.contains(myPlayer);
    }

    public static boolean contains(List<Position> points) {
        final Player myPlayer = get();
        return myPlayer != null && points.contains(myPlayer.getPosition());
    }

    public static boolean contains(Position[] points) {
        final Player myPlayer = get();
        return myPlayer != null && Arrays.stream(points)
                .filter(p -> p.equals(myPlayer.getPosition()))
                .findFirst()
                .orElse(null) != null;
    }

}
