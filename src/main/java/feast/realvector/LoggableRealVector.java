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

import beast.base.core.Loggable;
import beast.base.inference.CalculationNode;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealVector;

import java.io.PrintStream;

public abstract class LoggableRealVector<D extends Real> extends CalculationNode implements Loggable, RealVector<D> {

    @Override
    public void init(PrintStream out) {
        if (size()==1) {
            out.print(getID() + "\t");
        } else {
            for (int i = 0; i < size(); i++)
                out.print(getID() + "[" + i + "]\t");
        }
    }

    @Override
    public void log(long nSample, PrintStream out) {
        for (int i=0; i<size(); i++)
            out.print(get(i) + "\t");
    }

    @Override
    public void close(PrintStream out) {

    }
}
