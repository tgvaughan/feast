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

import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.inference.Operator;
import beast.base.inference.StateNode;
import beast.base.inference.parameter.RealParameter;

import java.util.*;

public abstract class SmartRealOperator extends Operator {

    public Input<List<RealParameter>> parametersInput = new Input<>("parameter",
            "One or more parameters to operate on", new ArrayList<>());

    public Input<Function> classesToExcludeInput = new Input<>("classesToExclude",
            "Elements with these value will not be operated on.");

    protected List<RealParameter> parameters;
    protected Map<RealParameter, Integer[]> groups;

    protected int nClasses;

    @Override
    public void initAndValidate() {

        parameters = parametersInput.get();

        SortedSet<Double> seenValuesSet = new TreeSet<>();

        for (RealParameter param : parameters) {
            for (int i=0; i<param.getDimension(); i++) {
                if (param.getValue(i) != 0.0)
                    seenValuesSet.add(param.getValue(i));
            }
        }

        // Explicitly exclude certain classes (identified by the element value)
        if (classesToExcludeInput.get() != null) {
            for (double value : classesToExcludeInput.get().getDoubleValues())
                seenValuesSet.remove(value);
        }

        List<Double> seenValues = new ArrayList<>(seenValuesSet);
        nClasses = seenValues.size();

        groups = new HashMap<>();

        for (RealParameter param : parameters) {
            Integer[] groupIDs = new Integer[param.getDimension()];

            for (int i = 0; i < param.getDimension(); i++)
                groupIDs[i] = seenValues.indexOf(param.getValue(i));

            groups.put(param, groupIDs);
        }
    }

    @Override
    public List<StateNode> listStateNodes() {
        return new ArrayList<>(parametersInput.get());
    }
}
