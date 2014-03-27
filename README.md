feast:  Bite-sized additions to BEAST 2
=======================================

This is a small [BEAST 2](http://www.beast2.org) package which
contains some additions to the core functionality.  It is compatible
with BEAST 2.1 and higher.

[![Build Status](https://travis-ci.org/tgvaughan/feast.svg?branch=master)](https://travis-ci.org/tgvaughan/feast)

Installation
------------

To install, download the latest release from
[here](https://github.com/tgvaughan/feast/releases) and extract it
into one of the following locations, depending on your operating
system:

 * GNU/Linux: ~/.beast/feast
 * Mac OS X: /library/Application Support/BEAST/feast
 * Windows: #HOME#\BEAST\feast (#HOME# is your user home directory)

Alternatively, for BEAST 2.1.2 and higher, you can install directly
from within BEAUti by adding the following URL
`http://tgvaughan.github.io/feast/package.xml` to the list of
package repositories and selecting feast from the package list.


Expression Calculator (ExpCalculator)
-------------------------------------

Takes simple arithmetic expressions and returns the result by acting
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
    2^I	                       | {2.0, 4.0, 6.0}  
    sum(I)                     | {6.0}
    [1,2,3]^2                  | {1.0, 4.0, 9.0}
    [I,J]                      | {1.0, 2.0, 3.0, 5.0, 10.0}

Note that since each ExpCalculator is a Function object itself, it can
be used as the input for other ExpCalculators.

Inspired by RPNcalculator by Joseph Heled (BEAST 1, BEAST 2 port by
Denise Kuehnert).  (This parser uses [ANTLR](http://www.antlr.org),
which is cheating.)


Expression Calculator Distribution (ExpCalculatorDistribution)
--------------------------------------------------------------

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


License
-------

This software is free (as in freedom).  With the exception of the
libraries on which it depends, it is made available under the terms of
the GNU General Public Licence version 3, which is contained in this
directory in the file named COPYING.

The following libraries are bundled with Feast:

* ANTLR (http://www.antlr.org)

Those libraries are distributed under the licences provided in the
LICENCE.* files included in this archive.
