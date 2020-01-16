/***************************************************************************
 *  Compilation:  javac JavaRand.java
 *  Execution:    java JavaRand
 *
 *  Runs a timing test on 10 million generations of the Java
 *  random number generator, Math.random().
 *
 *  % timing.sh
 *  Timing of this generator is compared to the eegl48 random
 *  number generator.
 *
 **************************************************************************/
/* Copyright © 2020,  aquila57 at github.com. */

public class JavaRand {

    public static void main(String[] args) {
	int i;
	Eegl.init();
	for (i=0;i<100000000;i++)
	   {
	   double frac;
           frac = Math.random();
	   } // for each loop
    } // public static void main(String[] args)

} // JavaRand


