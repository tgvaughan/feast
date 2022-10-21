package feast.fileio;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Description("Initializes a RealParameter with values read from a CSV/TSV file " +
        "in row-major order.")
public class RealParameterFromLabelledXSV extends RealParameter {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of CSV/TSV file to extract values from.",
            Input.Validate.REQUIRED);

    public Input<String> sepInput = new Input<>("sep",
            "Separator for CSV/TSV file.  Default is TAB.","\t");

    public Input<String> rowLabelsInput = new Input<>("rowLabels",
            "Labels of rows to include. (Default all.)");

    public Input<String> colLabelsInput = new Input<>("colLabels",
            "Labels of columns to include. (Default all.)");


    List<String> rowLabels = null, colLabels = null;
    String sep;

    public RealParameterFromLabelledXSV() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {

        if (valuesInput.get().isEmpty()) { // Guard against double-initialization
            if (rowLabelsInput.get() != null)
                rowLabels = Arrays.asList(rowLabelsInput.get().trim().split("\\s*,\\s*"));

            if (colLabelsInput.get() != null)
                colLabels = Arrays.asList(colLabelsInput.get().trim().split("\\s*,\\s*"));

            sep = "\\s*" + sepInput.get() + "\\s*";

            try (BufferedReader is = new BufferedReader(new FileReader(fileNameInput.get()))) {
                readRowMajor(is);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Error reading from file " + fileNameInput.get());
            }
        }

        super.initAndValidate();
    }
    
    void readRowMajor(BufferedReader is) throws IOException {
        int[] colIndices = null;
        if (colLabels != null) {
            List<String> colNames = Arrays.asList(is.readLine().trim().split(sep));
            colIndices = new int[colLabels.size()];
            for (int i=0; i<colIndices.length; i++)
                colIndices[i] = colNames.indexOf(colLabels.get(i));
        }

        String thisLine;
        while ((thisLine = is.readLine()) != null) {
            String[] elements = thisLine.trim().split(sep);

            if (rowLabels != null && !rowLabels.contains(elements[0]))
                continue;

            if (colIndices != null) {
                for (int i : colIndices)
                    valuesInput.setValue(Double.valueOf(elements[i]), this);
            } else {
                for (int i=1; i<elements.length; i++)
                    valuesInput.setValue(Double.valueOf(elements[i]), this);
            }
        }
    }
}
