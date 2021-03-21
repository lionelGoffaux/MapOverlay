package be.ac.umons.mapOverlay.model.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static java.lang.Math.sqrt;

class PointTest {
    @Test
    public void normTest(){
        Point point = new Point(0, 1);
        Assertions.assertEquals(0, Double.compare(point.getNorm(), 1));
        point = new Point(1, 1);
        Assertions.assertEquals(0, Double.compare(point.getNorm(), sqrt(2)));
    }


    @Test
    public void upperThanTest(){
        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 1);
        Assertions.assertTrue(point1.isUpperThan(point2));
    }

    @Test
    public void horizontalPointTest(){
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 1);
        Assertions.assertTrue(point1.isUpperThan(point2));
        Assertions.assertFalse(point2.isUpperThan(point1));
    }

    @Test
    public void scalarProductTest(){
        Point point1 = new Point(1, 2);
        Point point2 = new Point(5, 3);
        Assertions.assertEquals(0, Double.compare(point1.scalarProduct(point2), 11));
    }

    @Test
    public void perpendicularVectorTest(){
        Point point1 = new Point(1, 2);
        Point point2 = new Point(2, -1);
        Assertions.assertEquals(0, Double.compare(point1.scalarProduct(point2), 0));
    }

    @Test
    public void orientationTest(){
        Point point1 = new Point(0,0);
        Point point2 = new Point(1,1);
        Assertions.assertTrue(point1.isOriented(point2));
        point1 = new Point(2,1);
        point2 = new Point(1, .5);
        Assertions.assertTrue(point1.isOriented(point2));
        Assertions.assertTrue(point2.isOriented(point1));
        point1 = new Point(1,2);
        point2 = new Point(1, .5);
        Assertions.assertFalse(point1.isOriented(point2));
    }

    @Test
    public void comparisonTest(){
        Point point1 = new Point(1, 1);
        Point point2 = new Point(0, 0);
        Point point3 = new Point(1, 1);
        Point point4 = new Point(0, 1);
        Assertions.assertEquals(1, point1.compareTo(point2));
        Assertions.assertEquals(0, point1.compareTo(point3));
        Assertions.assertEquals(-1, point4.compareTo(point1));
    }

    @Test
    public void comparisonXTest(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1, 1);

        Assertions.assertEquals(-1, p1.compareX(p2));
        Assertions.assertEquals(-1, p1.compareX(p4));
        Assertions.assertEquals(0, p1.compareX(p3));

        Assertions.assertEquals(1, p2.compareX(p1));
        Assertions.assertEquals(1, p2.compareX(p3));
        Assertions.assertEquals(0, p2.compareX(p4));

        Assertions.assertEquals(-1, p3.compareX(p2));
        Assertions.assertEquals(-1, p3.compareX(p4));
        Assertions.assertEquals(0, p3.compareX(p1));

        Assertions.assertEquals(1, p4.compareX(p1));
        Assertions.assertEquals(1, p4.compareX(p3));
        Assertions.assertEquals(0, p4.compareX(p2));
    }

    @Test
    public void comparisonYTest(){
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1, 1);

        Assertions.assertEquals(-1, p1.compareY(p3));
        Assertions.assertEquals(-1, p1.compareY(p4));
        Assertions.assertEquals(0, p1.compareY(p2));

        Assertions.assertEquals(0, p2.compareY(p1));
        Assertions.assertEquals(-1, p2.compareY(p3));
        Assertions.assertEquals(-1, p2.compareY(p4));

        Assertions.assertEquals(1, p3.compareY(p2));
        Assertions.assertEquals(0, p3.compareY(p4));
        Assertions.assertEquals(1, p3.compareY(p1));

        Assertions.assertEquals(1, p4.compareY(p1));
        Assertions.assertEquals(0, p4.compareY(p3));
        Assertions.assertEquals(1, p4.compareY(p2));
    }

}