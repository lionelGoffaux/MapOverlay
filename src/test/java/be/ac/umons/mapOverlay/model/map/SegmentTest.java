package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.model.IntersectionsFinder;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

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
        Main main = Mockito.mock(Main.class);
        MockedStatic<Main> theMock = Mockito.mockStatic(Main.class);
        theMock.when(Main::getApp).thenReturn(main);
        IntersectionsFinder intersectionsFinder = Mockito.mock(IntersectionsFinder.class);
        Mockito.when(main.getIntersectionsFinder()).thenReturn(intersectionsFinder);
        Mockito.when(intersectionsFinder.getSweepLineY()).thenReturn(1.5);


        Segment segment1 = new Segment(1, 2, 3, 0);
        Segment segment2 = new Segment(1, 0, 3, 2);
        assertEquals(-1, segment1.compareTo(segment2));
        assertEquals(1, segment2.compareTo(segment1));
    }



}