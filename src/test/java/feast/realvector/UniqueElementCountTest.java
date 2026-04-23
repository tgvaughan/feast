package feast.realvector;

import beast.base.evolution.alignment.Taxon;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.TraitSet;
import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueElementCountTest {

    @Test
    public void test() {

        RealVectorParam<Real> param = new RealVectorParam<>(new double[] {1, 3, 5, 3, 3, 2}, Real.INSTANCE);

        UniqueElementCount elementCount = new UniqueElementCount();
        elementCount.initByName("arg", param );

        assertEquals(4, elementCount.get());
    }
}
