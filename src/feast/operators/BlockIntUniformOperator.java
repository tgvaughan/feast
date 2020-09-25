package feast.operators;

import beast.core.Input;
import beast.core.Operator;
import beast.core.parameter.BooleanParameter;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.RealParameter;
import beast.util.Randomizer;

import java.util.HashSet;
import java.util.Set;

public class BlockIntUniformOperator extends Operator {

    public Input<IntegerParameter> parameterInput = new Input<>(
            "parameter", "Parameter to operate on", Input.Validate.REQUIRED);

    public Input<BooleanParameter> indicatorInput = new Input<>(
            "indicator",
            "Boolean vector indicating which elements to operate on. " +
                    "(If absent, all elements are operated on.)");

    IntegerParameter parameter;
    BooleanParameter indicator;
    boolean hasIndicator;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        indicator = indicatorInput.get();
        hasIndicator = indicator != null;

        if (hasIndicator && indicator.getDimension() != parameter.getDimension())
            throw new IllegalArgumentException("Indicator and parameter dimension must match.");
    }

    @Override
    public double proposal() {

        for (int i=0; i<parameter.getDimension(); i++) {
            if (hasIndicator && !indicator.getValue(i))
                continue;

            int newVal = parameter.getLower() + Randomizer.nextInt(parameter.getUpper() - parameter.getLower() + 1);

            if (newVal>parameter.getUpper() || newVal<parameter.getLower())
                return Double.NEGATIVE_INFINITY;

            parameter.setValue(i, newVal);
        }

        return 0.0;
    }
}
