/*
 * Copyright (c) 2023 Tim Vaughan
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

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.Operator;
import beast.base.inference.StateNode;
import beast.base.util.Randomizer;

import java.util.ArrayList;
import java.util.List;

@Description("An operator which uniformly selects values for the input " +
        "state node from the provided discrete set of possibilities.")
public class DiscreteUniformJumpOperator extends Operator {

    public Input<StateNode> xInput = new Input<>("x",
            "State node", Input.Validate.REQUIRED);

    public Input<List<StateNode>> possibilitiesInput = new Input<>(
            "possibility", "Possibility", new ArrayList<>());

    StateNode x;
    List<StateNode> possibilities;

    @Override
    public void initAndValidate() {
        x = xInput.get();
        possibilities = possibilitiesInput.get();
    }

    @Override
    public double proposal() {

        x.startEditing(this);
        StateNode newValue = possibilities.get(Randomizer.nextInt(possibilities.size()));
        x.assignFromFragile(newValue);

        return 0;
    }
}
