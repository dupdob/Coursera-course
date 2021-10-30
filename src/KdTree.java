import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private final KdNode root;
    private int pointsCount = 0;

    public KdTree() {
        root = new KdNode(true);
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return size() == 0;
    }                      // is the set empty?

    public int size() {
        return pointsCount;
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (contains(p)) {
            return;
        }
        pointsCount++;
        root.insert(p);

    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return root.contains(p);
    }           // does the set contain point p?

    public void draw() {
        root.draw();
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> inRect = new ArrayList<>();
        RectHV currentRect = new RectHV(0, 0, 1, 1);

        root.scan(rect, inRect, currentRect);
        return inRect;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            return null;
        }
        return root.closest(p);
    }            // a nearest neighbor in the set to point p; null if the set is empty


    private class KdNode {
        private Point2D point;
        private KdNode left;
        private KdNode right;
        private final boolean isXBased;

        public KdNode(boolean isXBased) {
            this.isXBased = isXBased;
        }

        public void insert(Point2D p) {
            if (point == null) {
                // insert here
                this.point = p;
            } else {
                boolean goLeft = isXBased ? (p.x() < point.x()) : (p.y() < point.y());
                (goLeft ? getLeft() : getRight()).insert(p);
            }
        }

        public KdNode getLeft() {
            if (left == null) {
                left = new KdNode(!isXBased);
            }
            return left;
        }

        public KdNode getRight() {
            if (right == null) {
                right = new KdNode(!isXBased);
            }
            return right;
        }

        public boolean contains(Point2D p) {
            if (p == null || point == null) {
                return false;
            }
            if (p.equals(point)) {
                return true;
            }
            boolean goLeft = isXBased ? (p.x() < point.x()) : (p.y() < point.y());
            KdNode next = (goLeft ? left : right);
            return next != null && next.contains(p);
        }

        public void draw() {
            if (left != null) {
                left.draw();
            }
            if (point != null) {
                point.draw();
            }
            if (right != null) {
                right.draw();
            }
        }

        public void scan(RectHV rect, ArrayList<Point2D> inRect, RectHV currentRect) {
            if (!currentRect.intersects(rect) || point == null) {
                return;
            }
            if (isXBased) {
                if (left != null) {
                    left.scan(rect, inRect, new RectHV(currentRect.xmin(), currentRect.ymin(), point.x(), currentRect.ymax()));
                }
                if (right != null) {
                    right.scan(rect, inRect, new RectHV(point.x(), currentRect.ymin(), currentRect.xmax(), currentRect.ymax()));
                }
            } else {
                if (left != null) {
                    left.scan(rect, inRect, new RectHV(currentRect.xmin(), currentRect.ymin(), currentRect.xmax(), point.y()));
                }
                if (right != null) {
                    right.scan(rect, inRect, new RectHV(currentRect.xmin(), point.y(), currentRect.xmax(), currentRect.ymax()));
                }
            }
            if (rect.contains(point)) {
                inRect.add(point);
            }
        }

        public Point2D closest(Point2D p) {
            return findClosest(p, null, new RectHV(0, 0, 1, 1));
        }

        private Point2D findClosest(Point2D query, Point2D closest, RectHV currentRect) {
            if (point == null) {
                return null;
            }
            double closestDist;
            double pointDist = point.distanceSquaredTo(query);
            if (closest == null) {
                closest = point;
                closestDist = pointDist;
            } else {
                closestDist = closest.distanceSquaredTo(query);
                if (pointDist < closestDist) {
                    closest = point;
                    closestDist = pointDist;
                }
            }
            RectHV leftRect;
            RectHV rightRect;
            boolean leftFirst;
            if (isXBased) {
                leftRect = new RectHV(currentRect.xmin(), currentRect.ymin(), point.x(), currentRect.ymax());
                rightRect = new RectHV(point.x(), currentRect.ymin(), currentRect.xmax(), currentRect.ymax());
                leftFirst = query.x() < point.x();
            } else {
                leftRect = new RectHV(currentRect.xmin(), currentRect.ymin(), currentRect.xmax(), point.y());
                rightRect = new RectHV(currentRect.xmin(), point.y(), currentRect.xmax(), currentRect.ymax());
                leftFirst = query.y() < point.y();
            }
            if (left == null || leftRect.distanceSquaredTo(query) >= closestDist) {
                leftRect = null;
            }
            if (leftFirst && leftRect != null) {
                closest = left.findClosest(query, closest, leftRect);
            }
            if (right == null || rightRect.distanceSquaredTo(query) >= closest.distanceSquaredTo(query)) {
                rightRect = null;
            }
            if (rightRect != null) {
                closest = right.findClosest(query, closest, rightRect);
            }
            if (!leftFirst && leftRect != null) {
                if (left != null && leftRect.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = left.findClosest(query, closest, leftRect);
                }
            }
            return closest;
        }

    }
}
