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
import beast.base.spec.domain.PositiveReal;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealScalar;
import beast.base.spec.type.RealVector;

import java.util.ArrayList;
import java.util.List;

@Description("A RealVector whose elements are the elements of an input RealVector " +
        "scaled by another input RealVector.")
public class Scale<D extends Real> extends CalculatedRealVector<D> {

    public Input<RealVector> vectorInput = new Input<>("function",
            "Function to scale", Input.Validate.REQUIRED);

    public Input<List<RealScalar<PositiveReal>>> scalingFactorsInput = new Input<>("scaleBy",
            "Amount to scale by", new ArrayList<>());

    RealVector realVector;
    List<RealScalar<PositiveReal>> scalingFactors;

    @Override
    public void initAndValidate() {
        realVector = vectorInput.get();
        scalingFactors = scalingFactorsInput.get();
    }

    @Override
    public D getDomain() {
        return (D) realVector.getDomain();
    }

    @Override
    public int size() {
        return realVector.size();
    }

    @Override
    public double get(int i) {
        double res=realVector.get(i);
        for (RealScalar scalingFactor : scalingFactors)
            res *= scalingFactor.get();

        return res;
    }
}
