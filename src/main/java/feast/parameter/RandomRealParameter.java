/*
 * Copyright (c) 2023 Ugne Stolz, ETH Zurich
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

package feast.parameter;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.StateNode;
import beast.base.inference.StateNodeInitialiser;
import beast.base.inference.distribution.OneOnX;
import beast.base.inference.distribution.ParametricDistribution;
import beast.base.inference.distribution.Uniform;
import beast.base.inference.parameter.RealParameter;
import org.apache.commons.math.MathException;

import java.util.List;

@Description("Randomly innitialise a RealParameter by sampling from a ParametricDistribution.")
public class RandomRealParameter extends RealParameter implements StateNodeInitialiser {
    final public Input<RealParameter> initialInput = new Input<>("initial",
            "Parameter to initialize. (If absent, initialise RandomRealParameter itself.)");
    final public Input<ParametricDistribution> distributionInput = new Input<>("distr",
            "Distribution from which to draw a random value. Usually the prior ditribution for this parameter.",
            Input.Validate.REQUIRED);


    public RandomRealParameter() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        if (valuesInput.get().isEmpty()) {
            if (initialInput.get() == null)
                throw new IllegalArgumentException("Either value or initial input of RandomRealParameter must be set.");
        } else {
            super.initAndValidate();
        }

        initStateNodes();
    }

    @Override
    public void initStateNodes() {
        if (initialInput.get() != null)
            sampleParameter(initialInput.get());
        else
            sampleParameter(this);
    }

    protected void sampleParameter(RealParameter parameter) {
        int dim = parameter.getDimension();
        ParametricDistribution distribution = distributionInput.get();
        if (distribution instanceof Uniform)
            if (Double.isInfinite(((Uniform) distribution).lowerInput.get()) ||
                    Double.isInfinite(((Uniform) distribution).upperInput.get())){
                System.out.println("Cannot sample from improper Uniform distribution." +
                        "One or both bounds are infinite.");
                System.exit(1);
            }
        if (distribution instanceof OneOnX){
            System.out.println("Currently not implemented for OneOnX distribution.");
            System.exit(1);
        }

        for (int i = 0; i < dim; i++){
            Double rnd;
            try {
                do {
                    rnd  = distribution.sample(1)[0][0];
                } while (rnd < parameter.getLower() || rnd > parameter.getUpper());
            } catch (MathException e) {
                throw new RuntimeException(e);
            }
            parameter.setValue(i, rnd);
        }
    }

    @Override
    public void getInitialisedStateNodes(List<StateNode> stateNodes) {
        stateNodes.add(initialInput.get());
    }


}
