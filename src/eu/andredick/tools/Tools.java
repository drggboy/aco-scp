package eu.andredick.tools;

import java.util.ArrayList;
import java.util.Random;

public class Tools {

    public static ArrayList<Integer> getIndexList(int size) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        return indices;
    }

    /*All--------------------------------------*/
    public static int[][] getRandomSCP(int m, int n) {
        int[][] relations = new int[m][n];

        Random r = new Random();

        for (int i = 0; i < m; i++) {
            int counter = 0;  //Sicherstellen, dass jedes Element in min Subset vorhanden
            for (int j = 0; j < n; j++) {
                relations[i][j] = r.nextBoolean() ? 1 : 0;
                counter += relations[i][j];
            }
            if (counter == 0) {
                relations[i][r.nextInt(n)] = 1;
            }
        }
        return relations;
    }


}
