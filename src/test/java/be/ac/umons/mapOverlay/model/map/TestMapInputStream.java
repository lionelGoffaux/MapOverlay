package be.ac.umons.mapOverlay.model.map;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestMapInputStream {

    @Test
    public void readMapTest() throws FileNotFoundException, IOException {
        MapInputStream mis = new MapInputStream("cartes/test.txt");
        Map result = mis.readMap();
    }

    @Test
    public void FileNotExistTest(){
        assertThrows(FileNotFoundException.class, () -> {
            new MapInputStream("randomname42.dat");
        });
    }
}
