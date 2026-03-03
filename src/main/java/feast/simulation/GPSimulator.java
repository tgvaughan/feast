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

package feast.simulation;

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.Logger;
import beast.base.inference.Runnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Description("This Runnable allows you to iterate the initialisation of some" +
        " BEASTObject and to log the results using the standard Logger mechanism.")
public class GPSimulator extends Runnable {

    public Input<List<BEASTObject>> beastObjectInput = new Input<>(
            "simulationObject",
            "BEASTObject constituting simulated quantity.",
            new ArrayList<>());

    public Input<Integer> nSimsInput = new Input<>(
            "nSims",
            "Number of values to simulate",
            Input.Validate.REQUIRED);

    public Input<List<Logger>> loggersInput = new Input<>(
            "logger",
            "Logger used to write results to screen or disk.",
            new ArrayList<>());

    @Override
    public void initAndValidate() { }

    Set<BEASTObject> initializedObjects = new HashSet<>();

    @Override
    public void run() throws Exception {

        if (beastObjectInput.get().isEmpty())
            throw new IllegalArgumentException("Need to specify at least one" +
                    " simulation object.");

        // Initialize loggers
        for (Logger logger : loggersInput.get()) {
            logger.init();
        }

        for (int i=0; i<nSimsInput.get(); i++) {
            if (i>0) {
                initializedObjects.clear();
                for (BEASTObject beastObject : beastObjectInput.get())
                    recursiveReinit(beastObject, initializedObjects);
            }

            // Log state
            for (Logger logger : loggersInput.get())
                logger.log(i);
        }

        // Finalize loggers
        for (Logger logger : loggersInput.get())
            logger.close();
    }

    void recursiveReinit(BEASTObject beastObject, Set<BEASTObject> initializedObjects) {
        if (initializedObjects.contains(beastObject))
            return;

        for (Input<?> input : beastObject.getInputs().values()) {
            if (input.get() != null && input.get() instanceof  BEASTObject) {

                recursiveReinit((BEASTObject) input.get(), initializedObjects);
            }
        }

        beastObject.initAndValidate();

        initializedObjects.add(beastObject);
    }


}
