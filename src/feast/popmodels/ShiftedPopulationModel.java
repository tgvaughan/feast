package feast.popmodels;

import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.evolution.tree.coalescent.PopulationFunction;

import java.util.List;

public class ShiftedPopulationModel extends PopulationFunction.Abstract {

    public Input<PopulationFunction> popFuncInput = new Input<>(
            "populationModel",
            "Population model to shift in time",
            Input.Validate.REQUIRED);

    public Input<Function> offsetInput = new Input<>(
            "offset",
            "Time offset to use.",
            Input.Validate.REQUIRED);

    PopulationFunction popFunc;
    Function offset;

    @Override
    public void initAndValidate() {
        popFunc = popFuncInput.get();
        offset = offsetInput.get();
    }

    @Override
    public List<String> getParameterIds() {
        return null;
    }

    @Override
    public double getPopSize(double t) {
        return popFunc.getPopSize(t+offset.getArrayValue());
    }

    @Override
    public double getIntensity(double t) {
        return popFunc.getIntensity(t+offset.getArrayValue());
    }

    @Override
    public double getInverseIntensity(double x) {
        throw new UnsupportedOperationException("Method not implemented for ShiftedPopulationModel.");
    }
}
