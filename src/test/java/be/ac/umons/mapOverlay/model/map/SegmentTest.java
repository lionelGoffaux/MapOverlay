package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    public IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
    public MockedStatic<IntersectionsFinder> theMock = Mockito.mockStatic(IntersectionsFinder.class);

    @BeforeEach
    public void setup(){
        theMock.when(IntersectionsFinder::getInstance).thenReturn(intersectionsFinder);
        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(.75);
    }

    @AfterEach
    public void finish(){
        theMock.close();
    }

    @Test
    public void parallelIntersectTest(){
        Segment segment1 = new Segment(1, 1, 2, 3);
        Segment segment2 = new Segment(2, 2, 3, 4);
        assertNull(segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void notIntersectionTest(){
        Segment segment1 = new Segment(1, 1, 2, 2);
        Segment segment2 = new Segment(4, 2 ,5, 1);
        assertNull(segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void intersectionTest(){
        Segment segment1 = new Segment(0, 0 , 1, 1);
        Segment segment2 = new Segment(0, 1, 1, 0);
        assertEquals(new Point(.5, .5), segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void horizontalIntersectionTest(){
        Segment segment1 = new Segment(2, 0 , 0, 0);
        Segment segment2 = new Segment(0, -1, 2, 1);
        assertEquals(new Point(1, 0), segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void verticalIntersectionTest(){
        Segment segment1 = new Segment(0, -1 , 0, 1);
        Segment segment2 = new Segment(1, 0, -1, 0);
        assertEquals(new Point(0, 0), segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void extremityIntersectionTest(){
        Segment segment1 = new Segment(0, 0, 1, 1);
        Segment segment2 = new Segment(1, 1, 2, 0);
        assertEquals(new Point(1, 1), segment1.getIntersectionPoint(segment2));
        segment1 = new Segment(0, 0, 2, 2);
        segment2 = new Segment(1, 1, 3, 7);
        assertEquals(new Point(1, 1), segment1.getIntersectionPoint(segment2));
    }
    @Test
    public void prolongationIntersectionTest(){
        Segment segment1 = new Segment(0, 0, 1, 1);
        Segment segment2 = new Segment(1, 1, 2, 2);
        assertEquals(new Point(1, 1), segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void superpositionIntersectionTest(){
        Segment segment1 = new Segment(0, 0, 2, 2);
        Segment segment2 = new Segment(1, 1, 3, 3);
        assertNull(segment1.getIntersectionPoint(segment2));
    }

    @Test
    public void comparisonTest(){
        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(1.75);

        Segment segment1 = new Segment(1, 2, 3, 0);
        Segment segment2 = new Segment(1, 0, 3, 2);
        assertEquals(-1, segment1.compareTo(segment2));
        assertEquals(1, segment2.compareTo(segment1));
        segment1 = new Segment(1, 2, 3, 0);
        segment2 = new Segment(2, 3, 4, 5);
        assertEquals(-1, segment2.compareTo(segment1));
    }

    @Test
    public void orderReversedTest(){
        Segment s1 = new Segment(0,0,1,1);
        Segment s2 = new Segment(1, 0, 0, 1);

        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(0.25);
        assertEquals(-1, s1.compareTo(s2));

        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(0.5);
        assertEquals(1, s1.compareTo(s2));

        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(0.75);
        assertEquals(1, s1.compareTo(s2));
    }

    @Test
    public void horizontalSegmentOrder(){
        Segment s1 = new Segment(0,0,1,1);
        Segment s2 = new Segment(0.5, 0.5, 1, 0.5);
        Segment s3 = new Segment(0.5, 0.5, 1.1, 0.5);;

        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(0.5);
        assertEquals(-1, s1.compareTo(s2));
        assertEquals(1, s3.compareTo(s2));
    }

    @Test
    public void containsTest() {
        Segment seg = new Segment(0,0,2,2);
        Point p1 = new Point(1,1);
        Point p2 = new Point(0,0);
        Point p3 = new Point(2,2);
        Point p4 = new Point(3,3);
        Point p5 = new Point(100, 8);
        assertTrue(seg.contains(p1));
        assertTrue(seg.contains(p2));
        assertTrue(seg.contains(p3));
        assertFalse(seg.contains(p4));
        assertFalse(seg.contains(p5));
    }

}