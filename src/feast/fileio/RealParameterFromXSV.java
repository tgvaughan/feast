package feast.fileio;

import beast.core.Input;
import beast.core.parameter.RealParameter;

import java.io.*;

public class RealParameterFromXSV extends RealParameter {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of CSV/TSV file to extract values from.",
            Input.Validate.REQUIRED);

    public Input<Integer> rowInput = new Input<>("row", "Index of CSV/TSV file row to extract.");

    public Input<String> sepInput = new Input<>("sep",
            "Separator for CSV/TSV file.  Default is TAB.","\t");

    public Input<Integer> startColInput = new Input<>("startCol",
            "First column to include in the parameter.",
            0);

    public RealParameterFromXSV() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {

        int thisRow = 0;

        try (BufferedReader is = new BufferedReader(new FileReader(fileNameInput.get()))) {

            String thisLine;
            while ((thisLine = is.readLine()) != null) {
                if (thisRow == rowInput.get()) {

                    int col=0;
                    for (String field : thisLine.split(sepInput.get())) {
                        if (col >= startColInput.get())
                            valuesInput.setValue(Double.valueOf(field), this);

                        col ++;
                    }

                    break;
                }
                thisRow += 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        super.initAndValidate();
    }
}
