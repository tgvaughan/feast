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
import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import beast.base.spec.type.RealVector;

@Description("A RealParameter initialized from a function.")
public class RealVectorParamFromRealVector extends RealVectorParam<Real> {

    public Input<RealVector<? extends Real>> realVectorInput = new Input<>("realVector",
            "RealVector used to initialize RealParameter.",
            Input.Validate.REQUIRED);

    public RealVectorParamFromRealVector() {
        valuesInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        for (int i=0; i<realVectorInput.get().size(); i++)
            valuesInput.setValue(realVectorInput.get().get(i), this);

        super.initAndValidate();
    }
}
