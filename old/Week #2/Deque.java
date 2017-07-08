import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node firstNode = null;
    private Node lastNode = null;
    private int size;

    public Deque() {
    }                           // construct an empty deque

    public boolean isEmpty() {
        return size == 0;
    }                 // is the deque empty?

    public int size() {
        return size;
    }                        // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node currentFirst = this.firstNode;
        this.firstNode = new Node(currentFirst, null, item);
        if (currentFirst == null) {
            this.lastNode = this.firstNode;
        } else {
            currentFirst.prevNode = this.firstNode;
        }
        size++;
    }          // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node currentLast = this.lastNode;
        this.lastNode = new Node(null, this.lastNode, item);
        if (currentLast == null) {
            this.firstNode = this.lastNode;
        } else {
            currentLast.nextNode = this.lastNode;
        }
        size++;
    }           // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item result = this.firstNode.value;
        this.firstNode = this.firstNode.nextNode;
        if (this.firstNode == null) {
            this.lastNode = null;
        } else {
            this.firstNode.prevNode = null;
        }
        return result;
    }               // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item result = this.lastNode.value;
        this.lastNode = this.lastNode.prevNode;
        if (this.lastNode == null) {
            this.firstNode = null;
        } else {
            this.lastNode.nextNode = null;
        }
        return result;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeueIterator(this.firstNode);
    }        // return an iterator over items in order from front to end

    public static void main(String[] args) {

    }   // unit testing (optional)

    private class DequeueIterator implements Iterator<Item> {
        private Node currentNode;

        private DequeueIterator(Node node) {
            this.currentNode = node;
        }

        @Override
        public boolean hasNext() {
            return this.currentNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item value = currentNode.value;
            currentNode = currentNode.nextNode;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Node {
        private Node nextNode;
        private Node prevNode;
        private Item value;

        public Node(Node nextNode, Node prevNode, Item value) {
            this.nextNode = nextNode;
            this.prevNode = prevNode;
            this.value = value;
        }
    }
}
