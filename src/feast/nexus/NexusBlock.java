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

package feast.nexus;

import java.util.List;

/**
 * @author Tim Vaughan
 */
public abstract class NexusBlock {
    
    /**
     * @return name of block
     */
    public abstract String getBlockName();
    
    /**
     * @return list of strings containing lines in block
     */
    public abstract List<String> getBlockLines();
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("begin ").append(getBlockName()).append(";\n");
        
        for (String line : getBlockLines())
            sb.append("\t").append(line).append(";\n");
        
        sb.append("end;\n");
        
        return sb.toString();
    }
}
