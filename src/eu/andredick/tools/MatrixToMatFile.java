import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLDouble;
import java.io.IOException;

public class MatrixToMatFile {
    public static void main(String[] args) {
        double[][] matrix = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0},
                {7.0, 8.0, 9.0}
        };

        // Create MLDouble object to hold the matrix data
        MLDouble mlDouble = new MLDouble("matrix", matrix);

        // Write MLDouble object to .mat file
        MatFileWriter matFileWriter = new MatFileWriter();
        matFileWriter.addVariable("matrix", mlDouble);

        try {
            matFileWriter.write("output.mat");
            System.out.println("Matrix saved to output.mat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
