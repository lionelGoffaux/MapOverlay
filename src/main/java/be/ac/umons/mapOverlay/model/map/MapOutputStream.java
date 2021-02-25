package be.ac.umons.mapOverlay.model.map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapOutputStream {

    private final FileWriter fileWriter;

    public MapOutputStream(String path) throws IOException {
        this(new FileWriter("path"));
    }

    public MapOutputStream(File file) throws IOException {
        this(new FileWriter(file));
    }

    public MapOutputStream(FileWriter fileWriter){
        this.fileWriter = fileWriter;
    }

    public void writeMap(Map map) throws IOException {
        for(Segment s: map.getSegments()){
            writeSegment(s);
        }
    }

    private void writeSegment(Segment s) throws IOException {
        Point upperPoint = s.getUpperPoint();
        fileWriter.write(Double.toString(upperPoint.getX()) + " " + Double.toString(upperPoint.getY()) + " ");
        Point lowerPoint = s.getLowerPoint();
        fileWriter.write(Double.toString(lowerPoint.getX()) + " " + Double.toString(lowerPoint.getY()) + "\n");
    }

    public void close() throws IOException{
        this.fileWriter.close();
    }

}
