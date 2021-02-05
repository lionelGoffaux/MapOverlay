package be.ac.umons.mapOverlay.model.map;

import java.io.*;
import java.util.Scanner;

public class MapInputStream {

    private Scanner scanner;

    public MapInputStream(String path) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(path));
    }

    private Segment readSegment() throws IOException{
        double x1 = scanner.nextDouble(), y1 = scanner.nextDouble(), x2 = scanner.nextDouble(), y2 = scanner.nextDouble();
        Segment segment = new Segment(x1, y1, x2, y2);
        return segment;
    }

    public Map readMap() throws IOException{
        Map map = new Map();
        while (scanner.hasNext()) {
            map.addSegment(readSegment());
        }
        return map;
    }
}
