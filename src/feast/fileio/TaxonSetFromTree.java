package feast.fileio;

import beast.base.core.Input;
import beast.base.evolution.alignment.Taxon;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.Tree;

public class TaxonSetFromTree extends TaxonSet {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree from which to take taxon set.",
            Input.Validate.REQUIRED);

    public TaxonSetFromTree() {
        alignmentInput.setRule(Input.Validate.FORBIDDEN);
    }

    @Override
    public void initAndValidate() {
        TaxonSet taxonSet = treeInput.get().getTaxonset();
        if (taxonSet == null)
            throw new IllegalArgumentException("Tree provided to " +
                    "TaxonSetFromTree has no taxon set defined.");

        for (Taxon taxon : taxonSet.getTaxonSet())
            taxonsetInput.get().add(taxon);

        super.initAndValidate();
    }
}
