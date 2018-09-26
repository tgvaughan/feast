package feast.operators;

import beast.core.Input;
import beast.core.Operator;
import beast.core.parameter.BooleanParameter;
import beast.core.parameter.RealParameter;
import beast.util.Randomizer;

import java.util.HashSet;
import java.util.Set;

public class BlockScaleOperator extends Operator {

    public Input<RealParameter> parameterInput = new Input<>(
            "parameter", "Parameter to be scaled", Input.Validate.REQUIRED);

    public Input<BooleanParameter> indicatorInput = new Input<>(
            "indicator",
            "Boolean vector indicating which elements to scale. " +
                    "(If absent, all elements are scaled together.)");

    public Input<Double> scaleFactorInput = new Input<>(
            "scaleFactor", "Lower bound of the scale factor.",
            0.8);

    RealParameter parameter;
    BooleanParameter indicator;
    boolean hasIndicator;

    double scaleFactor;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        indicator = indicatorInput.get();
        hasIndicator = indicator != null;

        if (hasIndicator && indicator.getDimension() != parameter.getDimension())
            throw new IllegalArgumentException("Indicator and parameter dimension must match.");

        scaleFactor = scaleFactorInput.get();
    }

    /**
     * Set used for computing degrees of freedom.
     */
    private Set<Double> uniqueValues = new HashSet<>();

    /**
     * Determine the number of degrees of freedom (unique element values)
     * among the indicated elements.
     *
     * @return degrees of freedom
     */
    private int getScaledDegreesOfFreedom() {
        uniqueValues.clear();

        for (int i=0; i<parameter.getDimension(); i++) {
            if (hasIndicator && !indicator.getValue(i))
                continue;
            uniqueValues.add(parameter.getValue(i));
        }

        return uniqueValues.size();
    }

    /**
     * Select scale factor uniformly at random from [scaleFactor, 1/scaleFactor]
     *
     * @return scale factor
     */
    private double chooseScaleFactor() {
        return scaleFactor + Randomizer.nextDouble()*(1.0/scaleFactor - scaleFactor);
    }

    /**
     * Scale indicated elements of parameter by f.
     *
     * @param f scale factor
     * @return true if scaling succeeds, false if bounds are exceeded.
     */
    private boolean scaleParameter(double f) {

        for (int i=0; i<parameter.getDimension(); i++) {
            if (hasIndicator && !indicator.getValue(i))
                continue;

            double newVal = parameter.getValue(i)*f;

            if (newVal>parameter.getUpper() || newVal<parameter.getLower())
                return false;

            parameter.setValue(i, newVal);
        }

        return true;
    }

    @Override
    public double proposal() {

        double f = chooseScaleFactor();

        if (!scaleParameter(f))
            return Double.NEGATIVE_INFINITY;

        return Math.log(f)*(getScaledDegreesOfFreedom()-2);
    }
}
