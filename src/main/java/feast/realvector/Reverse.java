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
import beast.base.core.Input;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealVector;

@Description("A Function whose elements are the elements of the input Function but in reverse order.")
public class Reverse extends CalculatedRealVector<Real> {

    public Input<RealVector<? extends Real>> realVectorInput = new Input<>("arg",
            "Argument to reverse elements of.", Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() { }

    @Override
    public Real getDomain() {
        return realVectorInput.get().getDomain();
    }

    @Override
    public int size() {
        return realVectorInput.get().size();
    }

    @Override
    public double get(int dim) {
        return realVectorInput.get().get(size()-1-dim);
    }

}
