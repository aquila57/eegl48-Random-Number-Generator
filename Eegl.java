/***************************************************************************
 *  Compilation:  javac Eegl.java
 *  Execution:    methods called as subroutines
 *
 *  Generates random numbers
 *
 *  Examples of calling the methods of Eegl Class
 *  % Eegl.init()
 *  % Eegl.gen()
 *  % Eegl.get()
 *  % Eegl.random()
 *
 **************************************************************************/
/* Eegl.java  - eegl48 random number generator, core methods         */
/* Version 0.1.0                                                     */
/* Copyright (C) 2020 aquila57 at github.com                         */

/* This program is free software; you can redistribute it and/or     */
/* modify it under the terms of the GNU General Public License as    */
/* published by the Free Software Foundation; either version 2 of    */
/* the License, or (at your option) any later version.               */

/* This program is distributed in the hope that it will be useful,   */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of    */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the      */
/* GNU General Public License for more details.                      */

/* You should have received a copy of the GNU General Public License */
/* along with this program; if not, write to:                        */

   /* Free Software Foundation, Inc.                                 */
   /* 59 Temple Place - Suite 330                                    */
   /* Boston, MA 02111-1307, USA.                                    */

public class Eegl {

    static long fibo1;      /* fibonacci number */
    static long fibo2;      /* fibonacci number */
    static long fibo3;      /* fibonacci number */
    static long lfsr;       /* current 48 bit LFSR */
    static long prev;       /* previous 48 bit LFSR */
    static long pprev;      /* prior-previous 48 bit LFSR */
    static long num;        /* integer output of generator */
    static long maxint;     /* maximum integer 2^48 */
    static long mask;       /* and mask 2^48 - 1    */
    static double unif;     /* random number zero to one */
    static double modulus;     /* double precision 2^48 */

    // 16384 LFSR registers for the Bays-Durham shuffle */

    static long[] state = new long[16384];

    public static void init() {
       int i;
       long num;
       double dblnum;
       modulus  = 65536.0;
       modulus *= 65536.0;
       modulus *= 65536.0;
       maxint  = 65536;
       maxint *= 65536;
       maxint *= 65536;
       mask = maxint - 1;
       //------------------------------------------------
       // Populate all LFSR registers with random 48 bit
       // integers.
       //------------------------------------------------
       for (i=0;i<16384;i++)
          {
	  dblnum = Math.random() * modulus;
	  num = (long) Math.floor(dblnum);
	  state[i] = num;
	  } /* for each register in state */
       dblnum = Math.random() * modulus;
       num = (long) Math.floor(dblnum);
       prev = num;
       dblnum = Math.random() * modulus;
       num = (long) Math.floor(dblnum);
       pprev = num;
       dblnum = Math.random() * modulus;
       num = (long) Math.floor(dblnum);
       lfsr = num;
       //--------------------------------------------------
       // Populate fibonacci numbers with random 48 bit
       // integers.
       //--------------------------------------------------
       dblnum = Math.random() * modulus;
       num = (long) Math.floor(dblnum);
       fibo1 = num;
       dblnum = Math.random() * modulus;
       num = (long) Math.floor(dblnum);
       fibo2 = num;
       fibo3 = (fibo1 + fibo2) & mask;
       } // init()

    public static long gen() {
       long out = 0;
       long tmp;
       int indx;
       //-----------------------------------------
       // Back up previous LFSR and fibonacci numbers
       //-----------------------------------------
       fibo1 = fibo2;
       fibo2 = fibo3;
       pprev = prev;
       prev  = lfsr;
       //-----------------------------------------
       // Create a new LFSR generation
       // 48-bit LFSR 48,44,41,39
       //-----------------------------------------
       out = ((lfsr >> 9) & 1L);
       out ^= ((lfsr >> 7) & 1L);
       out ^= ((lfsr >> 4) & 1L);
       out ^= ((lfsr >> 0) & 1L);
       lfsr = (lfsr >> 1) | (out << 47);
       lfsr &= mask;
       //-----------------------------------------
       // Bays-Durham shuffle
       //-----------------------------------------
       indx = (int) (pprev & 16383);
       tmp = state[indx];
       state[indx] = lfsr & mask;
       lfsr = tmp & mask;
       //-----------------------------------------
       // fibonacci generation
       //-----------------------------------------
       fibo3 = (fibo1 + fibo2) & mask;
       //-----------------------------------------
       // generate output
       //-----------------------------------------
       num = (lfsr ^ prev ^ pprev ^ fibo3) & mask;
       unif = num;
       unif /= modulus;
       return(num);
       } // gen()

    //-----------------------------------------
    // Display generator output
    //-----------------------------------------
    public static void puteegl() {
       System.out.print("num ");
       System.out.println(num);
       System.out.print("unif ");
       System.out.println(unif);
       } // putlfsr()

    //-----------------------------------------
    // Debugging method to display registers
    //-----------------------------------------
    public static void putlfsr() {
       System.out.print("lfsr ");
       System.out.print(lfsr);
       System.out.print(" prev ");
       System.out.print(prev);
       System.out.print(" pprev ");
       System.out.println(pprev);
       System.out.print("fibo1 ");
       System.out.print(fibo1);
       System.out.print("fibo2 ");
       System.out.print(fibo2);
       System.out.print("fibo3 ");
       System.out.print(fibo3);
       } // putlfsr()

    //-----------------------------------------
    // Output 48 bit random integer
    //-----------------------------------------
    public static long get() {
       return(num);
       } // get()

    //-----------------------------------------
    // Output double precision random number
    // from zero to one
    //-----------------------------------------
    public static double random() {
       return(unif);
       } // random()

} // Eegl Class

