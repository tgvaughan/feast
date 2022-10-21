package feast.parameter;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;

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
