import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by dupdob on 23/06/2017.
 */
public class Tester {
    public static void main(String [] args) {

        Stopwatch stopwatch = new Stopwatch();
        StdOut.printf("Elpased time: %.2f\n", stopwatch.elapsedTime());

    }

    private static int[] readPoints(String file) {
        In source = new In(file);

        int k = source.readInt();
        int[] points = new int[k];
        for (int i = 0; i < k; i++) {
            points[i] = source.readInt();
        }
        return points;
    }
}
