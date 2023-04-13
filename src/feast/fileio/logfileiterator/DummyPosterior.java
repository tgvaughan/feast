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

package feast.fileio.logfileiterator;

import beast.base.core.BEASTInterface;
import beast.base.core.Input;
import beast.base.inference.Distribution;
import beast.base.inference.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A dummy posterior object.  When set as the posterior for a State object,
 * the State will identify inputs to this posterior as objects that must be
 * reinitialized when any of the state nodes change.
 */
public class DummyPosterior extends Distribution {

    public Input<List<BEASTInterface>> inputs = new Input<>("object",
            "Dummy posterior input.",
            new ArrayList<>());

    @Override
    public double calculateLogP() {
        for (BEASTInterface object : inputs.get()) {
            if (object instanceof Distribution)
                ((Distribution) object).calculateLogP();
        }

        return super.calculateLogP();
    }

    @Override
    public List<String> getArguments() {
        return null;
    }

    @Override
    public List<String> getConditions() {
        return null;
    }

    @Override
    public void sample(State state, Random random) {

    }
}
