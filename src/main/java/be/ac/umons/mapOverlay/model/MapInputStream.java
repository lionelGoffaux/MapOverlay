package be.ac.umons.mapOverlay.model;

import java.io.*;

public class MapInputStream {

    private DataInputStream dis;

    public MapInputStream(String path) throws FileNotFoundException {
        dis = new DataInputStream(new FileInputStream(new File(path)));
    }

    private Segment readSegment() throws IOException, SegmentPointOrderException{
        Double x1 = dis.readDouble(), y1 = dis.readDouble(), x2 = dis.readDouble(), y2 = dis.readDouble();
        Segment segment = new Segment(x1, y1, x2, y2);
        return segment;
    }

    public Map readMap() throws IOException, SegmentPointOrderException{
        Map map = new Map();
        while (dis.available() > 0) {
            map.addSegment(readSegment());
        }
        return map;
    }
}
