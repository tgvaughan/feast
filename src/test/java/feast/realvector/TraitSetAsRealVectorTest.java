package feast.realvector;

import beast.base.evolution.alignment.Taxon;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.TraitSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TraitSetAsRealVectorTest {

    @Test
    public void test() {

        List<Taxon> taxonList = new ArrayList<>();
        taxonList.add(new Taxon("A"));
        taxonList.add(new Taxon("B"));
        taxonList.add(new Taxon("C"));
        TaxonSet taxonSet = new TaxonSet(taxonList);

        TraitSet traitSet = new TraitSet();
        traitSet.initByName("traitname", "age",
                "taxa", taxonSet,
                "value","A=0,B=1,C=2");

        TraitSetAsRealVector traitSetAsRealVector = new TraitSetAsRealVector();
        traitSetAsRealVector.initByName("traitSet", traitSet );

        assertEquals(3, traitSetAsRealVector.size());
        assertEquals(0.0, traitSetAsRealVector.get(0));
        assertEquals(1.0, traitSetAsRealVector.get(1));
        assertEquals(2.0, traitSetAsRealVector.get(2));
    }

}
