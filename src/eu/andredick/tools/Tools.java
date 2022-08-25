package eu.andredick.tools;

import java.util.ArrayList;
import java.util.Random;

public class Tools {
    /**
     * 产生从 0 开始的顺序数列
     * @param size      指定数列大小
     * @return          顺序数列
     */
    public static ArrayList<Integer> getIndexList(int size) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        return indices;
    }

    /**
     * 产生随机的集合覆盖矩阵
     * @param m     元素个数
     * @param n     集合个数
     * @return      集合覆盖矩阵
     */
    /*All--------------------------------------*/
    public static int[][] getRandomSCP(int m, int n) {
        int[][] relations = new int[m][n];

        Random r = new Random();

        for (int i = 0; i < m; i++) {
            int counter = 0;  //确保每个元素都至少被一个集合覆盖
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
