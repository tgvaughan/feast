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

package feast.parameter;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;

@Description("A RealParameter initialized from a function.")
public class RealParameterFromFunction extends RealParameter {

    public Input<Function> functionInput = new Input<>("function",
            "Function used to initialize RealParameter.",
            Input.Validate.REQUIRED);

    public RealParameterFromFunction() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        for (double v : functionInput.get().getDoubleValues())
            valuesInput.setValue(v, this);

        super.initAndValidate();
    }
}
