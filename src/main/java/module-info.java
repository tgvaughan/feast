import feast.fileio.RealVectorParamFromLabelledXSV;
import feast.fileio.RealVectorParamFromXSV;
import feast.realvector.*;

open module feast {
    requires beast.pkgmgmt;
    requires beast.base;

    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.base;
    requires org.antlr.antlr4.runtime;
    requires org.apache.commons.math4.legacy;

    exports feast.expressions;
    exports feast.fileio;
    exports feast.realvector;
    exports feast.mapping;
    exports feast.modelselect;
    exports feast.nexus;
    exports feast.operators;
    exports feast.parameter;
    exports feast.popmodels;
    exports feast.simulation;

    provides beast.base.core.BEASTInterface with
        feast.mapping.DensityMapper,
        feast.operators.BlockIntUniformOperator,
        feast.operators.BlockIntRandomWalkOperator,
        feast.operators.SmartScaleOperator,
        feast.operators.BlockScaleOperator,
        feast.operators.SmartRealRandomWalkOperator,
        feast.operators.DiscreteUniformJumpOperator,
        feast.fileio.TreeFromNewickFile,
        feast.fileio.TraitSetFromXSV,
            RealVectorParamFromLabelledXSV,
        feast.fileio.logfileiterator.TraceLogFileState,
        feast.fileio.logfileiterator.DummyPosterior,
        feast.fileio.logfileiterator.TreeLogFileState,
        feast.fileio.logfileiterator.LogFileIterator,
        feast.fileio.logfileiterator.LogFileRealParameter,
        feast.fileio.TreeFromNexusFile,
            RealVectorParamFromXSV,
        feast.fileio.AlignmentFromNexus,
        feast.fileio.TraitSetFromTaxonSet,
        feast.fileio.TaxonSetFromTree,
        feast.fileio.TipDatesFromTree,
        feast.fileio.AlignmentFromFasta,
        feast.fileio.AlignmentFromFile,
        feast.simulation.GPSimulator,
        feast.simulation.SimulatedAlignment,
        feast.simulation.ShuffledAlignment,
            Sequence,
            TreeTipAges,
            Reverse,
            Concatenate,
            TraitSetAsRealVector,
            MetadataAsRealVector,
            Interleave,
            Slice,
            UniqueElementCount,
            Scale,
        feast.modelselect.DirichletProcessOperator,
        feast.modelselect.ModelSelectionParameter,
        feast.modelselect.DirichletProcessPrior,
        feast.expressions.ExpCalculator,
        feast.expressions.ExpCalculatorDistribution,
        feast.parameter.RealVectorParamFromRealVector,
        feast.parameter.IntVectorParamFromIntVector,
        feast.parameter.TimeParameter,
        feast.parameter.RandomRealVectorParam,
        feast.popmodels.ShiftedPopulationModel,
        feast.popmodels.CompoundPopulationModel,
        feast.popmodels.ExpressionPopulationModel;
}
