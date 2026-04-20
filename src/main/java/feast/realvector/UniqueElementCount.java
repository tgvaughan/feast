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
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.core.Loggable;
import beast.base.inference.CalculationNode;
import beast.base.spec.domain.Int;
import beast.base.spec.type.IntScalar;
import beast.base.spec.type.RealVector;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

@Description("Function representing the number of unique elements of another function.")
public class UniqueElementCount<D extends Int> extends CalculationNode implements Loggable, IntScalar<D> {

    public Input<RealVector> argInput = new Input<>("arg",
            "Number of unique elements of this parameter will be logged.",
            Input.Validate.REQUIRED);

    Set<Double> valueSet = new HashSet<>();

    @Override
    public void initAndValidate() {
    }

    @Override
    public D getDomain() {
        return (D) D.INSTANCE;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public int get() {
        valueSet.clear();
        for (int i = 0; i< argInput.get().size(); i++) {
            valueSet.add(argInput.get().get(i));
        }

        return valueSet.size();
    }

    @Override
    public void init(PrintStream out) {
        out.print(getID() + "\t");
    }

    @Override
    public void log(long nSample, PrintStream out) {
        out.print(get() + "\t");
    }

    @Override
    public void close(PrintStream out) {
    }
}
