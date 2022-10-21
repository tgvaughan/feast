package feast.fileio;


import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;

import java.io.*;

@Description("Initializes a RealParameter with values read from a CSV/TSV file " +
        "in row-major order.")
public class RealParameterFromXSV extends RealParameter {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of CSV/TSV file to extract values from.",
            Input.Validate.REQUIRED);

    public Input<String> sepInput = new Input<>("sep",
            "Separator for CSV/TSV file.  Default is TAB.","\t");

    public Input<Integer> startRowInput = new Input<>("startRow",
            "First row to include in the parameter. (Default 0.)",
            0);

    public Input<Integer> rowCountInput = new Input<>("rowCount",
            "Maximum number of rows to include. (Default all.)",
            Integer.MAX_VALUE);

    public Input<Integer> startColInput = new Input<>("startCol",
            "First column to include in the parameter. (Default 0.)",
            0);

    public Input<Integer> colCountInput = new Input<>("colCount",
            "Maximum number of columns to include. (Default all.)",
            Integer.MAX_VALUE);

    public RealParameterFromXSV() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {

        if (valuesInput.get().isEmpty()) { // Guard against double-initialization
            try (BufferedReader is = new BufferedReader(new FileReader(fileNameInput.get()))) {
                readRowMajor(is);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Error reading from file " + fileNameInput.get());
            }
        }

        super.initAndValidate();
    }
    
    void readRowMajor(BufferedReader is) throws IOException {
        int thisRow = 0;
        String thisLine;

        while ((thisLine = is.readLine()) != null) {
            if (thisRow >= startRowInput.get()) {

                if (thisRow - startRowInput.get() + 1 > rowCountInput.get())
                    break;

                int col=0;
                for (String field : thisLine.split(sepInput.get())) {
                    if (col >= startColInput.get()) {

                        if (col - startColInput.get() + 1 > colCountInput.get())
                            break;

                            valuesInput.setValue(Double.valueOf(field), this);
                    }

                    col ++;
                }
            }
            thisRow += 1;
        }
    }
}
