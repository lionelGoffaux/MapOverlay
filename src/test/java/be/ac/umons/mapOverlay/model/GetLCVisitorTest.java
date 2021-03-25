package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.IntersectionsFinderDependentTest;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GetLCVisitorTest extends IntersectionsFinderDependentTest {

    @BeforeEach
    public void setup(){
        intersectionsFinderMockedStatic.when(IntersectionsFinder::getInstance).thenReturn(intersectionsFinder);
        setSweepLineY(0);
    }

    @AfterEach
    public void finish(){
        intersectionsFinderMockedStatic.close();
    }

    @Test
    public void getLTest(){
        SweepLineStatus tree = new SweepLineStatus();

        Segment s1 = new Segment(0, 0, 1, 12);
        Segment s2 = new Segment(0.5, 0, 1, 12);
        Segment s3 = new Segment(1, 0, 3, 5);
        Segment s4 = new Segment(3,0, 3, 6);
        Segment s5 = new Segment(5, 0, 3, 5);

        Point p = new Point(3, 5);

        tree.insert(s5);
        tree.insert(s4);
        tree.insert(s1);
        tree.insert(s3);
        tree.insert(s2);

        GetLCVisitor glcv = new GetLCVisitor(p);
        tree.accept(glcv);
        Assertions.assertFalse(glcv.getL().contains(s1));
        Assertions.assertFalse(glcv.getL().contains(s2));
        Assertions.assertTrue(glcv.getL().contains(s3));
        Assertions.assertFalse(glcv.getL().contains(s4));
        Assertions.assertTrue(glcv.getL().contains(s5));

        Assertions.assertFalse(glcv.getC().contains(s1));
        Assertions.assertFalse(glcv.getC().contains(s2));
        Assertions.assertFalse(glcv.getC().contains(s3));
        Assertions.assertTrue(glcv.getC().contains(s4));
        Assertions.assertFalse(glcv.getC().contains(s5));
    }
}
