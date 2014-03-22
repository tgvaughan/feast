feast:  Bite-sized additions to BEAST 2
=======================================

This is a small [BEAST 2](http://www.beast2.org) package which
contains some additions to the core functionality.

Expression Calculator
---------------------

Takes simple arithmetic expressions and returns the result by acting
as a Loggable or a Function.  Binary operators can be applied to
Parameters of different lengths as in R, with the result having the
maximum of the two lengths, and the index into the shortest parameter
being the result index modulo the length of that parameter.

Example expressions (I and J are IDs of RealParameters with elements
{1.0, 2.0, 3.0} and {5.0, 10.0}, respectively.)

    Expression                 |  Loggable/Function value
    ------------------------------------------------------
    2.5*I                      | {2.5, 5.0, 7.5}
    I+J                        | {6.0, 12.0, 8.0}
    exp(I[0])                  | {2.718...}
    -log(exp(J))/(1.5+0.5*I[0])| {-2.5, -5.0}
    sqrt(J)                    | {2.236..., 3.162...}
    sum(I)                     | {6.0}

Inspired by RPNcalculator by Joseph Heled (BEAST 1, BEAST 2 port by
Denise Kuehnert).  (This parser uses [ANTLR](http://www.antlr.org),
which is cheating.)

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
