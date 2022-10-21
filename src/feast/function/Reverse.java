package feast.function;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;

@Description("A Function whose elements are the elements of the input Function but in reverse order.")
public class Reverse extends LoggableFunction {

    public Input<Function> functionInput = new Input<>("arg",
            "Argument to reverse elements of.", Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() { }

    @Override
    public int getDimension() {
        return functionInput.get().getDimension();
    }

    @Override
    public double getArrayValue(int dim) {
        return functionInput.get().getArrayValue(getDimension()-1-dim);
    }

}
