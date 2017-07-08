import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private List<EndPoint> refs = new ArrayList<>();

    public FastCollinearPoints(Point[] inputs) {
        if (inputs == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] points = Arrays.copyOfRange(inputs, 0, inputs.length);
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        Point[] buffer = Arrays.copyOfRange(inputs, 0, inputs.length);
        for (int i = 0; i < points.length-3; i++) {
            Point p = points[i];
            StdRandom.shuffle(buffer, i, buffer.length - 1);
            sortSlopes(buffer, p, i, buffer.length - 1, true);
        }
    }   // finds all line segments containing 4 or more points

    private void tryAddSegment(Point begin, Point end, double slope) {
        boolean found = false;
        for (int i = 0; i < this.refs.size(); i++) {
            if (this.refs.get(i).getEnd() == end) {
                if (!this.refs.get(i).addSlop(slope)) {
                    return;
                }
                found = true;
                break;
            }
        }
        if (!found) {
            EndPoint ref = new EndPoint(end);
            ref.addSlop(slope);
            this.refs.add(ref);
        }
        segments.add(new LineSegment(begin, end));
    }

    private void sortSlopes(Point[] points, Point p, int lo, int hi, boolean lookForMin) {
        if (hi - lo < (lookForMin ? 1 : 2)) {
            return;
        }
        int lt = lo, gt = hi;
        double refSlope = p.slopeTo(points[lo]);
        int i = lo + 1;
        while (i <= gt) {
            double nextSlope = p.slopeTo(points[i]);
            if (nextSlope < refSlope) {
                exch(points, lt++, i++);
            } else if (nextSlope > refSlope) {
                exch(points, i, gt--);
            } else {
                i++;
            }
        }

        if (gt - lt > 1) {
            Point min, max;
            min = p;
            max = p;
            for (int j = lt; j <= gt; j++) {
                if (min.compareTo(points[j]) > 0) {
                    min = points[j];
                    if (max != p) {
                        break;
                    }
                }
                else if (max.compareTo(points[j]) < 0) {
                    max = points[j];
                    if (min != p) {
                        break;
                    }
                }
            }
            if (p == min || p == max) {
                tryAddSegment(min, max, refSlope);
            }
        }
        sortSlopes(points, p, lo, lt - 1, lookForMin);
        sortSlopes(points, p, gt + 1, hi, false);
    }

    private void exch(Point[] points, int i, int i1) {
        Point temp = points[i];
        points[i] = points[i1];
        points[i1] = temp;
    }

    public int numberOfSegments() {
        return segments.size();
    }        // the number of line segments

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }                // the line segments

    private static class EndPoint {
        private Point end;
        private ArrayList<Double> slopes = new ArrayList<>();

        public EndPoint(Point end) {
            this.end = end;
        }

        public Point getEnd() {
            return end;
        }

        public boolean addSlop(double slope) {
            final int i = Collections.binarySearch(this.slopes, slope);
            if (i >= 0) {
                return false;
            }
            this.slopes.add(-i-1, slope);
            return true;
        }
    }
}