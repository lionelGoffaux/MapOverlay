package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.mapOverlay.IntersectionsFinderDependentTest;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SegmentTest extends IntersectionsFinderDependentTest {

    @BeforeEach
    public void setup(){
        intersectionsFinderMockedStatic.when(IntersectionsFinder::getInstance).thenReturn(intersectionsFinder);
        setSweepLineY(0.75);
    }

    @AfterEach
    public void finish(){
        intersectionsFinderMockedStatic.close();
    }

    @Test
    public void parallelIntersectTest(){
        Segment segment1 = new Segment(1, 1, 2, 3);
        Segment segment2 = new Segment(2, 2, 3, 4);
        Assertions.assertNull(segment1.getIntersection(segment2));
    }

    @Test
    public void noIntersectionTest(){
        Segment segment1 = new Segment(1, 1, 2, 2);
        Segment segment2 = new Segment(4, 2 ,5, 1);
        Assertions.assertNull(segment1.getIntersection(segment2));
    }

    @Test
    public void intersectionTest(){
        Segment segment1 = new Segment(0, 0 , 1, 1);
        Segment segment2 = new Segment(0, 1, 1, 0);
        Assertions.assertEquals(new Point(.5, .5), segment1.getIntersection(segment2));
    }

    @Test
    public void extremityIntersectionTest(){
        Segment segment1 = new Segment(0, 0, 1, 1);
        Segment segment2 = new Segment(1, 1, 2, 0);
        Assertions.assertEquals(new Point(1, 1), segment1.getIntersection(segment2));
        segment1 = new Segment(0, 0, 2, 2);
        segment2 = new Segment(1, 1, 3, 7);
        Assertions.assertEquals(new Point(1, 1), segment1.getIntersection(segment2));
    }

    @Test
    public void prolongationIntersectionTest(){
        Segment segment1 = new Segment(0, 0, 1, 1);
        Segment segment2 = new Segment(1, 1, 2, 2);
        Assertions.assertEquals(new Point(1, 1), segment1.getIntersection(segment2));
    }

    @Test
    public void superpositionIntersectionTest(){
        Segment segment1 = new Segment(0, 0, 2, 2);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Assertions.assertNull(segment1.getIntersection(segment2));
    }

    @Test
    public void comparisonTest(){
        setSweepLineY(1.75);

        Segment segment1 = new Segment(1, 2, 3, 0);
        Segment segment2 = new Segment(1, 0, 3, 2);
        Assertions.assertEquals(-1, segment1.compareTo(segment2));
        Assertions.assertEquals(1, segment2.compareTo(segment1));
    }

    @Test
    public void horizontalSegmentOrder(){
        Segment s1 = new Segment(0,0,1,1);
        Segment s2 = new Segment(0.5, 0.5, 2, 0.5);
        Segment s3 = new Segment(0.5, 0.5, 3, 0.5);;

        setSweepLineY(0.5);
        Assertions.assertEquals(-1, s1.compareTo(s2));
        Assertions.assertEquals(-1, s1.compareTo(s3));
        Assertions.assertEquals(1, s2.compareTo(s1));
        Assertions.assertEquals(1, s3.compareTo(s1));
        Assertions.assertEquals(1, s3.compareTo(s2));
        Assertions.assertEquals(-1, s2.compareTo(s3));
    }

    @Test
    public void orderReversedTest(){
        Segment s1 = new Segment(0,0,1,1);
        Segment s2 = new Segment(1, 0, 0, 1);

        setSweepLineY(0.25);
        Assertions.assertEquals(-1, s1.compareTo(s2));

        setSweepLineY(0.5);
        Assertions.assertEquals(1, s1.compareTo(s2));

        setSweepLineY(0.75);
        Assertions.assertEquals(1, s1.compareTo(s2));
    }

    @Test
    public void positiveSegmentContainsTest() {
        Segment s = new Segment(1, 1, 3, 9);

        // endpoints
        Assertions.assertTrue(s.contains(new Point(1, 1)));
        Assertions.assertTrue(s.contains(new Point(3, 9)));

        // between endpoints
        Assertions.assertTrue(s.contains(new Point(2, 5)));

        // before and after endpoints
        Assertions.assertFalse(s.contains(new Point(0, -3)));
        Assertions.assertFalse(s.contains(new Point(4, 13)));

        // below and above
        Assertions.assertFalse(s.contains(new Point(1.5, 2)));
        Assertions.assertFalse(s.contains(new Point(2.5, 8)));
    }

    @Test
    public void negativeSegmentContainsTest(){
        Segment s = new Segment(1, 2, 3, 0);

        // endpoints
        Assertions.assertTrue(s.contains(new Point(1, 2)));
        Assertions.assertTrue(s.contains(new Point(3, 0)));

        // between endpoints
        Assertions.assertTrue(s.contains(new Point(2, 1)));

        // before and after endpoints
        Assertions.assertFalse(s.contains(new Point(4, -1)));
        Assertions.assertFalse(s.contains(new Point(-1, 4)));

        // below and above
        Assertions.assertFalse(s.contains(new Point(0.5, 0.5)));
        Assertions.assertFalse(s.contains(new Point(2.5, 1.5)));
    }

    @Test
    public void coefATest(){
        Segment s1 = new Segment(1, 2, 4, 0);
        Segment s2 = new Segment(1, 1, 2, 5);

        Assertions.assertTrue(Utils.almostEqual(s1.a, -2));
        Assertions.assertTrue(Utils.almostEqual(s2.a, -4));
    }

    @Test
    public void coefBTest(){
        Segment s1 = new Segment(1, 2, 4, 0);
        Segment s2 = new Segment(1, 1, 2, 5);

        Assertions.assertTrue(Utils.almostEqual(s1.b, -3));
        Assertions.assertTrue(Utils.almostEqual(s2.b, 1));
    }

    @Test
    public void coefCTest(){
        Segment s1 = new Segment(1, 2, 4, 0);
        Segment s2 = new Segment(1, 1, 2, 5);

        Assertions.assertTrue(Utils.almostEqual(s1.c, -8));
        Assertions.assertTrue(Utils.almostEqual(s2.c, -3));
    }

    @Test
    public void detTest() {
        Segment s1 = new Segment(1, 2, 4, 0);
        Segment s2 = new Segment(1, 1, 2, 5);

        double det = Segment.getDet(s1, s2);
        Assertions.assertTrue(Utils.almostEqual(det, -14));
    }

    @Test
    public void disjointedParallelDetTest() {
        Segment s1 = new Segment(1, 2, 4, 0);
        Segment s2 = new Segment(4, 7, 7, 5);

        double det = Segment.getDet(s1, s2);
        Assertions.assertTrue(Utils.almostEqual(det, 0));
    }

    @Test
    public void combinedParallelDetTest() {
        Segment s1 = new Segment(1, 2, 4, 0);
        Segment s2 = new Segment(7, -2, 10, -4);

        double det = Segment.getDet(s1, s2);
        Assertions.assertTrue(Utils.almostEqual(det, 0));
    }
}