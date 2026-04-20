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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Description("A Function representing an evenly-spaced sequence of real numbers.")
public class Sequence<D extends Real> extends LoggableRealVector<D> {

    public Input<Double> startInput = new Input<>("start",
            "Start value of sequence",
            Input.Validate.REQUIRED);

    public Input<Double> stopInput = new Input<>("stop",
            "Stop value of sequence",
            Input.Validate.REQUIRED);

    public Input<Integer> lengthInput = new Input<>("length",
            "Length of sequence",
            Input.Validate.REQUIRED);

    Double[] values;

    @Override
    public D getDomain() {
        return (D) D.INSTANCE;
    }

    @Override
    public void initAndValidate() {
        int length = lengthInput.get();

        if (length<2)
            throw new IllegalArgumentException("Sequence length input " +
                    "must be at least 2.");

        values = new Double[length];

        double start = startInput.get();
        double stop = stopInput.get();
        double delta = (stop-start)/(length - 1);

        for (int i=0; i<length; i++)
            values[i] = start + i*delta;
    }

    @Override
    public List<Double> getElements() {
        return Arrays.asList(values);
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public double get(int dim) {
        return values[dim];
    }
}
