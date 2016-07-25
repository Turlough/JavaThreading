package com.example.turlough.linkedlist;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by turlough on 25/07/16.
 */
public class CustomList <T> implements Iterable<ListItem<T>> {

    private final ListItem<T> root = new ListItem<>(null);
    private ListItem<T> last = root;
    private ListItem<T> current = root;
    private Iterator<ListItem<T>> iterator = new ListIterator<>();


    public CustomList<T> add(T item){
        ListItem listItem = new ListItem(item);
        last.setNext(listItem);
        last = listItem;
        current = root.getNext();
        return this;
    }

    public CustomList <T> begin(){
        current = root;
        return this;
    }

    public T get(int position) {

        current = root;
        for (int i = 0; i <= position; i++) {

            current = current.getNext();
            if(current == null)
                throw new ArrayIndexOutOfBoundsException("No item found at this position");

        }
        return current.getItem();
    }

    public T remove (int position) {

        ListItem<T> previous = root;
        current = previous;

        for (int i = 0; i <= position; i++) {
            previous = current;
            current = previous.getNext();
            if(current == null)
                throw new ArrayIndexOutOfBoundsException("No item found at this position");

        }
        previous.setNext(current.getNext());
        return current.getItem();
    }

//<editor-fold desc = Iterator>
    @Override
    public Iterator<ListItem<T>> iterator() {
        return iterator;
    }

    @Override
    public void forEach(Consumer<? super ListItem<T>> action) {
        current = root.getNext();
        while(current != null){
            action.accept(current);
            current = current.getNext();
        }
    }

    @Override
    public Spliterator<ListItem<T>> spliterator() {
        return null;
    }

    class ListIterator<ListItem> implements Iterator<ListItem>{
        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public ListItem next() {
            if(current != null)
                current = current.getNext();
            return (ListItem) current;
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super ListItem> action) {
            while(current != null){
                action.accept((ListItem) current);
                current = current.getNext();
            }

        }
    }
    //</editor-fold>

}
