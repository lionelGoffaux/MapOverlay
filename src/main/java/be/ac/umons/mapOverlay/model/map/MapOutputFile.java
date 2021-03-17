package be.ac.umons.mapOverlay.model.map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapOutputFile { // TODO: tests

    private final FileWriter fileWriter;

    public MapOutputFile(String path) throws IOException {
        this(new FileWriter("path"));
    }

    public MapOutputFile(File file) throws IOException {
        this(new FileWriter(file));
    }

    public MapOutputFile(FileWriter fileWriter){
        this.fileWriter = fileWriter;
    }

    public void writeMap(Map map) throws IOException {
        for(Segment s: map.getSegments()){
            writeSegment(s);
        }
    }

    private void writeSegment(Segment s) throws IOException {
        Point upperPoint = s.getUpperPoint();
        fileWriter.write(upperPoint.getX() + " " + upperPoint.getY() + " ");
        Point lowerPoint = s.getLowerPoint();
        fileWriter.write(lowerPoint.getX() + " " + lowerPoint.getY() + "\n");
    }

    public void close() throws IOException{
        this.fileWriter.close();
    }

}
