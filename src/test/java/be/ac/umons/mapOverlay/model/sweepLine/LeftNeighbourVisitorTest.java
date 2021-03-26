package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.IntersectionsFinderDependentTest;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.sweepLine.GetLeftNeighbourVisitor;
import be.ac.umons.mapOverlay.model.sweepLine.SweepLineStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeftNeighbourVisitorTest extends IntersectionsFinderDependentTest {
    @BeforeEach
    public void setup(){
        intersectionsFinderMockedStatic.when(IntersectionsFinder::getInstance).thenReturn(intersectionsFinder);
        setSweepLineY(3.);
    }

    @AfterEach
    public void finish(){
        intersectionsFinderMockedStatic.close();
    }

    @Test
    public void getPointLeftNeighbourTest(){
        SweepLineStatus tree = new SweepLineStatus();

        // point 0
        Segment segment1 = new Segment(1,6,2,2);
        //  point 1
        Segment segment2 = new Segment(4,6,3,1);
        // point 2
        Segment segment3 = new Segment(5,5,6,1);
        // point 3

        tree.insert(segment2);
        tree.insert(segment3);
        tree.insert(segment1);

        Point p0 = new Point(0,3);
        Point p1 = new Point(3,3);
        Point p2 = new Point(5, 3);
        Point p3 = new Point(7, 3);

        GetLeftNeighbourVisitor glnv = new GetLeftNeighbourVisitor(p0);
        tree.accept(glnv);
        Assertions.assertNull(glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(p1);
        tree.accept(glnv);
        Assertions.assertEquals(segment1, glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(p2);
        tree.accept(glnv);
        Assertions.assertEquals(segment2, glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(p3);
        tree.accept(glnv);
        Assertions.assertEquals(segment3, glnv.getNeighbour());
    }

    @Test
    public void getSegmentLeftNeighbourTest(){
        SweepLineStatus tree = new SweepLineStatus();

        // point 0
        Segment segment1 = new Segment(1,6,2,2);
        //  point 1
        Segment segment2 = new Segment(4,6,3,1);
        // point 2
        Segment segment3 = new Segment(5,5,6,1);
        // point 3

        tree.insert(segment3);
        tree.insert(segment1);
        tree.insert(segment2);

        Segment s0 = new Segment(0, 6, 1, 2);
        Segment s1 = new Segment(2, 6, 3, 2);
        Segment s2 = new Segment(5, 6, 4, 2);
        Segment s3 = new Segment(7, 5, 7, 1);
        Segment s4 = new Segment(10, 3, 7, 3);

        GetLeftNeighbourVisitor glnv = new GetLeftNeighbourVisitor(s0);
        tree.accept(glnv);
        Assertions.assertNull(glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(s1);
        tree.accept(glnv);
        Assertions.assertEquals(segment1, glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(s2);
        tree.accept(glnv);
        Assertions.assertEquals(segment2, glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(s3);
        tree.accept(glnv);
        Assertions.assertEquals(segment3, glnv.getNeighbour());

        glnv = new GetLeftNeighbourVisitor(s4);
        tree.accept(glnv);
        Assertions.assertEquals(segment3, glnv.getNeighbour());
    }

}
