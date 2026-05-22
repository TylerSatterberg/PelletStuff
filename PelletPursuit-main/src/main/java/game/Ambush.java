package game;

import javafx.scene.paint.Color;

// Ambush — tries to cut the player off by targeting ahead of where they are heading.
public class Ambush extends Ghost {

    // Number of tiles ahead of the player's current direction to target.
    // Try values between 2 and 8.
    private static final int LOOK_AHEAD = 4;

    public Ambush(GameMap map) {
        super(map, GameMap.Tile.SPAWN_G3, 1.7);
    }

    @Override
    public String getName() { return "Ambush"; } // give your ghost a name

    @Override
    protected int[] chooseTarget(Player player, GameMap map) {
        // TODO (Base): Implement Ambush's personality.
        //
        // Ambush targets a point LOOK_AHEAD tiles ahead of the player's current
        // heading. Use player.getDx() and player.getDy() to find the heading,
        // then project that many tiles forward from the player's tile position.
        //
        // Make sure the target is always inside the maze — use Math.max and Math.min
        // to keep the column within [1, map.cols-2] and the row within [1, map.rows-2].
        //
        // When frightened, target a corner of the maze instead.
        //
        // How to verify: run the game and move in one direction — the orange ghost
        // should approach from in front of you rather than chasing from behind.
        int FutureX = Math.max(1, Math.min(player.getDx() + LOOK_AHEAD, map.rows - 2));
        int FutureY = Math.max(1, Math.min(player.getDy() + LOOK_AHEAD, map.cols - 2));


        if (frightened) {
            // Run away: target the corner farthest from the player
            int pc = player.col(map), pr = player.row(map);
            int targetCol, targetRow;
            if (pc < map.cols / 2) {
                targetCol = map.cols - 2;  // player on left  → flee right
            } else {
                targetCol = 1;             // player on right → flee left
            }
            if (pr < map.rows / 2) {
                targetRow = map.rows - 2;  // player on top    → flee bottom
            } else {
                targetRow = 1;             // player on bottom → flee top
            }
            return new int[]{ targetCol, targetRow };
        }

        return new int[]{ player.col(map) + FutureY, player.row(map) + FutureX };
    }

    // When chooseTarget() is working, add this ghost to the list in GameApp.java:
    //   new Ambush(map)

    @Override
    protected Color getBodyColor() { return Color.web("#ffb852"); }
}
