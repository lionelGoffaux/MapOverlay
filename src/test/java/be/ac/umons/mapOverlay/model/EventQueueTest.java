package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventQueueTest {
    
    @Test
    public void insertDuplicatesTest(){
        Segment s1 = new Segment(1, 2, 15 ,32);
        Segment s2 = new Segment(9, 6, 1, 2);

        Event e1 = new Event(new Point(2, 1));
        Event e2 = new Event(new Point(1, 2), s1);
        Event e3 = new Event(new Point(1, 2), s2);
        Event e4 = new Event(new Point(4, 2));

        EventQueue eventQueue = new EventQueue();
        eventQueue.insert(e1);
        eventQueue.insert(e2);
        eventQueue.insert(e3);
        eventQueue.insert(e4);

        assertEquals(e1, eventQueue.getNextEvent());
        assertEquals(e2, eventQueue.getNextEvent());
        assertEquals(e4, eventQueue.getNextEvent());
        assertTrue(eventQueue.isEmpty());

        assertTrue(e2.getU().contains(s1));
        assertTrue(e2.getU().contains(s2));
    }

    @Test
    public void getNextTest(){
        Event e1 = new Event(new Point(2, 1));
        Event e2 = new Event(new Point(1, 2));
        Event e3 = new Event(new Point(4, 2));
        EventQueue eventQueue = new EventQueue();

        eventQueue.insert(e1);
        assertEquals(e1, eventQueue.getNextEvent());
        assertTrue(eventQueue.isEmpty());
        eventQueue.insert(e1);
        eventQueue.insert(e2);
        assertEquals(e1, eventQueue.getNextEvent());
        assertEquals(e2, eventQueue.getNextEvent());
        assertTrue(eventQueue.isEmpty());
        eventQueue.insert(e1);
        eventQueue.insert(e2);
        eventQueue.insert(e3);
        assertEquals(e1, eventQueue.getNextEvent());
        assertEquals(e2, eventQueue.getNextEvent());
        assertEquals(e3, eventQueue.getNextEvent());
        assertTrue(eventQueue.isEmpty());
        eventQueue.insert(e3);
        eventQueue.insert(e2);
        eventQueue.insert(e1);
        assertEquals(e1, eventQueue.getNextEvent());
        assertEquals(e2, eventQueue.getNextEvent());
        assertEquals(e3, eventQueue.getNextEvent());
        assertTrue(eventQueue.isEmpty());
    }
}
