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
import beast.base.spec.domain.Domain;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealVector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Vaughan
 */
@Description("A RealVector produced by interleaving the elements of two " +
        "or more input RealVectors.")
public class Interleave<D extends Real> extends CalculatedRealVector<D> {

    public Input<List<RealVector>> argsInput = new Input<>("arg",
            "RealVectors to interleave.",
            new ArrayList<>());

    int maxLen, dim;
    List<RealVector> args;
    Domain domain;

    @Override
    public void initAndValidate() {
        args = argsInput.get();

        if (args.isEmpty())
            throw new IllegalArgumentException("Interleave requires at least one RealVector to interleave");

        domain = args.getFirst().getDomain();

        maxLen = 0;
        for (RealVector arg : args) {
            if (arg.getDomain() != domain)
                throw new IllegalArgumentException("Arguments to Interleave must have the same domain");

            maxLen = Math.max(arg.size(), maxLen);
        }

        dim = maxLen*args.size();
    }

    @Override
    public D getDomain() {
        return (D) domain;
    }

    @Override
    public int size() {
        return dim;
    }

    @Override
    public double get(int i) {
        if (i >= dim)
            throw new IllegalArgumentException("Index exceeds length of interleaved function.");

        int col = i / args.size();
        int row = i % args.size();

        RealVector arg = args.get(row);
        return arg.get(col % arg.size());
    }
}
