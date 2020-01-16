#!/bin/bash
make -f libks.mak clean
make -f kstest.mak clean
make -f binom.mak clean
rm -f *.class
