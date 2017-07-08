import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] blocks;
    private final int length;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
        length = blocks.length;
        this.blocks = cloneBoard(blocks);
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return this.blocks.length;
    }                 // board dimension n

    public int hamming() {
        int result = 0;
        int index = 0;
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                index++;
                if (this.blocks[i][j] == 0) {
                    continue;
                }
                if (this.blocks[i][j] != index) {
                    result++;
                }
            }
        }
        return result;
    }                   // number of blocks out of place

    private static int[][] cloneBoard(int[][] board) {
        int[][] twinBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                twinBoard[i][j] = board[i][j];
            }
        }
        return twinBoard;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                int tile = this.blocks[i][j];
                if (tile == 0) {
                    continue;
                }
                tile--;
                int row = tile / length;
                int col = tile % length;
                result += Math.abs(i - row) + Math.abs(j - col);
            }
        }
        return result;
    }                // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        int index = 1;
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                if (this.blocks[i][j] != index) {
                    if (this.blocks[i][j] > 0) {
                        return false;
                    }
                }
                index++;
            }
        }
        return true;
    }               // is this board the goal board?

    public Board twin() {
        Board twin = new Board(this.blocks);
        if (twin.blocks[0][0] == 0 || twin.blocks[0][1] == 0) {
            twin.swapBlock(1, 0, 1, 1);
        } else {
            twin.swapBlock(0, 0, 0, 1);
        }
        return twin;
    }                   // a board that is obtained by exchanging any pair of blocks

    private void swapBlock(int row1, int col1, int row2, int col2) {
        int temp = this.blocks[row1][col1];
        this.blocks[row1][col1] = this.blocks[row2][col2];
        this.blocks[row2][col2] = temp;
    }

    public boolean equals(Object y) {
        if (y == null || !y.getClass().equals(this.getClass())) {
            return false;
        }

        Board other = (Board) y;
        if (other.dimension() != this.dimension()) {
            return false;
        }
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                if (this.blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }       // does this board equal y?

    public Iterable<Board> neighbors() {
        List<Board> store = new ArrayList<>(4);
        int emptyRow = 0;
        int emptyCol = 0;
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                if (this.blocks[i][j] == 0) {
                    emptyCol = j;
                    emptyRow = i;
                    break;
                }
            }
        }
        Board temp;
        if (emptyRow > 0) {
            temp = new Board(this.blocks);
            temp.swapBlock(emptyRow, emptyCol, emptyRow - 1, emptyCol);
            store.add(temp);
        }
        if (emptyCol > 0) {
            temp = new Board(this.blocks);
            temp.swapBlock(emptyRow, emptyCol, emptyRow, emptyCol - 1);
            store.add(temp);
        }
        if (emptyRow < dimension() - 1) {
            temp = new Board(this.blocks);
            temp.swapBlock(emptyRow, emptyCol, emptyRow + 1, emptyCol);
            store.add(temp);
        }
        if (emptyCol < dimension() - 1) {
            temp = new Board(this.blocks);
            temp.swapBlock(emptyRow, emptyCol, emptyRow, emptyCol + 1);
            store.add(temp);
        }
        return store;

    }     // all neighboring boards

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println(args[0]);
        StdOut.println("hamming = " + initial.hamming());
        StdOut.println("manhattan = " + initial.manhattan());
        StdOut.println(initial);
    } // solve a slider puzzle (given below)

}
