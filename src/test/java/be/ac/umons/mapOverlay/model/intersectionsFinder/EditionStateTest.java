package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class EditionStateTest {

    public IntersectionsFinderState editionState = EditionState.getInstance();

    @Test
    public void entryTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        intersectionsFinder.sweepLineY = 18;
        editionState.entry(intersectionsFinder);

        Assertions.assertEquals(0, intersectionsFinder.sweepLineY);
        Assertions.assertTrue(intersectionsFinder.intersections.isEmpty());
    }

    @Test
    public void setMapTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        Map map = new Map();
        editionState.setMap(intersectionsFinder, map);

        Assertions.assertEquals(intersectionsFinder.map, map);
    }

    @Test
    public void startNewSegmentTest() {
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        editionState.startNewSegment(intersectionsFinder, 42, 42);

        Assertions.assertEquals(new Point(42, 42), intersectionsFinder.newSegmentStart);
    }

    @Test
    public void endNewSegmentStartNullTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        Map map = Mockito.mock(Map.class);
        intersectionsFinder.map = map;
        editionState.endNewSegment(intersectionsFinder, 64, 56);

        Mockito.verify(map, Mockito.times(0)).addSegment(Mockito.any(Segment.class));
    }

    @Test
    public void endNewSegmentTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        Map map = Mockito.mock(Map.class);
        intersectionsFinder.map = map;
        intersectionsFinder.newSegmentStart = new Point(42,42);
        editionState.endNewSegment(intersectionsFinder, 64, 56);

        Mockito.verify(map, Mockito.times(1)).addSegment(Mockito.any(Segment.class));
    }

    @Test
    public void newMapTest(){
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        editionState.newMap(intersectionsFinder);

        Assertions.assertEquals(new Map(), intersectionsFinder.map);
    }
}
