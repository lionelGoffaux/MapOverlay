package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

public class MapInputFileTest {

    @Test
    public void readMapTest() throws  IOException {
        ArrayList<Segment> expected = new ArrayList<>();
        expected.add(new Segment(1, 2, 3, 4));
        expected.add(new Segment(4, 3, 2, 1));

        MapInputFile mif = new MapInputFile("cartes/test.txt");
        Map map = mif.readMap();
        Assertions.assertEquals(expected, map.getSegments());
        mif.close();
    }
}
