package be.ac.umons.utils;

import static java.lang.Math.abs;

/**
 * Classe Utils
 */
public class Utils {
    /**
     * Vérifie l'égalité de deux doubles
     * @param u un double
     * @param v un double
     * @return l'égalité de u et v
     */
    public static boolean almostEqual(double u, double v){
        return abs(u-v) < 1e-9;
    }
}
