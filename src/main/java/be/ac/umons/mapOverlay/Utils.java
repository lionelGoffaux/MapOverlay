package be.ac.umons.mapOverlay;

import static java.lang.Math.abs;

public class Utils {
    public static boolean almostEqual(double u, double v){
        return abs(u-v) < 1e-5;
    }
}
