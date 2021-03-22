package be.ac.umons.mapOverlay.model.geometry;

import be.ac.umons.mapOverlay.IntersectionsFinderDependentTest;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LineTest extends IntersectionsFinderDependentTest {
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
    public void comparisonTest(){
        setSweepLineY(1.75);

        Segment segment1 = new Segment(1, 2, 3, 0);
        Segment segment2 = new Segment(1, 0, 3, 2);
        Assertions.assertEquals(-1, segment1.compareTo(segment2));
        Assertions.assertEquals(1, segment2.compareTo(segment1));
    }

    @Test
    public void positiveLineContainsTest() {
        Line s = new Line(1, 1, 3, 9);

        // endpoints
        Assertions.assertTrue(s.contains(new Point(1, 1)));
        Assertions.assertTrue(s.contains(new Point(3, 9)));

        // between endpoints
        Assertions.assertTrue(s.contains(new Point(2, 5)));

        // before and after endpoints
        Assertions.assertTrue(s.contains(new Point(0, -3)));
        Assertions.assertTrue(s.contains(new Point(4, 13)));

        // below and above
        Assertions.assertFalse(s.contains(new Point(1.5, 2)));
        Assertions.assertFalse(s.contains(new Point(2.5, 8)));
    }

    @Test
    public void negativeLineContainsTest(){
        Line s = new Line(1, 2, 3, 0);

        // endpoints
        Assertions.assertTrue(s.contains(new Point(1, 2)));
        Assertions.assertTrue(s.contains(new Point(3, 0)));

        // between endpoints
        Assertions.assertTrue(s.contains(new Point(2, 1)));

        // before and after endpoints
        Assertions.assertTrue(s.contains(new Point(4, -1)));
        Assertions.assertTrue(s.contains(new Point(-1, 4)));

        // below and above
        Assertions.assertFalse(s.contains(new Point(0.5, 0.5)));
        Assertions.assertFalse(s.contains(new Point(2.5, 1.5)));
    }
}
