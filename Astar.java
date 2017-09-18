package astar;

import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by xiaoxiao on 17-9-15.
 */
public class Astar {

    static Heuristic h = new Heuristic() {
        @Override
        public int f(int g, int h) {
            return g + h;
        }

        @Override
        public int h(Point src, Point dst) {
            int manhattan = Math.abs(src.x - dst.x) + Math.abs(src.y - dst.y);
            return manhattan <= 1 ? manhattan * 10 : 14;
        }

        @Override
        public int g(Point src, Point end) {
            return (Math.abs(src.x - end.x) + Math.abs(src.y - end.y)) * 10;
        }

        @Override
        public boolean invalid(int n) {
            return n == 1;
        }
    };

    static int[][] recVector = {
            {1, 0}, {-1, 0},
            {0, 1}, {0, -1},
            {1, 1}, {-1, -1},
            {1, -1}, {-1, 1}
    };

    static Stack<Point> astar(int[][] map, Point start, Point end, Heuristic heuristic) {
        int ml = map.length;

        BitSet closeList = new BitSet(ml * map[0].length);
        BitSet openList2 = (BitSet) closeList.clone();

        Stack<Point> path = new Stack<>();
        path.push(point(start, end, heuristic, start.x, start.y));

        Point po;
        int i = 1;
        do {
            Queue<Point> openList = openList(map, start, end, heuristic, closeList);

            closeList.set(start.x * ml + start.y);

            start = po = openList.poll();
            if (openList2.get(po.x * ml + po.y)) {
                path.pop();
            }
            path.push(po);
            if (i % 2 == 0) {
                closeList.or(openList2);
                openList2.clear();
            }
            openList.forEach(p -> openList2.set(p.x * ml + p.y));
            i++;
        } while (po.x != end.x || po.y != end.y);
        return path;
    }

    private static Queue<Point> openList(int[][] map, Point start, Point end, Heuristic heuristic, BitSet closeList) {
        Queue<Point> openList = new PriorityQueue<>();
        for (int[] vector : recVector) {
            int x = start.x + vector[0];
            int y = start.y + vector[1];
            if (x >= 0 && x < map.length && y >= 0 && y < map[x].length) {
                if (heuristic.invalid(map[x][y]) || closeList.get(x * map.length + y))
                    continue;
                openList.add(point(start, end, heuristic, x, y));
            }
        }
        return openList;
    }

    private static Point point(Point start, Point end, Heuristic heuristic, int x, int y) {
        Point p = Point.p(x, y);
        p.g = heuristic.g(p, end);
        p.h = heuristic.h(start, p);
        p.f = heuristic.f(p.g, p.h);
        return p;
    }

    interface Heuristic {
        int f(int g, int h);

        int h(Point src, Point dst);

        int g(Point src, Point end);

        boolean invalid(int n);
    }

    static class Point implements Comparable<Point> {
        int x, y;
        int g, h, f;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static Point p(int x, int y) {
            return new Point(x, y);
        }

        @Override
        public int compareTo(Point point) {
            return this.f - point.f;
        }

        @Override
        public String toString() {
            return "Point{x=" + x + ", y=" + y + ", g=" + g + ", h=" + h + ", f=" + f + '}';
        }
    }
}