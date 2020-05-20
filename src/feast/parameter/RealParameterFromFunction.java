package feast.parameter;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.parameter.RealParameter;

@Description("A RealParameter initialized from a function.")
public class RealParameterFromFunction extends RealParameter {

    public Input<Function> functionInput = new Input<>("function",
            "Function used to initialize RealParameter.",
            Input.Validate.REQUIRED);

    public RealParameterFromFunction() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        for (double v : functionInput.get().getDoubleValues())
            valuesInput.setValue(v, this);

        super.initAndValidate();
    }
}
