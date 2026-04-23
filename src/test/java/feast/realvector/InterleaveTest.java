package feast.realvector;

import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterleaveTest {

    @Test
    public void testInterleave() {
        RealVectorParam<Real> arg1 = new RealVectorParam<>(new double[] {1,2,3,4}, Real.INSTANCE);
        RealVectorParam<Real> arg2 = new RealVectorParam<>(new double[] {5,6,7}, Real.INSTANCE);
        RealVectorParam<Real> arg3 = new RealVectorParam<>(new double[] {8,9}, Real.INSTANCE);
        RealVectorParam<Real> arg4 = new RealVectorParam<>(new double[] {0}, Real.INSTANCE);

        Interleave interleave = new Interleave();
        interleave.initByName(
                "arg", arg1,
                "arg", arg2,
                "arg", arg3,
                "arg", arg4);

        for (int i=0; i<interleave.size(); i++)
            System.out.print(interleave.get(i) + " ");

        assertEquals(16, interleave.size());

        assertEquals(arg1.get(0), interleave.get(0), 1e-10);
        assertEquals(arg2.get(0), interleave.get(1), 1e-10);
        assertEquals(arg3.get(0), interleave.get(2), 1e-10);
        assertEquals(arg4.get(0), interleave.get(3), 1e-10);

        assertEquals(arg1.get(2), interleave.get(8), 1e-10);
        assertEquals(arg2.get(2), interleave.get(9), 1e-10);
        assertEquals(arg3.get(0), interleave.get(10), 1e-10);
        assertEquals(arg4.get(0), interleave.get(11), 1e-10);

        assertEquals(arg1.get(3), interleave.get(12), 1e-10);
        assertEquals(arg2.get(0), interleave.get(13), 1e-10);
        assertEquals(arg3.get(1), interleave.get(14), 1e-10);
        assertEquals(arg4.get(0), interleave.get(15), 1e-10);
    }
}
