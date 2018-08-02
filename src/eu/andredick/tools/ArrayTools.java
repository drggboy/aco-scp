package eu.andredick.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArrayTools {
    public static boolean[][] getZeroBool2DArray(int rows, int cols) {
        boolean[][] array = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = false;
            }
        }
        return array;
    }

    public static boolean[] getZeroBoolArray(int cols) {
        boolean[] array = new boolean[cols];
        for (int i = 0; i < cols; i++) {
            array[i] = false;
        }
        return array;
    }

    /*Tools--------------------------------------
     * Fisher-Yates-Verfahren Besser???
     * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle*/
    public static ArrayList<Integer> getRandomIntPermutation(ArrayList<Integer> list) {
        Random rGenerator = new Random();
        int size = list.size();
        for (int i = size - 1; i >= 1; i--) {
            int j = rGenerator.nextInt(i + 1); // i+1 wegen exklusiver oberer Schranke
            Integer t = list.get(i);
            list.set(i, j);
            list.set(j, t);
        }
        return list;
    }

    /*Tools--------------------------------------
     ** Fisher-Yates-Verfahren*/
    public static ArrayList<Integer> getRandomIntPermutation(int size) {
        Random rGenerator = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= size - 1; i++) {
            int j = rGenerator.nextInt(i + 1); // i+1 wegen exklusiver oberer Schranke
            if (j != i) {
                list.set(i, list.get(j));
            }
            list.set(j, i); // wobei i eigentlich eine Funktion f(i) ist
        }
        return list;
    }

    public static float[] getRandomFloatArray(int length, float from, float to) {
        Random r = new Random();
        float[] array = new float[length];
        float delta = to - from;
        for (int i = 0; i < array.length; i++) {
            array[i] = from + r.nextFloat() * delta;
        }

        return array;
    }

    public static float[] getFloatArray(int length, float value) {
        float[] array = new float[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = value;
        }

        return array;
    }

    public static float[] getFloatArray(int from, int length) {
        float[] array = new float[length];
        for (int i = 0; i < length; i++) {
            array[i] = i + from;
        }

        return array;
    }

    public static int[] stringToIntArray(String s) {
        String[] items = s.trim().split("\\s+");
        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                return null;
            }
            ;
        }

        return results;
    }

    public static float[] getAverageValuesOfArrays(List<float[]> arrays) {
        int minLength = arrays.get(0).length;
        int maxLength = arrays.get(0).length;
        for (float[] a : arrays) {
            if (a.length < minLength) {
                minLength = a.length;
            }
            if (a.length > maxLength) {
                maxLength = a.length;
            }
        }

        float[] averageArray = new float[minLength];
        for (int i = 0; i < minLength; i++) {
            float sum = 0f;
            for (float[] array : arrays) {
                sum += array[i];
            }
            averageArray[i] = sum / (float) arrays.size();
        }
        return averageArray;
    }

    public static float[] getMaxValuesOfArrays(List<float[]> arrays) {
        int minLength = arrays.get(0).length;
        int maxLength = arrays.get(0).length;
        for (float[] a : arrays) {
            if (a.length < minLength) {
                minLength = a.length;
            }
            if (a.length > maxLength) {
                maxLength = a.length;
            }
        }

        float[] array = new float[minLength];
        for (int i = 0; i < minLength; i++) {
            float bestValue_i = arrays.get(0)[i];
            for (int j = 1; j < arrays.size(); j++) {
                if (arrays.get(j)[i] < bestValue_i) {
                    bestValue_i = arrays.get(j)[i];
                }
            }
            array[i] = bestValue_i;

        }
        return array;
    }

    public static float[] getMinValuesOfArrays(List<float[]> arrays) {
        int minLength = arrays.get(0).length;
        int maxLength = arrays.get(0).length;
        for (float[] a : arrays) {
            if (a.length < minLength) {
                minLength = a.length;
            }
            if (a.length > maxLength) {
                maxLength = a.length;
            }
        }

        float[] array = new float[minLength];
        for (int i = 0; i < minLength; i++) {
            float worstValue_i = arrays.get(0)[i];
            for (int j = 1; j < arrays.size(); j++) {
                if (arrays.get(j)[i] > worstValue_i) {
                    worstValue_i = arrays.get(j)[i];
                }
            }
            array[i] = worstValue_i;

        }
        return array;
    }

    public static int getIndexOfMinValue(float[] values) {
        int minIndex = 0;
        float minValue = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < minValue) {
                minValue = values[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static float getMinValue(float[] values) {
        return values[getIndexOfMinValue(values)];
    }

    public static int getIndexOfMaxValue(float[] values) {
        int maxIndex = 0;
        float maxValue = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > maxValue) {
                maxValue = values[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static float getMaxValue(float[] values) {
        return values[getIndexOfMaxValue(values)];
    }

    public static float[] getProgressiveMinValueArray(float[] values) {
        float[] progressiveMinValues = new float[values.length];
        progressiveMinValues[0] = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < progressiveMinValues[i - 1]) {
                progressiveMinValues[i] = values[i];
            } else {
                progressiveMinValues[i] = progressiveMinValues[i - 1];
            }
        }
        return progressiveMinValues;
    }

    public static float[] getDifferenceOfArrays(float[] values1, float[] values2) {
        float[] resultArray = new float[values1.length];
        for (int i = 0; i < values1.length; i++) {
            resultArray[i] = values1[i] - values2[i];
        }
        return resultArray;
    }

    public static float getSum(float[] values) {
        float sum = 0;
        for (float i : values) {
            sum += i;
        }
        return sum;
    }

    public static float getMinValue(float[][] values) {
        float minValue = values[0][0];
        for (float[] row : values) {
            float currentMinValue = getMinValue(row);
            if (currentMinValue < minValue) {
                minValue = currentMinValue;
            }
        }
        return minValue;
    }


    public static float getMaxValue(float[][] values) {
        float maxValue = values[0][0];
        for (float[] row : values) {
            float currentMaxValue = getMaxValue(row);
            if (currentMaxValue > maxValue) {
                maxValue = currentMaxValue;
            }
        }
        return maxValue;
    }
}

