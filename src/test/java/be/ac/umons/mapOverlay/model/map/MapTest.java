package be.ac.umons.mapOverlay.model.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MapTest {

    @Test
    public void equalsTest() throws IOException {
        MapInputFile mif = new MapInputFile("cartes/test1.txt");
        Map map1 = mif.readMap();
        mif.close();
        mif = new MapInputFile("cartes/test1.txt");
        Map map2 = mif.readMap();
        mif.close();

        Assertions.assertEquals(map1, map2);
    }
}
