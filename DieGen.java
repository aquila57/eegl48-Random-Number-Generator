/***************************************************************************
 *  Compilation:  javac Binom.java
 *  Execution:    java Binom
 *
 *  eegl48 generator, generates input to a binomial distribution
 *  test.
 *
 *  % java Binom
 *
 **************************************************************************/
/* DieGen.java  - eegl48 random number generator for DieharderTest   */
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

import java.io.*;

public class DieGen {

    public static void main(String[] args) {
	int i;
	Eegl.init();
	OutputStream outputStream;
	for (i=0;i==i;)
	   {
	   long x;
	   int j;
	   x = Eegl.gen();
	   for (j=0;j<6;j++)
	      {
	      int ch;
	      ch = (int) (x & 255);
	      System.out.write(ch);
	      x >>= 8;
	      } // for each 8 bits in 48 bit word
	   } // for each generation
    } // public static void main(String[] args)

} // DieGen


