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

package feast.operators;

import beast.base.core.Input;
import beast.base.inference.Operator;
import beast.base.inference.StateNode;
import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import beast.base.spec.type.RealVector;

import java.util.*;

public abstract class SmartRealOperator extends Operator {

    public Input<List<RealVectorParam<? extends Real>>> parametersInput = new Input<>("parameter",
            "One or more parameters to operate on", new ArrayList<>());

    public Input<RealVector<? extends Real>> classesToExcludeInput = new Input<>("classesToExclude",
            "Elements with these value will not be operated on.");

    protected List<RealVectorParam<? extends Real>> parameters;
    protected Map<RealVectorParam<? extends Real>, Integer[]> groups;

    protected int nClasses;

    @Override
    public void initAndValidate() {

        parameters = parametersInput.get();

        SortedSet<Double> seenValuesSet = new TreeSet<>();

        for (RealVectorParam<? extends Real> param : parameters) {
            for (int i=0; i<param.size(); i++) {
                if (param.get(i) != 0.0)
                    seenValuesSet.add(param.get(i));
            }
        }

        // Explicitly exclude certain classes (identified by the element value)
        if (classesToExcludeInput.get() != null) {
            for (double value : classesToExcludeInput.get().getElements())
                seenValuesSet.remove(value);
        }

        List<Double> seenValues = new ArrayList<>(seenValuesSet);
        nClasses = seenValues.size();

        groups = new HashMap<>();

        for (RealVectorParam<? extends Real> param : parameters) {
            Integer[] groupIDs = new Integer[param.size()];

            for (int i = 0; i < param.size(); i++)
                groupIDs[i] = seenValues.indexOf(param.get(i));

            groups.put(param, groupIDs);
        }
    }

    @Override
    public List<StateNode> listStateNodes() {
        return new ArrayList<>(parametersInput.get());
    }
}
