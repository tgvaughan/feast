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

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;

@Description("Represents a RealParameter to be read in from a log file as part of " +
        "a LogFileIterator run.")
public class LogFileRealParameter extends BEASTObject {

    public Input<String> fieldNameInput = new Input<>("fieldName", "Name of field in log file.",
            Input.Validate.REQUIRED);

    public Input<RealParameter> fieldParameterInput = new Input<>("fieldParameter",
            "Parameter with which to associate log file entry values.",
            Input.Validate.REQUIRED);

    public Input<Integer> fieldParameterIndexInput = new Input<>("fieldParameterIndex",
            "Index of element in parameter to initialize with field value.",
            0);

    String fieldName;
    RealParameter fieldParameter;
    int paramIndex;

    @Override
    public void initAndValidate() {
        fieldName = fieldNameInput.get();
        fieldParameter = fieldParameterInput.get();
        paramIndex = fieldParameterIndexInput.get();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldParameterValue(String newValue) {
        fieldParameter.setValue(paramIndex, Double.valueOf(newValue));
    }
}
