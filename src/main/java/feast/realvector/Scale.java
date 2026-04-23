/*
 * Copyright (c) 2023 ETH Zurich
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

package feast.realvector;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealScalar;
import beast.base.spec.type.RealVector;

import java.util.ArrayList;
import java.util.List;

@Description("A RealVector whose elements are the elements of an input RealVector " +
        "scaled by another input RealVector.")
public class Scale extends CalculatedRealVector<Real> {

    public Input<RealVector<? extends Real>> vectorInput = new Input<>("arg",
            "Function to scale", Input.Validate.REQUIRED);

    public Input<List<RealScalar<? extends Real>>> scalingFactorsInput = new Input<>("scaleBy",
            "Amount to scale by", new ArrayList<>());

    RealVector<? extends Real> realVector;
    List<RealScalar<? extends Real>> scalingFactors;

    Real domain;

    @Override
    public void initAndValidate() {
        realVector = vectorInput.get();
        scalingFactors = scalingFactorsInput.get();

    }

    @Override
    public Real getDomain() {
        return Real.INSTANCE;
        /*
        Could do something fancier here (e.g. when both realVector and scalingFactors
        are UnitIntervals, result would be UnitInterval), but capturing all cases
        is very tedious.  Alternatively, Domain classes themselves could
        implement this algebra to define Domain1*Domain2 or Domain1+Domain2
        for all pairs.
         */
    }

    @Override
    public int size() {
        return realVector.size();
    }

    @Override
    public double get(int i) {
        double res=realVector.get(i);
        for (RealScalar<? extends Real> scalingFactor : scalingFactors)
            res *= scalingFactor.get();

        return res;
    }
}
