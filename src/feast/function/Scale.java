package feast.function;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;

import java.util.ArrayList;
import java.util.List;

@Description("A Function whose elements are the elements of an input Function " +
        "scaled by another input Function.")
public class Scale extends LoggableFunction {

    public Input<Function> functionInput = new Input<>("function",
            "Function to scale", Input.Validate.REQUIRED);

    public Input<List<Function>> scalingFactorsInput = new Input<>("scaleBy",
            "Amount to scale by", new ArrayList<>());

    Function function;
    List<Function> scalingFactors;

    @Override
    public void initAndValidate() {
        function = functionInput.get();
        scalingFactors = scalingFactorsInput.get();

        for (Function scalingFactor : scalingFactors)
        if (scalingFactor.getDimension() != 1)
            throw new IllegalArgumentException("Dimension of scaleBy argument to Scale is not 1.");
    }

    @Override
    public int getDimension() {
        return function.getDimension();
    }

    @Override
    public double getArrayValue(int i) {
        double res=function.getArrayValue(i);
        for (Function scalingFactor : scalingFactors)
            res *= scalingFactor.getArrayValue();

        return res;
    }
}
