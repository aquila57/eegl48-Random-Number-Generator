# eegl48 Random Number Generator in Java

This is a Java implementation of the random number
generator in the eegl64 repository.

The random number generator here is driven by a 48 bit
linear feedback shift register, combined with a fibonacci
generator and a Bays-Durham shuffle of 16384 LFSRs.
Each 48 bit output is the result of XORing the current
LFSR with two previous LFSRs and a fibonacci sum.
The expected period length of this generator approximates
the permutations of 16384, or 1.2 * 10^61036.  This period
length is orders of magnitude longer than the Mercenne
Twister, which has a period length of 2^19937.

The performance of this generator is equivalent to the
Java generator, Math.random();

So far this generator passes the Kolmogorov-Smirnov test
and the binomial distribution test.

The eegl48 generator is contained in the Eegl.java class.
Eegl.gen() produces a 48 bit random integer with a uniform
distribution.
Eegl.random() produces a double precision uniform number
between zero and one.
