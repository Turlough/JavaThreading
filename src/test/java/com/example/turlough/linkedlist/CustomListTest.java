package com.example.turlough.linkedlist;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by turlough on 25/07/16.
 */
public class CustomListTest {
    CustomList<Item> list;

    @Before
    public void setUp() throws Exception {
        list = new CustomList<>();

        Item item1 = new Item(1,"test  1");
        Item item2 = new Item(2,"test  2");
        Item item3 = new Item(3,"test  3");
        list.add(item1)
                .add(item2)
                .add(item3);

    }

    @Test
    public void add() throws Exception {
        Item item1 = new Item(1,"test  1");
        Item item2 = new Item(2,"test  2");
        Item item3 = new Item(3,"test  3");
        list.add(item1)
                .add(item2)
                .add(item3);
    }

    @Test
    public void iterator() throws Exception {

        list.begin();

        int count = 0;
        while(list.iterator().hasNext()){
            Item i = list.iterator().next().getItem();
            System.out.printf("Id = %d, Name = '%s'\n", i.getId(), i.getName());;
            ++count;
        }

        assertEquals(3, count);
    }

    @Test
    public void forEach() throws Exception {

        list.forEach((i) -> {
            System.out.printf("Id = %d, Name = '%s'\n", i.getItem().getId(), i.getItem().getName());
        });

    }

    @Test
    public void get() throws Exception {

        assertEquals("failed to get 0", 1, list.get(0).getId());
        assertEquals("failed to get 1", 2, list.get(1).getId());
        assertEquals("failed to get 2", 3, list.get(2).getId());
    }

    @Test
    public void remove() throws Exception {

        Item item = list.remove(1);

        //removed the correct item
        assertEquals("removed the wrong item", 2, item.getId());

        //remaining items are linked correctly
        assertEquals("failed to get 0", 1, list.get(0).getId());
        assertEquals("failed to get 1", 3, list.get(1).getId());

    }

    @Test
    public void removeZero() throws Exception {

        Item item = list.remove(0);

        //removed the correct item
        assertEquals("removed the wrong item", 1, item.getId());

        //remaining items are linked correctly
        assertEquals("failed to get 0", 2, list.get(0).getId());
        assertEquals("failed to get 1", 3, list.get(1).getId());

    }
}