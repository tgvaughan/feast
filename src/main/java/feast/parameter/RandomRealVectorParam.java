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
import beast.base.spec.domain.Real;
import beast.base.spec.inference.distribution.ScalarDistribution;
import beast.base.spec.inference.parameter.RealScalarParam;
import beast.base.spec.inference.parameter.RealVectorParam;

import java.util.List;

@Description("Randomly initialise a RealVectorParam by sampling from a ParametricDistribution.")
public class RandomRealVectorParam extends RealVectorParam<Real> implements StateNodeInitialiser {
    final public Input<RealVectorParam<Real>> initialInput = new Input<>("initial",
            "Parameter to initialize. (If absent, initialise RandomRealParameter itself.)");
    final public Input<ScalarDistribution<?, Double>> distributionInput
            = new Input<>("distr",
            "Distribution from which to draw a random value. Usually the prior distribution for this parameter.",
            Input.Validate.REQUIRED);

    public RandomRealVectorParam() {
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

        double dummyParamVal = initialInput.get() != null ? initialInput.get().get(0) : this.get(0);

        if (distributionInput.get().paramInput.get() == null) {
            RealScalarParam<Real> dummyParam = new RealScalarParam<>(dummyParamVal, Real.INSTANCE);
            distributionInput.get().paramInput.setValue(dummyParam, distributionInput.get());
            distributionInput.get().initAndValidate();
        }

    }

    @Override
    public void initStateNodes() {
        if (initialInput.get() != null)
            sampleParameter(initialInput.get());
        else
            sampleParameter(this);
    }

    protected void sampleParameter(RealVectorParam<Real> parameter) {
        int dim = parameter.size();
        ScalarDistribution<?, Double> distribution = distributionInput.get();

        for (int i = 0; i < dim; i++){
            parameter.set(i, distribution.sample().getFirst());
        }
    }

    @Override
    public void getInitialisedStateNodes(List<StateNode> stateNodes) {
        stateNodes.add(initialInput.get());
    }


}
