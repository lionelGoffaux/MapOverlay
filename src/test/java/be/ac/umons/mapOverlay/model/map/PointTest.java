package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Point;
import org.junit.jupiter.api.Test;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    public void normTest(){
        Point point = new Point(0, 1);
        assertEquals(0, Double.compare(point.getNorm(), 1));
        point = new Point(1, 1);
        assertEquals(0, Double.compare(point.getNorm(), sqrt(2)));
    }


    @Test
    public void upperThanTest(){
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 1);
        assertTrue(point2.isUpperThan(point1));
    }

    @Test
    public void horizontalPointTest(){
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 1);
        assertTrue(point1.isUpperThan(point2));
        assertFalse(point2.isUpperThan(point1));
    }

    @Test
    public void scalarProductTest(){
        Point point1 = new Point(1, 2);
        Point point2 = new Point(5, 3);
        assertEquals(0, Double.compare(point1.scalarProduct(point2), 11));
    }

    @Test
    public void perpendicularVectorTest(){
        Point point1 = new Point(1, 2);
        Point point2 = new Point(2, -1);
        assertEquals(0, Double.compare(point1.scalarProduct(point2), 0));
    }

    @Test
    public void orientationTest(){
        Point point1 = new Point(0,0);
        Point point2 = new Point(1,1);
        assertTrue(point1.isOriented(point2));
        point1 = new Point(2,1);
        point2 = new Point(1, .5);
        assertTrue(point1.isOriented(point2));
        assertTrue(point2.isOriented(point1));
        point1 = new Point(1,2);
        point2 = new Point(1, .5);
        assertFalse(point1.isOriented(point2));
    }

    @Test
    public void comparisonTest(){
        Point point1 = new Point(1, 1);
        Point point2 = new Point(0, 0);
        Point point3 = new Point(1, 1);
        Point point4 = new Point(0, 1);
        assertEquals(1, point1.compareTo(point2));
        assertEquals(0, point1.compareTo(point3));
        assertEquals(1, point4.compareTo(point1));
    }



}