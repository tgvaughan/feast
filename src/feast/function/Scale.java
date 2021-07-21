package feast.function;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;

@Description("A Function whose elements are the elements of an input Function " +
        "scaled by another input Function.")
public class Scale extends LoggableFunction {

    public Input<Function> functionInput = new Input<>("function",
            "Function to scale", Input.Validate.REQUIRED);

    public Input<Function> scaleInput = new Input<>("scaleBy",
            "Amount to scale by", Input.Validate.REQUIRED);

    Function function, scaleBy;

    @Override
    public void initAndValidate() {
        function = functionInput.get();
        scaleBy = scaleInput.get();

        if (scaleBy.getDimension() != 1)
            throw new IllegalArgumentException("Dimension of scaleBy argument to Scale is not 1.");
    }

    @Override
    public int getDimension() {
        return function.getDimension();
    }

    @Override
    public double getArrayValue(int i) {
        return function.getArrayValue(i)*scaleBy.getArrayValue();
    }
}
