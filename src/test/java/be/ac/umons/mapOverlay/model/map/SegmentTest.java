package be.ac.umons.mapOverlay.model.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    @Test
    public void intersectTest(){
        Segment segment1 = new Segment(1, 1, 2, 3);
        Segment segment2 = new Segment(2, 2, 3, 4);
        assertNull(segment1.isIntersecting(segment2));
        segment1 = new Segment(1, 1, 2, 2);
        segment2 = new Segment(4, 2 ,5, 1);
        assertNull(segment1.isIntersecting(segment2));
        segment1 = new Segment(0, 0 , 1, 1);
        segment2 = new Segment(0, 1, 1, 0);
        assertEquals(new Point(.5, .5), segment1.isIntersecting(segment2));
    }

}