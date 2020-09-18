package feast.popmodels;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.parameter.RealParameter;
import beast.evolution.tree.coalescent.ConstantPopulation;
import beast.evolution.tree.coalescent.ExponentialGrowth;
import beast.evolution.tree.coalescent.PopulationFunction;

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

    List<PopulationFunction> popFuncs;
    Function changeTimes;

    @Override
    public void initAndValidate() {
        popFuncs = popFunctionsInput.get();
        changeTimes = changeTimesInput.get();

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

        double tshifted = idx > 0
                ? t - changeTimes.getArrayValue(idx-1)
                : t;

        return popFuncs.get(idx).getPopSize(tshifted);
    }

    @Override
    public double getIntensity(double t) {
        int idx = getIndex(t);

        double x = 0.0;
        double tprev = 0.0;

        for (int i=0; i<idx; i++) {
            x += popFuncs.get(i).getIntensity(changeTimes.getArrayValue(i) - tprev);
            tprev = changeTimes.getArrayValue(i);
        }

        return x + popFuncs.get(idx).getIntensity(t - tprev);
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
        ConstantPopulation pf2 = new ConstantPopulation();
        pf2.initByName("popSize", new RealParameter("5.0"));
        ExponentialGrowth pf3 = new ExponentialGrowth();
        pf3.initByName("popSize", new RealParameter("5.0"),
                "growthRate", new RealParameter("0.2"));
//        ConstantPopulation pf3 = new ConstantPopulation();
//        pf3.initByName("popSize", new RealParameter("2.0"));
        ConstantPopulation pf4 = new ConstantPopulation();
        pf4.initByName("popSize", new RealParameter("12.0"));
        ConstantPopulation pf5 = new ConstantPopulation();
        pf4.initByName("popSize", new RealParameter("7.5"));

        cpm.initByName(
                "populationModel", pf1,
                "populationModel", pf2,
                "populationModel", pf3,
                "populationModel", pf4,
                "populationModel", pf5,
                "changeTimes", new RealParameter("3.0 5.0 12.0 14.0")
        );

        try (PrintStream out = new PrintStream("out.txt")) {
//        try (PrintStream out = System.out) {
            out.println("t\tn\tx");
            for (int i = 0; i < 100; i++) {
                double t = i * 20.0 / 100;

                out.println(t + "\t" + cpm.getPopSize(t) + "\t" + cpm.getIntensity(t));
            }
        } catch (Exception ex) {

        }
    }
}
