package eu.andredick.tools;

import eu.andredick.aco.algorithm.Statistics;

import java.util.ArrayList;
import java.util.List;

public class Criterions {

    public static float RAR(float z, float z_opt) {
        return z / z_opt;
    }

    public static float RPZ(float z, float z_opt, float z_max) {
        return (z - z_opt) / (z_max - z_opt) * 100;
    }

    public static float RPD(float z, float z_opt) {
        return (z - z_opt) / (z_opt - 0) * 100;
    }

    public static float RPI(float z, float z_opt, float z_iter) {
        return (z_iter - z) / (z_iter - z_opt) * 100;
    }

    public static float getAverageBestValue(Statistics[] statistics) {
        float sum = 0;
        for (Statistics s : statistics) {
            sum += s.getGlobalMinValue();
        }
        return sum / statistics.length;
    }

    public static List<float[]> getArraysOfIterationBestValues(Statistics[] statistics) {
        List<float[]> arrays = new ArrayList<>();
        for (Statistics s : statistics) {
            arrays.add(s.getIterationMinValuesArray());
        }
        return arrays;
    }
}
