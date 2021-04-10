package be.ac.umons.mapOverlay.model.intersectionsFinder;

import be.ac.umons.mapOverlay.model.geometry.Point;
import be.ac.umons.mapOverlay.model.geometry.Segment;
import be.ac.umons.mapOverlay.model.map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntersectionsFinderTest {

    @Test
    public void simpleIntersectionTest(){
        IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        Map map =  new Map();
        map.addSegment(new Segment(0, 0, 1, 1));
        map.addSegment(new Segment(0, 1, 0.9, 0.1));
        intersectionsFinder.setMap(map);

        Assertions.assertTrue(intersectionsFinder.getIntersections().isEmpty());

        intersectionsFinder.stepForward();
        Assertions.assertTrue(intersectionsFinder.getIntersections().isEmpty());

        intersectionsFinder.stepForward();
        Assertions.assertTrue(intersectionsFinder.getIntersections().isEmpty());

        intersectionsFinder.stepForward();
        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        Point p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);

        intersectionsFinder.stepForward();
        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());

        intersectionsFinder.stepForward();
        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
    }

    @Test
    public void multipleIntersectionTest(){
        IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        Map map =  new Map();
        map.addSegment(new Segment(0, 0, 1, 1));
        map.addSegment(new Segment(0, 1, 1, 0));
        map.addSegment(new Segment(0.5, 0, 0.5, 1));
        intersectionsFinder.setMap(map);
        intersectionsFinder.findAll();

        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        Point p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);
    }

    @Test
    public void HorizontalIntersectionTest(){
        IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        Map map =  new Map();
        map.addSegment(new Segment(0, 0, 1, 1));
        map.addSegment(new Segment(0, 0.5, 1, 0.5));
        intersectionsFinder.setMap(map);

        intersectionsFinder.findAll();


        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        Point p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);
    }

    @Test
    public void UpperIntersectionTest(){
        IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        Map map =  new Map();
        map.addSegment(new Segment(0, 0, 1, 1));
        map.addSegment(new Segment(0, 1, 0.5, 0.5));
        intersectionsFinder.setMap(map);
        intersectionsFinder.findAll();

        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        Point p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);


        intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        map =  new Map();
        map.addSegment(new Segment(0.5, 0.5, 1, 1));
        map.addSegment(new Segment(0, 1, 1, 0));
        intersectionsFinder.setMap(map);
        intersectionsFinder.findAll();

        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);
    }

    @Test
    public void HorizontalUpperPointIntersectionTest(){
        IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        Map map =  new Map();
        map.addSegment(new Segment(0, 0, 1, 1));
        map.addSegment(new Segment(0.5, 0.5, 1, 0.5));
        intersectionsFinder.setMap(map);
        intersectionsFinder.findAll();

        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        Point p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);

        intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        map =  new Map();
        map.addSegment(new Segment(0.5, 0.5, 1, 1));
        map.addSegment(new Segment(0., 0.5, 1, 0.5));
        intersectionsFinder.setMap(map);
        intersectionsFinder.findAll();

        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);
    }

    @Test
    public void endPointIntersectionTest(){
        IntersectionsFinder intersectionsFinder = new IntersectionsFinder();
        IntersectionsFinder.instance = intersectionsFinder;
        Map map =  new Map();
        map.addSegment(new Segment(0, 0, 0.5, 0.5));
        map.addSegment(new Segment(0, 1, 0.5, 0.5));
        intersectionsFinder.setMap(map);
        intersectionsFinder.findAll();

        Assertions.assertEquals(1, intersectionsFinder.getIntersections().size());
        Point p =  intersectionsFinder.getIntersections().get(0);
        Assertions.assertEquals(new Point(0.5, 0.5), p);
    }

}
