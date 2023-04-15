package eu.andredick.tools;

import eu.andredick.scp.SCProblem;
import eu.andredick.scp.Structure;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.jmatio.ImportMatrixMAT;

import java.io.File;
import java.io.IOException;

public class MatConvert {

    /**
     * 通过.mat文件导入覆盖矩阵，并设置所有子集权重为1<br>
     * .mat文件应放置在指定路径下:"resources\mat\"
     * @param mat_name      文件名
     * @return              SCP问题实例
     */
    public static SCProblem Mat_to_SCP (String mat_name){
        String matPath = "resources\\mat\\";
        File file = new File(matPath + mat_name);
        Matrix data = null;
        try {
            data = ImportMatrixMAT.fromFile(file);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        boolean[][] Coverage = data.toBooleanArray();
        Structure structure = new Structure(Coverage);
        int cols = structure.subsetsSize();
        Matrix objC = DenseMatrix.Factory.ones(1,cols);
        float[] objCoeff = objC.toFloatArray()[0];
        SCProblem problem = new SCProblem(Coverage, objCoeff);
        return problem;
    }

}
