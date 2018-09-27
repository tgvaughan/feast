feast:  Bite-sized additions to BEAST 2
=======================================

This is a small [BEAST 2](http://www.beast2.org) package which
contains some additions to the core functionality.

[![Build Status](https://travis-ci.org/tgvaughan/feast.svg?branch=master)](https://travis-ci.org/tgvaughan/feast)


Installation
------------

You can install directly from within BEAUti by adding
the following URL `http://tgvaughan.github.io/feast/package.xml` to
the list of package repositories and selecting feast from the package
list.

More recent releases are also made available as archives containing
only the necessary jar files and the associated javadocs. These are
for the use of BEAST 2 package developers who want to use some of the
classes but don't want to add a full package dependency.  These
archives have the form `*-jarsOnly.zip` and are also found at
https://github.com/tgvaughan/feast/releases.


Building from source
--------------------

The default target in the provided [Apache ANT](http://ant.apache.org)
build script can be used to build the BEAST 2 package from scratch.
The package will be left in the `dist/` directory.

Features
========

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
    <indicator spec="BooleanParameter" value="true true false false"/>
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

Function Slicing
----------------

Instances of the `feast.function.Slice` class are `Function`s and `Loggable`s which
represent or more elements of another `Function`.  This allows
element-specific priors to be set, and individual elements to be logged.

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
        <logger>

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

TreeFromNewickFile
------------------

This class allows trees to be loaded at runtime from Newick files rather
than stored in the BEAST 2 XML:

```xml
<tree spec='feast.fileio.TreeFromNewickFile' fileName="example.tree"
    IsLabelledNewick="true" adjustTipHeights="false" .../>
```

This is a short extension to `TreeParser`, so all of the various inputs
supported by that class are also supported by `TreeFromNewickFile`.

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
that parameter.

Example expressions (I and J are IDs of RealParameters with elements
{1.0, 2.0, 3.0} and {5.0, 10.0}, respectively.)

    Expression                 |  Loggable/Function value
    ------------------------------------------------------
    2.5*I                      | {2.5, 5.0, 7.5}
    I+J                        | {6.0, 12.0, 8.0}
    exp(I[0])                  | {2.718...}
    -log(exp(J))/(1.5+0.5*I[0])| {-2.5, -5.0}
    sqrt(J)                    | {2.236..., 3.162...}
    2^I                        | {2.0, 4.0, 6.0}  
    sum(I)                     | {6.0}
    {1,2,3}^2                  | {1.0, 4.0, 9.0}
    {I,J}                      | {1.0, 2.0, 3.0, 5.0, 10.0}
    theta(I-2)                 | {0.0, 1.0, 1.0}

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
