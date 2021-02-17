package be.ac.umons.mapOverlay.model;

import be.ac.umons.mapOverlay.Main;
import be.ac.umons.mapOverlay.model.map.Segment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SweepLineStatusTest {
    public Main main = Mockito.mock(Main.class);
    public MockedStatic<Main> theMock = Mockito.mockStatic(Main.class);

    @BeforeEach
    public void setup(){
        theMock.when(Main::getApp).thenReturn(main);
        Mockito.when(main.getSweepLineY()).thenReturn(.75);
    }

    @AfterEach
    public void finish(){
        theMock.close();
    }

    @Test
    public void insertLeafTest(){
        SweepLineStatus<Segment> tree = new SweepLineStatus<>();
        Segment segment1 = new Segment(0,0 ,1,1);
        Segment segment2 = new Segment(1,0 ,0,1);
        tree.insert(segment1);
        tree.insert(segment2);
        assertEquals(segment2, tree.getData());
        assertEquals(segment2, tree.getLeft().getData());
        assertEquals(segment1, tree.getRight().getData());

        Mockito.when(main.getSweepLineY()).thenReturn(.1);
        tree = new SweepLineStatus<>();
        tree.insert(segment1);
        tree.insert(segment2);
        assertEquals(segment1, tree.getData());
        assertEquals(segment1, tree.getLeft().getData());
        assertEquals(segment2, tree.getRight().getData());
    }

    @Test
    public void insertTest(){
        Mockito.when(main.getSweepLineY()).thenReturn(2.75);
        SweepLineStatus<Segment> tree = new SweepLineStatus<>();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        assertEquals(segment1, tree.getData());
        assertEquals(segment1, tree.getLeft().getData());
        assertEquals(segment3, tree.getRight().getData());
        assertEquals(segment2, tree.getRight().getRight().getData());
        assertEquals(segment3, tree.getRight().getLeft().getData());

    }

    @Test
    public void insertWithRotationTest(){
        Mockito.when(main.getSweepLineY()).thenReturn(2.75);
        SweepLineStatus<Segment> tree = new SweepLineStatus<>();
        Segment segment1 = new Segment(1, 3, 2, 1);
        Segment segment2 = new Segment(1, 1, 3, 3);
        Segment segment3 = new Segment(2, 4, 3, 1);
        Segment segment4 = new Segment(4, 3, 5, 1);
        tree.insert(segment1);
        tree.insert(segment2);
        tree.insert(segment3);
        tree.insert(segment4);
        assertEquals(segment3, tree.getData());
        assertEquals(segment1, tree.getLeft().getData());
        assertEquals(segment2, tree.getRight().getData());
        assertEquals(segment4, tree.getRight().getRight().getData());
        assertEquals(segment2, tree.getRight().getLeft().getData());
        assertEquals(segment1, tree.getLeft().getLeft().getData());
        assertEquals(segment3, tree.getLeft().getRight().getData());
    }

}