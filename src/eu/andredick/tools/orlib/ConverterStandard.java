package eu.andredick.tools.orlib;

import eu.andredick.scp.SCProblem;
import eu.andredick.tools.ArrayTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConverterStandard implements AbstractConverter {

    @Override
    public SCProblem convert(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String zeile = null;
        zeile = reader.readLine();
        int[] fileRow = ArrayTools.stringToIntArray(zeile);
        int rows = fileRow[0];
        int columns = fileRow[1];
        boolean[][] ralations = ArrayTools.getZeroBool2DArray(rows, columns);
        float[] objCoeff = new float[columns];

        System.out.println("Rows: " + rows + " Cols:" + columns);

        int coeffCounter = 0;
        while (coeffCounter < objCoeff.length - 1) {
            zeile = reader.readLine();
            fileRow = ArrayTools.stringToIntArray(zeile);
            for (int i = 0; i < fileRow.length; i++) {
                objCoeff[i + coeffCounter] = fileRow[i];
            }
            coeffCounter += fileRow.length;
        }
        System.out.println("系数计数器: " + coeffCounter);

        for (int i = 0; i < rows; i++) {
            zeile = reader.readLine();
            fileRow = ArrayTools.stringToIntArray(zeile);
            if (fileRow.length != 1) {
                System.out.println("包含多个数字");
            }
            int columnsInRow = fileRow[0];
            int counter = 0;
            while (counter < columnsInRow) {
                zeile = reader.readLine();
                fileRow = ArrayTools.stringToIntArray(zeile);
                for (int j = 0; j < fileRow.length; j++) {
                    int col = fileRow[j];
                    ralations[i][col - 1] = true;
                }
                counter += fileRow.length;
            }

        }
        return new SCProblem(ralations, objCoeff);
    }
}
