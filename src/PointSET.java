import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return points.isEmpty();
    }                      // is the set empty?

    public int size() {
        return points.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }           // does the set contain point p?

    public void draw() {
        for (Point2D p : this.points) {
            p.draw();
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> inRect = new ArrayList<>();
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        for (Point2D p : this.points) {
            if (rect.contains(p)) {
                inRect.add(p);
            }
        }
        return inRect;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        double closestDist = Double.MAX_VALUE;
        Point2D closest = null;

        for (Point2D q : this.points) {
            double dist = p.distanceSquaredTo(q);
            if (dist < closestDist) {
                closestDist = dist;
                closest = q;
            }
        }
        return closest;
    }            // a nearest neighbor in the set to point p; null if the set is empty

}
