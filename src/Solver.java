import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private final Board initial;
    private final Board twin;
    private SearchNode endNode = null;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initial;
        this.twin = this.initial.twin();
        solve();
    }          // find a solution to the initial board (using the A* algorithm)

    private void solve() {

        final MinPQ<SearchNode> queue = new MinPQ<>();
        final MinPQ<SearchNode> twinQueue = new MinPQ<>();
        queue.insert(SearchNode.buildInitial(this.initial));
        twinQueue.insert(SearchNode.buildInitial(this.twin));
        while (true) {
            if (stepAlgo(queue)) {
                break;
            }
            if (stepAlgo(twinQueue)) {
                this.endNode = null;
                break;
            }
        }
    }

    private boolean stepAlgo(MinPQ<SearchNode> inQueue) {
        final SearchNode searchNode = inQueue.delMin();
        if (searchNode.board.isGoal()) {
            this.endNode = searchNode;
            return true;
        }
        for (SearchNode next : searchNode.scanNeighbors()) {
            inQueue.insert(next);
        }
        return false;
    }

    public boolean isSolvable() {
        return this.endNode != null;
    }           // is the initial board solvable?

    public int moves() {
        int len = 0;
        SearchNode node = this.endNode;
        while (node != null) {
            len++;
            node = node.parentNode;
        }
        return len -1;
    }                    // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (this.endNode == null) {
            return null;
        }
        Stack<Board> solution = new Stack<>();
        SearchNode node = this.endNode;
        while (node != null) {
            solution.push(node.board);
            node = node.parentNode;
        }
        return solution;
    }     // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode parentNode;
        private final int priority;
        private final int pathLen;

        public SearchNode(Board board, SearchNode parentNode, int pathLen) {
            this.board = board;
            this.parentNode = parentNode;
            this.pathLen = pathLen;
            this.priority = board.manhattan() + this.pathLen;
        }

        public static SearchNode buildInitial(Board board) {
            return new SearchNode(board, null, 0);
        }

        public List<SearchNode> scanNeighbors() {
            List<SearchNode> result = new ArrayList<>(3);
            for (Board next : this.board.neighbors()) {
                if (this.parentNode != null && this.parentNode.board.equals(next)) {
                    // don't go back
                    continue;
                }
                result.add(new SearchNode(next, this, pathLen + 1));
            }
            return result;
        }

        @Override
        public int compareTo(SearchNode other) {
            return this.priority - other.priority;
        }
    }
}
