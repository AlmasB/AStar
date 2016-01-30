package com.almasb.astar;

import java.util.Arrays;
import java.util.Collections;

/**
 * A 2d maze.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class Maze {
    private final int width;
    private final int height;
    private final int[][] maze;
    private MazeCell[][] mazeCells;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MazeCell[][] getMaze() {
        return mazeCells;
    }

    public MazeCell getMazeCell(int x, int y) {
        return mazeCells[x][y];
    }

    /**
     * Constructs a new maze with given width and height.
     *
     * @param width maze width
     * @param height maze height
     */
    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        maze = new int[width][height];
        mazeCells = new MazeCell[width][height];
        generateMaze(0, 0);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                MazeCell cell = new MazeCell(j, i);
                if ((maze[j][i] & 1) == 0) cell.setTopWall(true);
                if ((maze[j][i] & 8) == 0) cell.setLeftWall(true);

                mazeCells[j][i] = cell;
            }
        }
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, width) && between(ny, height) && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    }
}
