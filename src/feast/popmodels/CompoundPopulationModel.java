package feast.popmodels;


import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.evolution.tree.coalescent.ConstantPopulation;
import beast.base.evolution.tree.coalescent.ExponentialGrowth;
import beast.base.evolution.tree.coalescent.PopulationFunction;
import beast.base.inference.parameter.RealParameter;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Description("Population model constructed by piecewise assembly of other population functions.")
public class CompoundPopulationModel extends PopulationFunction.Abstract {

    public Input<List<PopulationFunction>> popFunctionsInput = new Input<>("populationModel",
            "Population model segment", new ArrayList<>());

    public Input<Function> changeTimesInput = new Input<>("changeTimes",
            "Times of transitions between individual model segments.",
            Input.Validate.REQUIRED);

    public Input<Boolean> makeContinuousInput = new Input<>("makeContinuous",
            "Scale population functions to ensure resulting compound " +
                    "function is continuous. (Default false)", false);

    List<PopulationFunction> popFuncs;
    Function changeTimes;

    boolean makeContinuous;

    @Override
    public void initAndValidate() {
        popFuncs = popFunctionsInput.get();
        changeTimes = changeTimesInput.get();
        makeContinuous = makeContinuousInput.get();

        if (popFuncs.size() != changeTimes.getDimension()+1)
            throw new IllegalArgumentException("The dimension of changeTimes must be one less than the number of " +
                    "populationModel inputs.");

        super.initAndValidate();
    }

    @Override
    public List<String> getParameterIds() {
        return null;
    }

    @Override
    public double getPopSize(double t) {
        int idx = getIndex(t);

        double tprev;
        double scaleFactor = 1.0;

        if (makeContinuous) {
            tprev = 0.0;
            for (int i = 0; i < idx; i++) {
                double Nend = popFuncs.get(i).getPopSize(changeTimes.getArrayValue(i) - tprev) * scaleFactor;
                scaleFactor = Nend / popFuncs.get(i + 1).getPopSize(0);
                tprev = changeTimes.getArrayValue(i);
            }
        } else {
            tprev = idx > 0
                    ? changeTimes.getArrayValue(idx-1)
                    : 0.0;
        }

        return popFuncs.get(idx).getPopSize(t - tprev)*scaleFactor;
    }

    @Override
    public double getIntensity(double t) {
        int idx = getIndex(t);

        double x = 0.0;
        double tprev = 0.0;
        double scaleFactor = 1.0;

        for (int i=0; i<idx; i++) {
            x += popFuncs.get(i).getIntensity(changeTimes.getArrayValue(i) - tprev)/scaleFactor;

            if (makeContinuous) {
                double Nend = popFuncs.get(i).getPopSize(changeTimes.getArrayValue(i) - tprev) * scaleFactor;
                scaleFactor = Nend / popFuncs.get(i + 1).getPopSize(0);
            }

            tprev = changeTimes.getArrayValue(i);
        }

        return x + popFuncs.get(idx).getIntensity(t - tprev)/scaleFactor;
    }

    @Override
    public double getInverseIntensity(double x) {
        throw new UnsupportedOperationException("Method unimplemented for CompoundPopulationModel.");
    }

    /**
     * Find index of interval such that t \in [t_lower, t_upper), where
     * t_lower and t_upper are the times which bracket the interval.
     *
     * We don't use Array.binarySearch() so that we can avoid copying
     * the changeTimes Function into an array.
     *
     * @param t time
     * @return index of interval containing time.
     */
    private int getIndex(double t) {

        int imin = 0, imax = popFuncs.size()-1;

        while (imax != imin) {

            int imed = (imin + imax)/2;

            if (imed > 0 && changeTimes.getArrayValue(imed-1) > t)
                imax = imed-1;
            else if (imed < changeTimes.getDimension() && changeTimes.getArrayValue(imed) <= t)
                imin = imed+1;
            else
                return imed;
        }

        return imin;
    }

    /**
     * Main method for debugging.
     *
     * @param args
     */
    public static void main(String[] args) {

        CompoundPopulationModel cpm = new CompoundPopulationModel();
        ConstantPopulation pf1 = new ConstantPopulation();
        pf1.initByName("popSize", new RealParameter("1.0"));
        ExponentialGrowth pf2 = new ExponentialGrowth();
        pf2.initByName("popSize", new RealParameter("1.0"),
                "growthRate", new RealParameter("1.0"));
        ConstantPopulation pf3 = new ConstantPopulation();
        pf3.initByName("popSize", new RealParameter("2.0"));

        cpm.initByName(
                "populationModel", pf1,
                "populationModel", pf2,
                "populationModel", pf3,
                "changeTimes", new RealParameter("3.0 5.0"),
                "makeContinuous", false);

        try (PrintStream out = new PrintStream("out.txt")) {
//        try (PrintStream out = System.out) {
            out.println("t\tn\tx");
            for (int i = 0; i <= 100; i++) {
                double t = i * 10.0 / 100;

                out.println(t + "\t" + cpm.getPopSize(t) + "\t" + cpm.getIntensity(t));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
