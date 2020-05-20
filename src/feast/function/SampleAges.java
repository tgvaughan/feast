package feast.function;

import beast.core.Description;
import beast.core.Input;
import beast.evolution.tree.Tree;

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
