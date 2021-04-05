package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapOutputFile {

    private final FileWriter fileWriter;

    public MapOutputFile(File file) throws IOException {
        this(new FileWriter(file));
    }

    public MapOutputFile(FileWriter fileWriter){
        this.fileWriter = fileWriter;
    }

    /***
     * Ecrit une map dans un fichier.
     * @param map
     * @throws IOException
     */
    public void writeMap(Map map) throws IOException {
        for(Segment s: map.getSegments()){
            writeSegment(s);
        }
    }

    private void writeSegment(Segment s) throws IOException {
        fileWriter.write(s + "\n");
    }

    public void close() throws IOException{
        this.fileWriter.close();
    }

}
