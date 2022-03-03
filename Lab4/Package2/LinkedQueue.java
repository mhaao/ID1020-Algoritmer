package Package2;
/*
 *@author: code taken from Robert Sedgewick and Kevin Wayne.
 *Algorithms & Datastructures: KTH ID1021-HT21-1. Tillägg Murtadha Alobaidi
 *Lab4-Q2
 *LinkedQueue FIFO queue (linked list)from kapitel 1 FUNDAMENTALS .
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue<Item> implements Iterable<Item>
{
        private int n;         // number of elements on queue
        private Node first;    // beginning of queue
        private Node last;     // end of queue

        // helper linked list class
        private class Node {
            private Item item;
            private Node next;
        }

        //Initializes an empty queue.
        public LinkedQueue() {
            first = null;
            last  = null;
            n = 0;
        }

        /** Is this queue empty?
         *  @return true if this queue is empty; false otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this queue.
         * @return the number of items in this queue
         */
        public int size() {
            return n;
        }

        /** Adds the item to this queue.
         * @param item the item to add
         */
        public void enqueue(Item item) {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else           oldlast.next = last;
            n++;
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         * @return the item on this queue that was least recently added
         * @throws java.util.NoSuchElementException if this queue is empty
         */
        public Item dequeue() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            n--;
            if (isEmpty()) last = null;   // to avoid loitering
            return item;
        }

        /**
         * Returns an iterator that iterates over the items in this queue in FIFO order.
         * @return an iterator that iterates over the items in this queue in FIFO order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator();
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node current = first;

            public boolean hasNext()  { return current != null;                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
}
