package feast.fileio;

import beast.core.Input;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.Sequence;

/**
 * Alignments initialized from files extend this class.
 */
public class AlignmentFromFile extends Alignment {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of file "
            + "containing sequence alignment in fasta format.", Input.Validate.REQUIRED);

    public Input<String> outFileNameInput = new Input<>("xmlFileName",
            "Name of file to write XML fragment to.");

    public Input<String> endsWithInput = new Input<>(
            "endsWith", "If provided, include only those sequences whose header " +
            "strings end with the provided substring.");

    /**
     * Add sequence to the alignment, provided the predicates specified
     * in the inputs are satisfied.
     *
     * @param sequence sequence to add
     */
    protected void addSequence(Sequence sequence) {
        if (endsWithInput.get() == null || sequence.getTaxon().endsWith(endsWithInput.get()))
            sequenceInput.setValue(sequence, this);
    }
}
