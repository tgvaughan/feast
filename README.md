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

Documentation
-------------

A guide to the various components in feast (with examples) can be
found at https://tgvaughan.github.io/feast.

License
-------

This software is free (as in freedom).  With the exception of the
libraries on which it depends, it is made available under the terms of
the GNU General Public Licence version 3, which is contained in this
directory in the file named COPYING.
