import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] inputPoints) {
        if (inputPoints == null) {
            throw new IllegalArgumentException();
        }
        Point[] points = Arrays.copyOf(inputPoints, inputPoints.length);

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i+1; j < points.length; j++) {
                Point q1 = points[j];
                double slope = p.slopeTo(q1);
                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                Comparator<Point> comp = p.slopeOrder();
                for (int k = j+1; k < points.length; k++) {
                    Point q2 = points[k];
                    if (comp.compare(q1, q2) != 0) {
                        continue;
                    }
                    for (int m = k+1; m < points.length; m++) {
                        Point q3 = points[m];
                        if (comp.compare(q1, q3) == 0) {
                            // we have a segment
                            LineSegment segment = new LineSegment(p, q3);
                            segments.add(segment);
                        }
                    }
                }

            }
        }
    }    // finds all line segments containing 4 points
    public int numberOfSegments() {
        return segments.size();
    }        // the number of line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }                // the line segments
}
