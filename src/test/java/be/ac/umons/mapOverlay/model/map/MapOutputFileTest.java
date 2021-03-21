package be.ac.umons.mapOverlay.model.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class MapOutputFileTest {

    @Test
    public void MapOutputTest() throws IOException {
        MapInputFile mif = new MapInputFile("cartes/test.txt");
        Map map1 = mif.readMap();
        mif.close();

        File save = new File("cartes/testout.txt");
        MapOutputFile mof = new MapOutputFile(save);
        mof.writeMap(map1);
        mof.close();

        mif = new MapInputFile(save);
        Map map2 = mif.readMap();
        mif.close();

        save.delete();
        Assertions.assertEquals(map1, map2);
    }
}
