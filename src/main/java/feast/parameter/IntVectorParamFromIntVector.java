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

package feast.parameter;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.spec.domain.Int;
import beast.base.spec.inference.parameter.IntVectorParam;
import beast.base.spec.type.IntVector;

@Description("A IntegerParameter initialized from a function.  (The elements of" +
        " the input function are rounded to the nearest integer.")
public class IntVectorParamFromIntVector extends IntVectorParam<Int> {

    public Input<IntVector<? extends Int>> intVectorInput = new Input<>("intVector",
            "IntVector used to initialize IntVectorParam.",
            Input.Validate.REQUIRED);

    public IntVectorParamFromIntVector() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        valuesInput.setValue(intVectorInput.get().getElements(), this);
        super.initAndValidate();
    }
}
