/*
 * Copyright (c) 2023 Tim Vaughan
 *
 * This file is part of feast.
 *
 * feast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * feast is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with feast. If not, see <https://www.gnu.org/licenses/>.
 */

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
import java.util.Arrays;
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
    double[] scaleFactorCache, intensityCache, changeTimesCache;
    boolean cachesDirty;

    @Override
    public void initAndValidate() {
        popFuncs = popFunctionsInput.get();
        changeTimes = changeTimesInput.get();
        makeContinuous = makeContinuousInput.get();

        scaleFactorCache = new double[popFuncs.size()];
        intensityCache = new double[popFuncs.size()];
        changeTimesCache = new double[changeTimes.getDimension()];

        Arrays.fill(scaleFactorCache, 1.0);

        cachesDirty = true;

        if (popFuncs.size() != changeTimes.getDimension()+1)
            throw new IllegalArgumentException("The dimension of changeTimes must be one less than the number of " +
                    "populationModel inputs.");

        super.initAndValidate();
    }

    @Override
    public List<String> getParameterIds() {
        return null;
    }

    public void updateCaches() {
        if (!cachesDirty)
            return;

        // Copy change times to array

        for (int i=0; i<changeTimes.getDimension(); i++)
            changeTimesCache[i] = changeTimes.getArrayValue(i);

        // Update scale factor cache

        if (makeContinuous) {
            double tprev = 0.0;
            scaleFactorCache[0] = 1.0;

            for (int i = 0; i < changeTimesCache.length; i++) {
                double Nend = popFuncs.get(i).getPopSize(changeTimesCache[i] - tprev) * scaleFactorCache[i];
                scaleFactorCache[i + 1] = Nend / popFuncs.get(i + 1).getPopSize(0);
                tprev = changeTimes.getArrayValue(i);
            }
        }

        // Update intensity cache

        intensityCache[0] = 0.0;
        double tprev = 0.0;

        for (int i=0; i < changeTimesCache.length; i++) {
            intensityCache[i+1] = intensityCache[i]
                    + popFuncs.get(i).getIntensity(changeTimesCache[i] - tprev)
                    / scaleFactorCache[i];
            tprev = changeTimesCache[i];
        }

    }

    @Override
    public double getPopSize(double t) {
        updateCaches();

        int idx = Arrays.binarySearch(changeTimesCache, t);

        if (idx < 0)
            idx = -idx - 1;

        double tprev = idx > 0
                ? changeTimesCache[idx-1]
                : 0.0;

        return popFuncs.get(idx).getPopSize(t - tprev) * scaleFactorCache[idx];
    }

    @Override
    public double getIntensity(double t) {
        updateCaches();

        int idx = Arrays.binarySearch(changeTimesCache, t);

        if (idx < 0)
            idx = -idx - 1;

        double tprev = idx > 0
                ? changeTimesCache[idx-1]
                : 0.0;

        return intensityCache[idx] + popFuncs.get(idx).getIntensity(t - tprev)
                /scaleFactorCache[idx];

    }

    @Override
    public double getInverseIntensity(double x) {
        updateCaches();

        int idx = Arrays.binarySearch(intensityCache, x);

        if (idx < 0)
            idx = -idx - 2;

        double xprev = idx >= 0
                ? intensityCache[idx]
                : 0.0;

        double tprev = idx-1 >= 0
                ? changeTimesCache[idx-1]
                : 0.0;

        int popIdx = Math.max(idx, 0);

        return tprev + popFuncs.get(popIdx).getInverseIntensity((x-xprev)*scaleFactorCache[popIdx]);
    }

    @Override
    protected boolean requiresRecalculation() {
        cachesDirty = true;
        return true;
    }

    @Override
    protected void restore() {
        cachesDirty = true;

        super.restore();
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
                "makeContinuous", true);

        double t0 = -1.0;
        double t1 = 10.0;
        int steps = 110;

//        try (PrintStream out = System.out) {
        try (PrintStream out = new PrintStream("out.txt")) {
            out.println("t\tn\tx\tinvx");
            for (int i = 0; i <= steps; i++) {
                double t = t0 + i * (t1-t0) / steps;

                out.println(t
                        + "\t" + cpm.getPopSize(t)
                        + "\t" + cpm.getIntensity(t)
                        + "\t" + cpm.getInverseIntensity(cpm.getIntensity(t)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
