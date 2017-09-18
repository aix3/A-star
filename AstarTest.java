package astar;

import java.util.Stack;

/**
 * Created by xiaoxiao on 17-9-15.
 */
public class AstarTest {

    static int[][] map = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
    };

    public static void main(String[] args) {
        Astar.Point start = Astar.Point.p(4, 1);
        Astar.Point end = Astar.Point.p(4, 5);

        Stack<Astar.Point> path = Astar.astar(map, start, end, Astar.h);
        path.forEach(System.out::println);
    }
}
