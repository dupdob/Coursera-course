import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }                         // constructs the point (x, y)

    public void draw() {
        StdDraw.point(x, y);
    }                              // draws this point

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }                   // draws the line segment from this point to that point

    public String toString() {
        return "(" + x + ", " + y + ")";
    }                           // string representation

    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y == that.y) {
            if (this.x < that.x) {
                return -1;
            } else if (this.x == that.x) {
                return 0;
            }
        }
        return 1;
    }    // compare two points by y-coordinates, breaking ties by x-coordinates

    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if (that.y == this.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else if (that.y == this.y) {
            return 0.0;
        }
        return ((double) (that.y - this.y))/((double) (that.x - this.x));
    }      // the slope between this point and that point

    public Comparator<Point> slopeOrder() {
        return new Compare();
    }             // compare two points by slopes they make with this point

    private class Compare implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            final double slope1 = slopeTo(o1);
            final double slope2 = slopeTo(o2);
            if (slope1 > slope2) {
                return 1;
            } else if (slope1 < slope2) {
                return -1;
            }
            return 0;
        }
    }
}