package be.ac.umons.mapOverlay.model.sweepLine;

import be.ac.umons.mapOverlay.IntersectionsFinderDependentTest;
import be.ac.umons.mapOverlay.model.intersectionsFinder.IntersectionsFinder;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void complexSuppressionTest(){
        setSweepLineY(1);
        SweepLineStatus tree = new SweepLineStatus();

        Segment s1 = new Segment(1,1, 0, 2);
        Segment s2 = new Segment(1, 1, 2, 2);
        Segment s3 = new Segment(3, 0, 3, 2);

        tree.insert(s3);
        tree.insert(s2);
        tree.insert(s1);
        tree.suppress(s2);

        Assertions.assertEquals(s1, tree.getData());
        Assertions.assertEquals(s1, tree.getLeft().getData());
        Assertions.assertTrue(tree.getLeft().isLeaf());
        Assertions.assertEquals(s3, tree.getRight().getData());
        Assertions.assertTrue(tree.getRight().isLeaf());
    }

}