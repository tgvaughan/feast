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

package feast.function;

import beast.base.core.Function;
import beast.base.core.Loggable;
import beast.base.inference.CalculationNode;

import java.io.PrintStream;

public abstract class LoggableFunction extends CalculationNode implements Loggable, Function {

    @Override
    public void init(PrintStream out) {
        if (getDimension()==1) {
            out.print(getID() + "\t");
        } else {
            for (int i = 0; i < getDimension(); i++)
                out.print(getID() + "[" + i + "]\t");
        }
    }

    @Override
    public void log(long nSample, PrintStream out) {
        for (int i=0; i<getDimension(); i++)
            out.print(getArrayValue(i) + "\t");
    }

    @Override
    public void close(PrintStream out) {

    }
}
