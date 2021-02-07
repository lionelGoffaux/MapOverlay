package be.ac.umons.mapOverlay.model.map;

import org.junit.jupiter.api.Test;

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



}