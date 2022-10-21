package feast.function;

import beast.base.inference.parameter.RealParameter;
import org.junit.Assert;
import org.junit.Test;

public class InterleaveTest {

    @Test
    public void testInterleave() {
        RealParameter arg1 = new RealParameter("1 2 3 4");
        RealParameter arg2 = new RealParameter("5 6 7");
        RealParameter arg3 = new RealParameter("8 9");
        RealParameter arg4 = new RealParameter("0");

        Interleave interleave = new Interleave();
        interleave.initByName(
                "arg", arg1,
                "arg", arg2,
                "arg", arg3,
                "arg", arg4);

        for (int i=0; i<interleave.getDimension(); i++)
            System.out.print(interleave.getArrayValue(i) + " ");

        Assert.assertEquals(16, interleave.getDimension());

        Assert.assertEquals(arg1.getArrayValue(0), interleave.getArrayValue(0), 1e-10);
        Assert.assertEquals(arg2.getArrayValue(0), interleave.getArrayValue(1), 1e-10);
        Assert.assertEquals(arg3.getArrayValue(0), interleave.getArrayValue(2), 1e-10);
        Assert.assertEquals(arg4.getArrayValue(0), interleave.getArrayValue(3), 1e-10);

        Assert.assertEquals(arg1.getArrayValue(2), interleave.getArrayValue(8), 1e-10);
        Assert.assertEquals(arg2.getArrayValue(2), interleave.getArrayValue(9), 1e-10);
        Assert.assertEquals(arg3.getArrayValue(0), interleave.getArrayValue(10), 1e-10);
        Assert.assertEquals(arg4.getArrayValue(0), interleave.getArrayValue(11), 1e-10);

        Assert.assertEquals(arg1.getArrayValue(3), interleave.getArrayValue(12), 1e-10);
        Assert.assertEquals(arg2.getArrayValue(0), interleave.getArrayValue(13), 1e-10);
        Assert.assertEquals(arg3.getArrayValue(1), interleave.getArrayValue(14), 1e-10);
        Assert.assertEquals(arg4.getArrayValue(0), interleave.getArrayValue(15), 1e-10);
    }
}
