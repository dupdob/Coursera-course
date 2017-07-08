import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int n = 0;
    private final Random rnd;

    public RandomizedQueue() {
        array = (Item[]) new Object[2];
        n = 0;
        rnd = new Random();
    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        return this.n == 0;
    }                 // is the queue empty?
    public int size() {
        return this.n;
    }                        // return the number of items on the queue
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (n == array.length) {
            resize(2 * n);
        }
        array[n++] = item;
    }           // add the item

    private void resize(int newSize) {
        if (newSize < 2) {
            newSize = 2;
        }

        Item[] nextArray = (Item[]) new Object[newSize];
        for (int i = 0; i < n; i++) {
            nextArray[i] = array[i];
        }
        array = nextArray;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int id = rnd.nextInt(size());
        Item removed = array[id];
        array[id] = array[--n];
        array[n] = null;
        if (n < array.length / 4) {
            resize(array.length / 2);
        }
        return removed;
    }                    // remove and return a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int id = rnd.nextInt(size());
        return array[id];
    }                     // return (but do not remove) a ran   dom item
    public Iterator<Item> iterator() {
        return new MyIterator();
    }         // return an independent iterator over items in random order
    public static void main(String[] args) {

    }   // unit testing (optional)

    private class MyIterator implements Iterator<Item> {
        private int[] indexes = new int[n];
        private int id = 0;

        public MyIterator() {
            for (int i = 0; i < indexes.length; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return id < indexes.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[indexes[id++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
