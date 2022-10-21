package feast.function;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.evolution.tree.Tree;

@Description("Function representing ages of sample nodes of tree.")
public class SampleAges extends LoggableFunction {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree to extract leaf ages from.",
            Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() {
    }

    @Override
    public int getDimension() {
        return treeInput.get().getLeafNodeCount();
    }

    @Override
    public double getArrayValue(int dim) {
        return treeInput.get().getArrayValue(dim);
    }
}
