package feast.operators;


import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;
import beast.base.util.Randomizer;

import java.text.DecimalFormat;

@Description("Random walk operator which modifies identical values together.")
public class SmartRealRandomWalkOperator extends SmartRealOperator {

    final public Input<Double> windowSizeInput =
            new Input<>("windowSize",
                    "the size of the window both up and down when " +
                            "using uniform interval OR standard deviation " +
                            "when using Gaussian",
                    Input.Validate.REQUIRED);

    final public Input<Boolean> useGaussianInput =
            new Input<>("useGaussian",
                    "Use Gaussian to move instead of uniform " +
                            "interval. Default false.",
                    false);

    double windowSize = 1;
    boolean useGaussian;

    @Override
	public void initAndValidate() {
        super.initAndValidate();

        windowSize = windowSizeInput.get();
        useGaussian = useGaussianInput.get();
    }

    /**
     * override this for proposals,
     * returns log of hastingRatio, or Double.NEGATIVE_INFINITY if proposal should not be accepted *
     */
    @Override
    public double proposal() {

        // Select class at random

        int classIdx = Randomizer.nextInt(nClasses);

        double delta;
        if (useGaussian) {
            delta = Randomizer.nextGaussian() * windowSize;
        } else {
            delta = Randomizer.nextDouble() * 2 * windowSize - windowSize;
        }

        for (RealParameter param : parameters) {
            Integer[] group = groups.get(param);

            for (int i=0; i<param.getDimension(); i++) {
                if (group[i] != classIdx)
                    continue;

                double newVal = param.getValue(i) + delta;

                if (newVal > param.getUpper() || newVal < param.getLower())
                    return Double.NEGATIVE_INFINITY;

                param.setValue(i, newVal);
            }
        }

        return 0.0;
    }


    @Override
    public double getCoercableParameterValue() {
        return windowSize;
    }

    @Override
    public void setCoercableParameterValue(double value) {
        windowSize = value;
    }

    /**
     * called after every invocation of this operator to see whether
     * a parameter can be optimised for better acceptance hence faster
     * mixing
     *
     * @param logAlpha difference in posterior between previous state & proposed state + hasting ratio
     */
    @Override
    public void optimize(double logAlpha) {
        // must be overridden by operator implementation to have an effect
        double delta = calcDelta(logAlpha);

        delta += Math.log(windowSize);
        windowSize = Math.exp(delta);
    }

    @Override
    public final String getPerformanceSuggestion() {
        double prob = m_nNrAccepted / (m_nNrAccepted + m_nNrRejected + 0.0);
        double targetProb = getTargetAcceptanceProbability();

        double ratio = prob / targetProb;
        if (ratio > 2.0) ratio = 2.0;
        if (ratio < 0.5) ratio = 0.5;

        // new scale factor
        double newWindowSize = windowSize * ratio;

        DecimalFormat formatter = new DecimalFormat("#.###");
        if (prob < 0.10) {
            return "Try setting window size to about " + formatter.format(newWindowSize);
        } else if (prob > 0.40) {
            return "Try setting window size to about " + formatter.format(newWindowSize);
        } else return "";
    }
}