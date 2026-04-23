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
import beast.base.spec.domain.Domain;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealVector;

import java.util.ArrayList;
import java.util.List;

@Description("A RealVector whose elements are the result of concatenating the elements of the input RealVector.")
public class Concatenate extends LoggableRealVector<Real> {

    public Input<List<RealVector<? extends Real>>> realVectorsInput = new Input<>("arg",
            "One or more functions to concatenate.",
            new ArrayList<>());

    List<RealVector<? extends Real>> realVectors;
    int totalDim;

    Real domain;

    @Override
    public void initAndValidate() {
        realVectors = realVectorsInput.get();

        if (realVectors.isEmpty())
            throw new IllegalArgumentException("Concatenate expects at least one input function.");

        domain = realVectors.getFirst().getDomain();

        totalDim = 0;
        for (RealVector<? extends Real> vec : realVectorsInput.get()) {
            if (vec.getDomain() != domain)
                throw new IllegalArgumentException("All inputs to concatenate must have the same domain.");
            totalDim += vec.size();
        }

    }

    @Override
    public List<Double> getElements() {
        List<Double> elements = new ArrayList<>();
        for (int i=0; i<size(); i++)
            elements.add(get(i));
        return elements;
    }

    @Override
    public Real getDomain() {
        return domain;
    }

    @Override
    public int size() {
        return totalDim;
    }

    @Override
    public double get(int dim) {
        int paramIdx=0;
        while (dim >= realVectors.get(paramIdx).size()) {
            dim -= realVectors.get(paramIdx).size();
            paramIdx += 1;
        }

        return realVectors.get(paramIdx).get(dim);
    }
}
