package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.IntersectionsFinderDependentTest;
import be.ac.umons.mapOverlay.model.intersectionFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SweepLineStatusTest extends IntersectionsFinderDependentTest {

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
    public void insertLeafTest(){
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(0,0 ,1,1);
        Segment segment2 = new Segment(1,0 ,0,1);
        tree.insert(segment1);
        tree.insert(segment2);
        Assertions.assertEquals(segment2, tree.getData());
        Assertions.assertEquals(segment2, tree.getLeft().getData());
        Assertions.assertEquals(segment1, tree.getRight().getData());

        setSweepLineY(.1);
        tree = new SweepLineStatus();
        tree.insert(segment1);
        tree.insert(segment2);
        Assertions.assertEquals(segment1, tree.getData());
        Assertions.assertEquals(segment1, tree.getLeft().getData());
        Assertions.assertEquals(segment2, tree.getRight().getData());
    }

    @Test
    public void insertTest(){
        setSweepLineY(2.75);
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        Assertions.assertEquals(segment1, tree.getData());
        Assertions.assertEquals(segment1, tree.getLeft().getData());
        Assertions.assertEquals(segment3, tree.getRight().getData());
        Assertions.assertEquals(segment2, tree.getRight().getRight().getData());
        Assertions.assertEquals(segment3, tree.getRight().getLeft().getData());

    }

    @Test
    public void insertWithRotationTest(){
        setSweepLineY(2.75);
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        Segment segment4 = new Segment(4, 3, 5, 1);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        tree.insert(segment4);
        Assertions.assertEquals(segment3, tree.getData());
        Assertions.assertEquals(segment1, tree.getLeft().getData());
        Assertions.assertEquals(segment2, tree.getRight().getData());
        Assertions.assertEquals(segment4, tree.getRight().getRight().getData());
        Assertions.assertEquals(segment2, tree.getRight().getLeft().getData());
        Assertions.assertEquals(segment1, tree.getLeft().getLeft().getData());
        Assertions.assertEquals(segment3, tree.getLeft().getRight().getData());
    }

    @Test
    public void suppressionTest(){
        setSweepLineY(2.75);
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        Segment segment4 = new Segment(4, 3, 5, 1);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        tree.insert(segment4);
        tree.suppress(segment4);
        Assertions.assertEquals(segment3, tree.getData());
        Assertions.assertEquals(segment1, tree.getLeft().getData());
        Assertions.assertEquals(segment1, tree.getLeft().getLeft().getData());
        Assertions.assertEquals(segment3, tree.getLeft().getRight().getData());
        Assertions.assertEquals(segment2, tree.getRight().getData());
        tree.suppress(segment2);
        Assertions.assertEquals(segment1, tree.getData());
        Assertions.assertEquals(segment1, tree.getLeft().getData());
        Assertions.assertEquals(segment3, tree.getRight().getData());
        tree.suppress(segment1);
        tree.suppress(segment3);
    }

    @Test
    public void isLeafTest(){
        setSweepLineY(2.75);
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        Segment segment4 = new Segment(4, 3, 5, 1);
        tree.insert(segment1);
        Assertions.assertTrue(tree.isLeaf());
        tree.insert(segment2);
        Assertions.assertTrue(tree.getRight().isLeaf());
        tree.insert(segment3);
        tree.insert(segment4);
        Assertions.assertTrue(tree.getRight().getRight().isLeaf());
        Assertions.assertFalse(tree.isLeaf());
        Assertions.assertFalse(tree.getRight().isLeaf());
    }

    @Test
    public void suppressionOneNodeTest(){
        setSweepLineY(2.75);
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        tree.insert(segment1);
        tree.suppress(segment2); // suppression when there is one node in the tree and we try to suppress a node which is not in the tree
        tree.suppress(segment1);
        Assertions.assertNull(tree.getData());
        Assertions.assertNull(tree.getLeft());
        Assertions.assertNull(tree.getRight());
    }

    @Test
    public void getLeftNeighbourTest() {
        SweepLineStatus tree = new SweepLineStatus();
        // point 0
        Segment segment1 = new Segment(1,6,2,2);
        //  point 1
        Segment segment2 = new Segment(4,6,3,1);
        // point 2
        Segment segment3 = new Segment(5,5,6,1);
        // point 3
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        Point p0 = new Point(0,3);
        Point p1 = new Point(3,3);
        Point p2 = new Point(5, 3);
        Point p3 = new Point(7, 3);
//        System.out.println("======Point 0========");
//        System.out.println(tree.getLeftNeighbour(p0));
//        System.out.println("======Point 1========");
//        System.out.println(tree.getLeftNeighbour(p1));
//        System.out.println("======Point 2========");
//        System.out.println(tree.getLeftNeighbour(p2));
//        System.out.println("======Point 3========");
//        System.out.println(tree.getLeftNeighbour(p3));
        Assertions.assertNull(tree.getLeftNeighbour(p0));
        Assertions.assertEquals(segment1, tree.getLeftNeighbour(p1));
        Assertions.assertEquals(segment2, tree.getLeftNeighbour(p2));
        Assertions.assertEquals(segment3, tree.getLeftNeighbour(p3));

    }

    @Test
    public void getRightNeighbourTest(){
        SweepLineStatus tree = new SweepLineStatus();
        // point 0
        Segment segment1 = new Segment(1,6,2,2);
        //  point 1
        Segment segment2 = new Segment(4,6,3,1);
        // point 2
        Segment segment3 = new Segment(5,5,6,1);
        // point 3
        tree.insert(segment3);
        tree.insert(segment2);
        tree.insert(segment1);
        Point p0 = new Point(0,3);
        Point p1 = new Point(3,3);
        Point p2 = new Point(5, 3);
        Point p3 = new Point(7, 3);
//        System.out.println("======Point 0========");
//        System.out.println(tree.getRightNeighbour(p0));
//        System.out.println("======Point 1========");
//        System.out.println(tree.getRightNeighbour(p1));
//        System.out.println("======Point 2========");
//        System.out.println(tree.getRightNeighbour(p2));
//        System.out.println("======Point 3========");
//        System.out.println(tree.getRightNeighbour(p3));
        Assertions.assertEquals(segment1, tree.getRightNeighbour(p0));
        Assertions.assertEquals(segment2, tree.getRightNeighbour(p1));
        Assertions.assertEquals(segment3, tree.getRightNeighbour(p2));
        Assertions.assertNull(tree.getRightNeighbour(p3));
    }

    @Test
    public void getRightSegmentNeighbourTest() {
        setSweepLineY(3.);
        SweepLineStatus tree = new SweepLineStatus();
        // point 0
        Segment segment1 = new Segment(1,6,2,2);
        //  point 1
        Segment segment2 = new Segment(4,6,3,1);
        // point 2
        Segment segment3 = new Segment(5,5,6,1);
        // point 3
        tree.insert(segment3);
        tree.insert(segment2);
        tree.insert(segment1);
        Segment s0 = new Segment(0, 6, 1, 2);
        Segment s1 = new Segment(2, 6, 3, 2);
        Segment s2 = new Segment(5, 6, 4, 2);
        Segment s3 = new Segment(7, 5, 7, 1);
        Assertions.assertEquals(segment1, tree.getRightNeighbour(s0));
        Assertions.assertEquals(segment2, tree.getRightNeighbour(s1));
        Assertions.assertEquals(segment3, tree.getRightNeighbour(s2));
        Assertions.assertNull(tree.getRightNeighbour(s3));
    }

    @Test
    public void getLeftSegmentNeighbourTest() {
        setSweepLineY(3.);
        SweepLineStatus tree = new SweepLineStatus();
        // point 0
        Segment segment1 = new Segment(1,6,2,2);
        //  point 1
        Segment segment2 = new Segment(4,6,3,1);
        // point 2
        Segment segment3 = new Segment(5,5,6,1);
        // point 3
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        Segment s0 = new Segment(0, 6, 1, 2);
        Segment s1 = new Segment(2, 6, 3, 2);
        Segment s2 = new Segment(5, 6, 4, 2);
        Segment s3 = new Segment(7, 5, 7, 1);
        Assertions.assertNull(tree.getLeftNeighbour(s0));
        Assertions.assertEquals(segment1, tree.getLeftNeighbour(s1));
        Assertions.assertEquals(segment2, tree.getLeftNeighbour(s2));
        Assertions.assertEquals(segment3, tree.getLeftNeighbour(s3));
    }

    @Test
    public void getLeavesTest(){
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        Segment segment4 = new Segment(4, 3, 5, 1);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        tree.insert(segment4);
        ArrayList<Segment> list = tree.getLeaves();
        Assertions.assertTrue(list.contains(segment1));
        Assertions.assertTrue(list.contains(segment2));
        Assertions.assertTrue(list.contains(segment3));
        Assertions.assertTrue(list.contains(segment4));
    }

    @Test
    public void getLTest(){
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment0 = new Segment(1, 7, 2, 1);
        Segment segment1 = new Segment(1, 7, 2, 3);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        Segment segment4 = new Segment(4, 3, 5, 3);
        tree.insert(segment0);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        tree.insert(segment4);
        /*Point p0 = new Point(2, 1);
        Point p1 = new Point(1,1);
        Point p2 = new Point(3,1);
        Point p3 = new Point(5,1);*/
        Point p0 = new Point(1, 7);
        Point p1 = new Point(3,3);
        Point p2 = new Point(2,4);
        Point p3 = new Point(5,3);
        Assertions.assertTrue(tree.getL(p0).contains(segment0));
        Assertions.assertTrue(tree.getL(p0).contains(segment1));
        Assertions.assertTrue(tree.getL(p1).contains(segment2));
        Assertions.assertTrue(tree.getL(p2).contains(segment3));
        Assertions.assertTrue(tree.getL(p3).contains(segment4));
    }

    @Test
    public void getCTest() {
        SweepLineStatus tree = new SweepLineStatus();
        Segment segment0 = new Segment(0, 0, 2, 2);
        Segment segment1 = new Segment(0, 2, 2, 0);
        tree.insert(segment0);
        tree.insert(segment1);
        Point p1 = new Point(1,1);
        Point p2 = new Point(1.75, 1.75);
        ArrayList<Segment> list = tree.getC(p1);
        Assertions.assertTrue(list.contains(segment0));
        Assertions.assertTrue(list.contains(segment1));
        list = tree.getC(p2);
        Assertions.assertTrue(list.contains(segment0));
        Assertions.assertFalse(list.contains(segment1));
    }
}