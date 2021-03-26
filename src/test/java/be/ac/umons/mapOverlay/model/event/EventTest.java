package be.ac.umons.mapOverlay.model.event;

import be.ac.umons.mapOverlay.model.event.Event;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class EventTest {

    @Test
    public void equalsTest(){
        Event e1 = new Event(new Point(25, 2.6));
        Event e2 = new Event(new Point(25, 2.6), new Segment(25, 2.6, 12, 12));
        Event e3 = new Event(new Point(12, 2.6));

        Assertions.assertEquals(e1, e2);
        Assertions.assertEquals(e2, e1);
        Assertions.assertNotEquals(e1, e3);
        Assertions.assertNotEquals(e2, e3);
        Assertions.assertNotEquals(e3, e1);
        Assertions.assertNotEquals(e3, e2);
    }

    @Test
    public void compareTest(){
        Event e1 = new Event(new Point(2, 1));
        Event e2 = new Event(new Point(1, 2));
        Event e3 = new Event(new Point(4, 2));

        Assertions.assertEquals(-1, e1.compareTo(e2));
        Assertions.assertEquals(-1, e1.compareTo(e3));
        Assertions.assertEquals(-1, e2.compareTo(e3));
        Assertions.assertEquals(1, e3.compareTo(e2));
        Assertions.assertEquals(1, e3.compareTo(e1));
        Assertions.assertEquals(1, e2.compareTo(e1));
    }

    @Test
    public void updateSegmentsTest(){
        Segment s1 = new Segment(6, 5, 2, 4);
        Segment s2 = new Segment(6, 5, 2, 4);
        Segment s3 = new Segment(2, 4, 6, 5);
        Segment s4 = new Segment(2, 4, 12, 15);

        ArrayList<Segment> segments = new ArrayList<>();

        Event e = new Event(new Point(2, 4), s1);

        Assertions.assertEquals(1, e.getU().size());
        segments.add(s1);
        e.updateSegments(segments);
        Assertions.assertEquals(1, e.getU().size());
        segments.add(s2);
        e.updateSegments(segments);
        Assertions.assertEquals(1, e.getU().size());
        segments.add(s3);
        e.updateSegments(segments);
        Assertions.assertEquals(1, e.getU().size());
        segments.add(s4);
        e.updateSegments(segments);
        Assertions.assertEquals(2, e.getU().size());
    }

    @Test
    public void getPointTest(){
        Point p = new Point(12, 42);
        Event e = new Event(p);

        Assertions.assertEquals(p, e.getPoint());
    }
}
