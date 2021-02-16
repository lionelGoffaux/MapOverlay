package be.ac.umons.mapOverlay.model.map;

import java.io.*;
import java.util.Scanner;
import java.util.Locale;

public class MapInputStream {

    private Scanner scanner;

    public MapInputStream(String path) throws FileNotFoundException {
        this(new FileReader(path));
    }

    public MapInputStream(File file) throws  FileNotFoundException {
        this(new FileReader(file));
    }

    public MapInputStream(FileReader fileReader){
        scanner = new Scanner(fileReader);
        scanner.useLocale(Locale.US);
    }

    private Segment readSegment(){
        double x1 = scanner.nextDouble(), y1 = scanner.nextDouble();
        double x2 = scanner.nextDouble(), y2 = scanner.nextDouble();
        Segment segment = new Segment(x1, y1, x2, y2);
        return segment;
    }

    public Map readMap(){
        Map map = new Map();
        while (scanner.hasNext()) {
            map.addSegment(readSegment());
        }
        return map;
    }
}
