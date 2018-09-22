package eu.andredick.tools.orlib;

import eu.andredick.scp.SCProblem;

import java.io.IOException;
import java.io.InputStream;

public interface AbstractConverter {

    public SCProblem convert(InputStream in) throws IOException;

}
