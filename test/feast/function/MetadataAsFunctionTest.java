/*
 * Copyright (c) 2025 ETH Zürich
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

package feast.function;

import beast.base.evolution.tree.Tree;
import beast.base.evolution.tree.TreeParser;
import org.junit.Assert;
import org.junit.Test;

public class MetadataAsFunctionTest {

    @Test
    public void test() {
        Tree tree = new TreeParser("(0[&md=10]:1,(1[&md=11,anotherkey=\"hello\"]:1,2[&md=12]:2)3:1)4:0;");

        MetadataAsFunction mdfunc = new MetadataAsFunction();
        mdfunc.initByName("tree", tree, "key", "md");

        Assert.assertEquals((Double)tree.getNode(0).getMetaData("md"),
                mdfunc.getArrayValue(0), 1e-10);
        Assert.assertEquals((Double)tree.getNode(1).getMetaData("md"),
                mdfunc.getArrayValue(1), 1e-10);
        Assert.assertEquals((Double)tree.getNode(2).getMetaData("md"),
                mdfunc.getArrayValue(2), 1e-10);

        Assert.assertTrue(Double.isNaN(mdfunc.getArrayValue(3)));
        Assert.assertTrue(Double.isNaN(mdfunc.getArrayValue(4)));
    }

}
