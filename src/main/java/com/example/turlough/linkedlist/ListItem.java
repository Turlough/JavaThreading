package com.example.turlough.linkedlist;

/**
 * Created by turlough on 25/07/16.
 */
public class ListItem <T> {

    private T item = null;
    private ListItem<T> next = null;
    private ListItem<T> previous = null;

    public ListItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public ListItem<T> getNext() {
        return next;
    }

    public void setNext(ListItem<T> next) {
        this.next = next;
    }

    public ListItem<T> getPrevious() {
        return previous;
    }

    public void setPrevious(ListItem<T> previous) {
        this.previous = previous;
    }
}
