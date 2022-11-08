package feast.operators;

import beast.base.core.Input;
import beast.base.inference.Operator;
import beast.base.inference.StateNode;
import beast.base.util.Randomizer;

import java.util.ArrayList;
import java.util.List;


public class DiscreteUniformJumpOperator extends Operator {

    public Input<StateNode> xInput = new Input<>("x",
            "State node", Input.Validate.REQUIRED);

    public Input<List<StateNode>> possibilitiesInput = new Input<>(
            "possibility", "Possibility", new ArrayList<>());

    StateNode x;
    List<StateNode> possibilities;

    @Override
    public void initAndValidate() {
        x = xInput.get();
        possibilities = possibilitiesInput.get();
    }

    @Override
    public double proposal() {

        x.startEditing(this);
        StateNode newValue = possibilities.get(Randomizer.nextInt(possibilities.size()));
        x.assignFromFragile(newValue);

        return 0;
    }
}
