package feast.function;

import beast.core.Description;
import beast.core.Input;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.TraitSet;

@Description("All trait sets have at least some (potentially useless) representation " +
        "as a vector of doubles.  This class just allows you to explicitly treat them " +
        "as Functions.")
public class TraitSetAsFunction extends LoggableFunction {

    public Input<TraitSet> traitSetInput = new Input<>("traitSet",
            "Trait set to represent as a function.",
            Input.Validate.REQUIRED);

    TaxonSet taxonSet;
    TraitSet traitSet;

    @Override
    public void initAndValidate() {
        traitSet = traitSetInput.get();
        taxonSet = traitSet.taxaInput.get();
    }

    @Override
    public int getDimension() {
        return taxonSet.getTaxonCount();
    }

    @Override
    public double getArrayValue(int dim) {
        return traitSet.getValue(taxonSet.asStringList().get(dim));
    }
}
