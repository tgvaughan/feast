package feast.fileio.logfileiterator;

import beast.base.core.BEASTObject;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;

public class LogFileRealParameter extends BEASTObject {

    public Input<String> fieldNameInput = new Input<>("fieldName", "Name of field in log file.",
            Input.Validate.REQUIRED);

    public Input<RealParameter> fieldParameterInput = new Input<>("fieldParameter",
            "Parameter with which to associate log file entry values.",
            Input.Validate.REQUIRED);

    public Input<Integer> fieldParameterIndexInput = new Input<>("fieldParameterIndex",
            "Index of element in parameter to initialize with field value.",
            0);

    String fieldName;
    RealParameter fieldParameter;
    int paramIndex;

    @Override
    public void initAndValidate() {
        fieldName = fieldNameInput.get();
        fieldParameter = fieldParameterInput.get();
        paramIndex = fieldParameterIndexInput.get();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldParameterValue(String newValue) {
        fieldParameter.setValue(paramIndex, Double.valueOf(newValue));
    }
}
