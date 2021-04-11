package be.ac.umons.mapOverlay.model.map;

import be.ac.umons.mapOverlay.model.geometry.Segment;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Classe qui lit les fichiers de carte.
 */
public class MapInputFile {

    private final Scanner scanner;
    private final FileReader fileReader;

    public MapInputFile(String path) throws IOException {
        this(new FileReader(path));
    }

    public MapInputFile(File file) throws  IOException {
        this(new FileReader(file));
    }

    public MapInputFile(FileReader fileReader){
        this.fileReader = fileReader;
        scanner = new Scanner(fileReader);
        scanner.useLocale(Locale.US);
    }

    private Segment readSegment(){
        double x1 = scanner.nextDouble(), y1 = scanner.nextDouble();
        double x2 = scanner.nextDouble(), y2 = scanner.nextDouble();
        return new Segment(x1, y1, x2, y2);
    }

    /***
     * Retourne la map contenue dans le fichier.
     * @return
     */
    public Map readMap(){
        Map map = new Map();
        while (scanner.hasNext()) {
            map.addSegment(readSegment());
        }
        return map;
    }

    /**
     * Ferme le fichier contenant la carte.
     * @throws IOException
     */
    public void close() throws IOException {
        this.fileReader.close();
    }
}
