package eu.andredick.tools.orlib;

import eu.andredick.scp.SCProblem;
import eu.andredick.tools.ArrayTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConverterRail implements AbstractConverter {

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

        int columnCounter = 0;
        while ((zeile = reader.readLine()) != null) {
            fileRow = ArrayTools.stringToIntArray(zeile);
            objCoeff[columnCounter] = fileRow[0];
            for (int i = 2; i < fileRow.length; i++) {
                int iRow = fileRow[i] - 1;
                ralations[iRow][columnCounter] = true;
            }
            columnCounter++;
        }

        return new SCProblem(ralations, objCoeff);
    }
}
