feast:  Tasty additions to BEAST 2
==================================

This is a small [BEAST 2](http://www.beast2.org) package which
contains additions to the core functionality.  The common thread
which connects these additions is that they all work to increase
the flexibility of BEAST and to empower users to set up a broader
range of analyses without needing to write additional Java code.

You won't find new models here, but you will find different
ways to combine existing models and make certain tasks easier.

[![Build Status](https://github.com/tgvaughan/feast/workflows/Unit%2Fintegration%20tests/badge.svg)](https://github.com/tgvaughan/feast/actions?query=workflow%3A%22Unit%2Fintegration+tests%22)


Installation
------------

You can install directly from within BEAUti by opening the package
manager via File->"Manage packages", selecting "feast" and clicking
the "Install/Upgrade" button.

Building from source
--------------------

The default target in the provided [Apache ANT](http://ant.apache.org)
build script can be used to build the BEAST 2 package from scratch.
The package archive will be left in the `dist/` directory.

Features
========

The rest of this README is an asymptotically complete list of the features feast
provides, in reverse chronological order of the date each was added.

A quick note of apology: I know this is becoming difficult to read.  This
organization vaguely made sense at the inception of feast, when there were very
few unrelated features, but makes less sense now that it contains increasingly
many that fall into distinct groups. This is a known problem, and I plan to
"one day" reorganise things.

Initialize RealParameters from CSV/TSV files
--------------------------------------------

When setting up complicated analyses, it can be useful to extract some
(fixed) parameter values from an external CSV or TSV (or whatever-SV) file.
The `RealParameterFromXSV` and `RealParameterFromLabelledXSV` classes can be
used to do this.

`RealParameterFromXSV` simply initializes the parameter with values read
(in row-major order) from a rectangular portion of the table in the specified
file. For instance,
```xml
<migrationRates spec="feast.fileio.RealParameterFromXSV"
                fileName="migration_rates.csv"
                sep=","
                startRow="1" rowCount="3"
                startCol="1" colCount="3"/>
```
could be used to initialize a 3x3 matrix of migration rates in some analysis
using elements in a CSV file.  The inputs `startRow` and `startCol` both
default to "0", while leaving either `rowCount` and `colCount` undefined
will cause values to be taken up until the end of the corresponding row
or column.

`RealParameterFromLabelledXSV` works simillarly, but assumes that the either
or both of the first row or column contain labels, and allows these labels
to be used to specify elements. For example,
```xml
<samplingProportions spec="feast.fileio.RealParameterFromLabelledXSV"
    fileName="sampling.tsv"
    colLabels="sampling"
    rowLabels="early_cretaceous, late_cretaceous, jurassic"/>
```
could be used to set sampling rates for geological epochs from some
external data file.  If `rowLabels` is omitted, the entire column
will be read.  Similarly, if `colLabels` is omitted, the entire row
is read.

Initialize RealParameters from Functions
----------------------------------------

The `RealParameterFromFunction` class provides a `RealParameter` which is
**initialized** from a `Function`.  Thus you can do things like

```xml
<samplingRateChangeTimes spec="RealParameterFromFunction">
    <function spec="ExpCalculator" value="{0,max(taxonTimes)+0.1}">
        <arg id="taxonTimes" spec="TraitSetAsFunction" traitSet="@dateTrait"/>
    </function>
</samplingRateChangeTimes>
```

which initializes a BDSKY sampling rate change time array so that the change
automatically falls just before the oldest sample.

Beware that `RealParameterFromFunction` only retrieves the `Function` values
during initialization. **Changes in the `Function` values after initialization
will not be reflected in the parameter!**

Compound coalescent population functions
----------------------------------------

BEAST 2 includes several commonly-used coalescent population models such
as a constant population function and an exponential growth function.
To add other population functions required writing a new Java class.
The `CompoundPopulationModel` class lifts this restriction.

This class allows you to combine existing population functions (i.e.
objects which implement the `PopulationFunction` interface) in a piecewise
manner to construct arbitrarily complicated compound models.

For example, here is a piecewise-constant population function created
by joining together three `ConstantPopulation` models:
```xml
<populationModel spec="CompoundPopulationModel">
    <popModel spec="ConstantPopulation"> <popSize spec="RealParameter" value="5.0"/></popModel>
    <popModel spec="ConstantPopulation"> <popSize spec="RealParameter" value="10.0"/></popModel>
    <popModel spec="ConstantPopulation"> <popSize spec="RealParameter" value="2.0"/></popModel>
    <changeTimes spec="RealParameter" value="1.0 3.0"/>
</populationModel>
```
The `changeTimes` input specifies the times of the transitions between
models, and thus must have one less element than the number of constituent models.

The file `piecewiseCoalescent.xml` illustrates how this can be used in a
simple coalescent analysis.

Log File Post-processing
------------------------

Occasionally it's useful to be able to resurrect BEAST states from trace
and tree log files in order to perform further processing.  This is useful,
for instance, if you realise that it would have been useful to compute
additional tree statistics.  Or perhaps you'd like to stochastically map
ancestral traits onto trees belonging to the posterior of a multi-type
birth-death analysis.  The `feast.fileio.LogFileIterator` class and its
friends allow you to do these things and more.

The `LogFileIterator` class is a `Runnable` class, meaning that you use it
in place of BEAST's usual `MCMC` class at the top level.  A trivial example
is as follows:

```xml
<beast version='2.0' namespace='feast.fileio.logfileiterator:beast.evolution.tree:beast.core.parameter'>

  <run spec="LogFileIterator">
    <logFileState spec="TraceLogFileState" logFileName="original_analysis.log">
      <logFileEntry spec="LogFileRealParameter" fieldName="hky.kappa">
        <fieldParameter id="kappa" spec="RealParameter" value="0.0"/>
      </logFileEntry>
    </logFileState>

    <logFileState spec="TreeLogFileState" logFileName="original_analysis.trees">
      <tree spec="beast.evolution.tree.Tree" id="tree"/>
    </logFileState>

    <logger logEvery="10000" fileName="processed.log">
      <log idref="recoveredParameter"/>
      <log id="treestat" spec="beast.evolution.tree.TreeStatLogger" tree="@tree"/>
    </logger>

    <logger logEvery="100000">
      <log idref="kappa"/>
      <log idref="treestat"/>
    </logger>
  </run>
</beast>
```

In this example (which is also provided in the examples directory as `TreeLogFileIteratorTest.xml`)
we use the "hky.kappa" field of the `original_analysis.log` trace log file to set the value of a
`RealParameter` named "kappa".  Simultaneously, we use the trees in the tree log file `original_analysis.trees`
to set the value of the `Tree` named "tree".  The value of "kappa" and the height and length statistics
of "tree" are then written to the file "processed.log" and the screen in the usual way using loggers.

Model selection using ModelSelectionParameter
----------------------------------------------------

While model selection is extremely complicated in general, it can be almost
trivial when the number of models you wish to consider is small and the models
can be regarded as special cases of either each other
(i.e. the are nested) or can easily expressed as special cases of a single
general model.

The `ModelSelectionParameter` is created with this kind of BSSVS-style model
selection in mind. It is a `CalculationNode` implementing `Function` which takes
one or more parameters (also `Function`s) as input, together with an
`IntegerParameter` known as the selection index. The `ModelSelectionParameter`
acts as though it is the parameter specified by the selection index.

The simplest application of this is to allow BEAST to select between models
1 and 2, where in model 1 a parameter (eg the extinction rate) is zero, while
in model 2 the extinction rate has some unknown non-zero value.

We can implement this by defining the death rate to be a `ModelSelectionParameter`:
```xml
<deathRate id="deathRate" spec="ModelSelectionParameter">
    <parameter spec="RealParameter" value="0.0"/>
    <parameter id="nonZeroDeathRate" spec="RealParameter" value="1.0"/>
    <selectionIndices id="selectionIdx" spec="IntegerParameter" value="1" lower="0" upper="1"/>
</deathRate>
```
We then use this "deathRate" parameter as input to the tree prior, place some
sensible prior on "nonZeroDeathRate", and add an operator to modify the
"selectionIdx" `IntegerParameter`.  The result is equivalent to placing a
"spike and slab" prior on the death rate parameter, which is a mixture between
a point mass at zero and whatever prior you place on "nonZeroDeathRate".

In addition to this simple case, if "selectionIndices" is an `IntegerParameter`
with more than one element, the additional `ModelSelectionParameter` input,
"thisIndex", can be used to specify which element to use to select the parameter.
This allows you to sample across models where, for example, subsets of different
parameters are allowed to be exactly equal.  In this case, each model parameter
is its own `ModelSelectionParameter`, shares exactly the same set of 
"parameter" and "selectionIndices" inputs, but has its own unique value of
"thisIndex".

The principal shortcoming of ModelSelectionParameter is that it is only
compatible with distributions which take `Function`s rather than `RealParameter`
s as input.

Scaling subsets of RealParameter elements together
--------------------------------------------------

It's occasionally necessary to tie multiple elements of a RealParameter
together, for instance to represent a vector of rates where a subset of
these rates are conditioned to be identical.  The `BlockScaleOperator`
operator makes this possible.  It behaves essentially identically to the
standard BEAST 2 `ScaleOperator`, with inputs "parameter" and "indicator"
specifying respectively the `RealParameter` to scale and the `BooleanParameter`
indicating which elements to scale.  The differences are that `BlockScaleOperator`

  1. _always_ scales the selected elements together (i.e. using the same
      scale factor), and
  2. it correctly takes into account the number of degrees of freedom
     (i.e. unique element values) among the selected elements.
     
An example usage of this operator is as follows:
```xml
<operator spec="BlockScaleOperator" parameter="@paramToScale" weight="1.0">
    <indicator spec="BooleanParameter" value="true true false false" estimate="false"/>
</operator>
```
Here "@paramToScale" references a `RealParameter` with 4 elements, and the
operator jointly scales the first two.

Initializing RealParameters using formatted times
-------------------------------------------------

Particularly in birth-death models, it is often desirable to specify
several points in time as ages relative to the "present".  This is done
when setting up change times for birth-death parameters, and requires
one to do a fairly large number of error-prone calculations involving
converting date strings like "05/03/1980" and "27/02/1980" to fractional
years before some later date such as "01/01/1990".  Once converted, these
values become extremely important yet difficult to interpret entries in
the final BEAST XML file.

The `TimeParameter` class is intended to combat this.  It can be used
anywhere that a `RealParameter` is expected.  For example, to create
a `RealParameter` with the ages corresponding to the times above, use

```xml
<parameter spec="TimeParameter"
           time="05/03/1980 27/02/1980"
           mostRecentSampleTime="01/01/1990"
           timeFormat="dd/MM/yyyy"/>
```

This is completely equivalent to

```xml
<parameter spec="RealParameter"
           value="9.825137 9.844262"/>
```

but much more readable.

Note that the time format specification string is the same as used by the
`dateFormat` input to `TraitSet`, and applies both to the values of `time`
and the value of `mostRecentSampleTime`.  If this format specifier is not provided,
the time is assumed to have already been converted to fractional years, so
the only action taken by TimeParameter is to subtract this value from
`mostRecentSampleTime`.

Sequence alignment simulation
-----------------------------

While BEAST contains its own alignment simulator in `beast.app.seqgen.SimulatedAlignment`,
this can be awkward to use as it requires pre-specifying the taxa to associate
with the simulated alignment.

The `feast.simulation.SimulatedAlignment` class is a simpler alignment simulator
which lifts this requirement.  For example, the following will initialize
an alignment by simulating sequences down the input tree using a Jukes-Cantor
substitution model:

```xml
<alignment spec="feast.simulation.SimulatedAlignment"
           outputFileName="simulated_alignment.nexus"
           tree="@tree" sequenceLength="1000">
    <siteModel spec="SiteModel">
        <substModel spec="JukesCantor"/>
    </siteModel>
</alignment>
```

The class supports the same inputs as `Alignment` and `beast.app.seqgen.SimulatedAlignment`,
with the exception that the `data` input should be omitted - it will cause
strange errors if you include it.  Also, only strict clocks are currently
supported, so there is no `branchRateModel` input.

Easy state simulation
---------------------

One BEAST 2 idiom which is pervasive in my code at least is the existance
of StateNodes that initialize their own state stochastically.  `RandomTree`
is an example of this in the core: this class is a `Tree` that initializes
itself by drawing from a coalescent distribution with a given population
function.

While such classes usually exist to choose a starting state for the
MCMC chain, it is often necessary - particularly in the validation phase
of model development - to simulate a large number of instances of this
simulated state.  This is usually because the simulation is often done from some
known distribution, which can then be compared against the output of MCMC.

The class `feast.simulation.GPSimulator` (from "general purpose simulator")
class simply makes it possible to configure this kind of simulation using
a BEAST XML file.  It is simply a `beast.core.Runnable` that takes a `BEASTObject`
as input, a number of iterations and some loggers. For each iteration, it then simply calls the
`BEASTObject`'s `initAndValidate` method and tells the loggers to write their
results.

See `examples/SimulateCoalescentTrees.xml` for an example of using `GPSimulator`
to simulate 100 coalescent trees.

Function Slicing, Reversing and Concatenating
---------------------------------------------

Instances of the `feast.function.Slice` class are `Function`s and `Loggable`s which
represent or more elements of another `Function`.  This allows
element-specific priors to be set, and individual elements to be logged.

Here's a simple example of using `Slice` to place a prior on a subset of elements
of a `RealParameter`:

```xml
<distrib spec="Prior">
    <x spec="feast.function.Slice" arg="@samplingProportion" index="2" count="1"/>
    <distr spec="LogNormalDistributionModel" M="0" S="1"/>
</distrib>
```

Similarly, instances of the `feast.function.Reverse` class are
`Funtion`s and `Loggable`s which represent the elements of another
`Function` but in reverse order.

Instances of the `feast.function.Concatenate` class are `Function`s
and `Loggable`s whose elements are composed of the elements of their
input `Function`s joined together.  For example, here is a `Function`
representing a piecewise constant reproductive number to be used as
input to the BDSKY tree prior, but composed of distinct input parameters
rather than a single real parameter with multiple dimensions:

```xml
<reproductiveNumber spec="feast.function.Concatenate">
    <arg spec="RealParameter" value="1.0"/>
    <arg spec="RealParameter" value="2.0"/>
</reproductiveNumber>
```

DensityMapper
-------------

This class provides an easy way to produce plots of the variation in a
particular `Distribution` (i.e. probability density) as a function of one or
more parameters.  The class extends the BEAST `Runnable` abstract class and
thus takes the place of the `MCMC` class in a regular BEAST XML.  To use
`DensityMapper` you'll therefore need to create an XML with the following
general form:

```xml
<beast version="2.0">
    <run spec="DensityMapper">
        <distribution id="distrib" ... />

        <realParam spec="RealParameter" id="paramA"
            value="V_A" lower="LOWER_A" upper="UPPER_A"/>
        <steps spec="IntegerParameter"
            value="STEPS_A"/>

        <realParam spec="RealParameter" id="paramB"
            value="V_B1 V_B2" lower="LOWER_B" upper="UPPER_B"/>
        <steps spec="IntegerParameter"
            value="STEPS_B1 STEPS_B2"/>

        <logger spec="Logger" logEvery="1">
            <log idref="paramA"/>
            <log idref="paramB"/>
            <log idref="distrib"/>
        </logger>

        <!-- Additional (screen?) loggers -->
    </run>
</beast>
```

The `<realParam>` elements specify which parameters are varied, the upper and
lower bounds of these parameters specify the range over which the parameter is
varied, and the corresponding `<steps>` elements specify the number of steps to
use for each parameter. When the number of steps is 1 the parameter is not
varied and instead the contents of the `value` attribute is used.

When parameters are vectors, the associated `<steps>` parameter may have
dimension 1 in which case all elements of the vector parameter are varied
together (e.g. [0,0], [0.1,0.1], ..., [1, 1]).  Alternatively, the steps
parameter may have dimension equal to that of the real parameter in which case
each element of the parameter is varied independently with the corresponding
number of steps (e.g. [0,0], [0, 0.1], ..., [0, 1], [0.1, 0.0], ..., [1, 1]).
To keep one component of a vector parameter fixed, set the corresponding steps
element to 1.

If, in addition to the `<realParam>` and `<steps>` elements a
`<logScale>` BooleanParameter element is provided, DensityMapper will
evaluate the densities at a logarithmic distribution of parameter
values between the same bounds.  I.e., the following snippet will
cause `paramA` to be set to values logarithmically distributed between
LOWER_A and UPPER_A:

```xml
        <realParam spec="RealParameter" id="paramA"
            value="V_A" lower="LOWER_A" upper="UPPER_A"/>
        <steps spec="IntegerParameter"
            value="STEPS_A"/>
        <logScale spec="BooleanParameter"
            value="true"/>
```

Take care that if the log-scale element is provided for one parameter that it is
also provided for all other parameters.

An example usage of `DensityMapper` which produces the variation in a
coalescent tree density as a function of the population size is provided as
`DensityMapper.xml` which can be found in the examples directory.

TreeFromNewickFile & TreeFromNexusFile
--------------------------------------

These classes allow trees to be loaded at runtime from Newick files rather
than stored in the BEAST 2 XML:

```xml
<tree spec='feast.fileio.TreeFromNewickFile' fileName="example.tree"
    IsLabelledNewick="true" adjustTipHeights="false" .../>
```
or 
```xml
<tree spec='feast.fileio.TreeFromNexusFile' fileName="example.nexus"
    IsLabelledNewick="true" adjustTipHeights="false" .../>
```

The optional attribute `treeIndex` can be used to specify the index (starting
from zero) of the tree to read from the file.

For Nexus files, only the first trees block encountered is processed.

Both classes are thin wrappers around `TreeParser`, so all of the various inputs
supported by that class are also supported.

TraitSetFromTaxonSet
--------------------

This class allows `TraitSet`s to be configured directly from `TaxonSet`s.  This
is similar in spirit to the "auto configuration" option provided by BEAUti to
set up tree tip dates from information encoded in the sequence names.

Use this as you would usually use a `TraitSet`, but instead of including the
trait assignments directly in the body, specify inputs indicating how to extract
the trait values from the taxon names.

For instance:

```xml
<trait spec="feast.fileio.TraitSetFromTaxonSet"
       delimiter="|"
       everythingAfterLast="true"
       traitname="date"
       dateFormat="yyyy-M-dd">
       <taxa id="taxonSet" spec="TaxonSet" alignment="@alignment"/>
</trait>
```

Instead of `everythingAfterLast="true"`, one can also use `everythingBeforeFirst="true"` or
`takeGroup="N"` where N is the index of the group (delimited by the chosen delimiter).

AlignmentFromNexus/Fasta
------------------------

This class allows alignments to be loaded at runtime from Nexus or Fasta files
rather than stored in the BEAST 2 XML:

```xml
<!-- Nexus: -->
<alignment spec='feast.fileio.AlignmentFromNexus' fileName="example.nexus"/>

<!-- Fasta: -->
<alignment spec='feast.fileio.AlignmentFromFasta' fileName="example.fasta"/>
```

The Fasta import uses the sequence labels from the file as taxon labels.

Obviously these classes runs contrary to BEAST's philosophy of keeping
everything necessary to run an analysis in the XML file.  However, it
is sometimes convenient to be able to do this.  As a bonus, the
`xmlFileName` attribute can be used to write the appropriate XML
fragment to disk.


NexusWriter
-----------

This class complements `NexusParser`, allowing both trees and alignments to be
written to [Nexus files](http://dx.doi.org/10.1093%2Fsysbio%2F46.4.590).
It contains a single static method
```java
public static void write(Alignment alignment, List<Tree> trees, PrintStream ps)
```
that writes the Nexus-formatted data to the given `PrintStream`.

This could be easily used as the basis for a utility which extracts alignments
from BEAST 2 input files.


Expression Calculator
---------------------

The following classes, which are found in `feast.expressions`, provide
support for parsing of simple arithmetic expressions.


### ExpCalculator ###

Takes an arithmetic expression and returns the result by acting
as a Loggable or a Function.  Binary operators can be applied to
Functions (including Parameters) of different lengths as in R, with
the result having the maximum of the two lengths, and the index into
the shortest parameter being the result index modulo the length of
that parameter. True and false are represented by 1.0 and 0.0 respectively.

Example expressions (I and J are IDs of RealParameters with elements
{1.0, 2.0, 3.0} and {5.0, 10.0}, respectively.)

    Expression                 |  Loggable/Function value
    ------------------------------------------------------
    2.5*I                      | {2.5, 5.0, 7.5}
    I+J                        | {6.0, 12.0, 8.0}
    exp(I[0])                  | {2.718...}
    {I,J}                      | {1.0, 2.0, 3.0, 5.0, 10.0}
    1:3                        | {1.0, 2.0, 3.0}
    J[1:0]                     | {10.0, 5.0}
    1[1:3]                     | {1.0, 1.0, 1.0}
    -log(exp(J))/(1.5+0.5*I[0])| {-2.5, -5.0}
    sqrt(J)                    | {2.236..., 3.162...}
    J!                         | {1.0, 2.0, 6.0}
    2^I                        | {2.0, 4.0, 6.0}  
    sum(I)                     | {6.0}
    {1,2,3}^2                  | {1.0, 4.0, 9.0}
    theta(I-2)                 | {0.0, 1.0, 1.0}
    I < 3 && I >=1             | {1.0, 1.0, 0.0}
    I < 3 ? 12 : 13            | {12.0, 12.0, 13.0}
    {min(J), max(J), len(J)}   | {5.0, 10.0, 2.0}
    sort({5,1,3})              | {1.0, 3.0, 5.0}

Note that since each ExpCalculator is a Function object itself, it can
be used as the input for other ExpCalculators.

Inspired by RPNcalculator by Joseph Heled (BEAST 1, BEAST 2 port by
Denise Kuehnert).  (This uses a parser generated by
[ANTLR](http://www.antlr.org), which is cheating.)


### ExpCalculatorDistribution ###

This is basically identical to ExpCalculator, but extends the abstract
Distribution class, allowing the definition of arbitrary distributions
over R^n at runtime.  Distributions can be specified in terms of their
log or directly as probabilities (/probability densities).

The file `ECdistribTest.xml` in the examples/ folder is a simple
example where a multi-variate normal distribution is constructed.
This example also illustrates the nested expression concept mentioned
above.  The file `ECdistribArraytest.xml` in the same folder samples
the same posterior, but uses array notation to express the target
density.


### ExpCalculatorParametricDistribution ###

This provides a cut-down version of the ExpCalculatorDistribution
functionality that is compatible with beast.math.distributions.Prior.


License
-------

This software is free (as in freedom).  With the exception of the
libraries on which it depends, it is made available under the terms of
the GNU General Public Licence version 3, which is contained in this
directory in the file named COPYING.
