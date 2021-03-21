package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.Event;
import be.ac.umons.mapOverlay.model.EventQueue;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class FindingStateTest {

    public IntersectionsFinderState findingState = FindingState.getInstance();

    @Test
    public void entryTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        Segment s = new Segment(68, 70, 42, 42);
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(s);
        Mockito.when(intersectionsFinder.getSegments()).thenReturn(segments);
        findingState.entry(intersectionsFinder);

        Assertions.assertTrue(intersectionsFinder.status.isEmpty());
        Event e = intersectionsFinder.eventQueue.getNextEvent();
        Assertions.assertEquals(new Point(42, 42), e.getPoint());
        Assertions.assertEquals(1, e.getU().size());
        Assertions.assertEquals(s, e.getU().get(0));
        e = intersectionsFinder.eventQueue.getNextEvent();
        Assertions.assertEquals(new Point(68, 70), e.getPoint());
        Assertions.assertEquals(0, e.getU().size());
    }

    @Test
    public void stepForwardEmptyQueueTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        intersectionsFinder.eventQueue = new EventQueue();
        findingState.stepForward(intersectionsFinder);

        Mockito.verify(intersectionsFinder, Mockito.times(0)).handleEventPoint(Mockito.any(Event.class));
    }

    @Test
    public void stepForwardTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        intersectionsFinder.eventQueue = new EventQueue();
        intersectionsFinder.eventQueue.insert(new Event(new Point(42, 42)));
        intersectionsFinder.eventQueue.insert(new Event(new Point(42, 42)));
        intersectionsFinder.eventQueue.insert(new Event(new Point(63, 42)));
        findingState.stepForward(intersectionsFinder);

        Mockito.verify(intersectionsFinder, Mockito.times(1)).handleEventPoint(Mockito.any(Event.class));
    }

    @Test
    public void findAllEmptyQueueTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        intersectionsFinder.eventQueue = new EventQueue();
        findingState.findAll(intersectionsFinder);

        Mockito.verify(intersectionsFinder, Mockito.times(0)).handleEventPoint(Mockito.any(Event.class));
    }

    @Test
    public void findAllTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        intersectionsFinder.eventQueue = new EventQueue();
        intersectionsFinder.eventQueue.insert(new Event(new Point(42, 42)));
        intersectionsFinder.eventQueue.insert(new Event(new Point(42, 42)));
        intersectionsFinder.eventQueue.insert(new Event(new Point(63, 42)));
        findingState.findAll(intersectionsFinder);

        Mockito.verify(intersectionsFinder, Mockito.times(2)).handleEventPoint(Mockito.any(Event.class));
    }

    @Test
    public void startNewSegmentTest() {
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        findingState.startNewSegment(intersectionsFinder, 42, 42);

        Assertions.assertEquals(new Point(42, 42), intersectionsFinder.newSegmentStart);
        Mockito.verify(intersectionsFinder, Mockito.times(1)).setState(EditionState.getInstance());
    }

    @Test
    public void newMapTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        findingState.newMap(intersectionsFinder);

        Assertions.assertEquals(new Map(), intersectionsFinder.map);
        Mockito.verify(intersectionsFinder, Mockito.times(1)).setState(EditionState.getInstance());
    }
}
