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
/* Binom.java  - eegl48 random number generator for Binomal Test     */
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

public class Binom {

    public static void main(String[] args) {
	int i;
	Eegl.init();
	for (i=0;i<2500000;i++)
	   {
	   long x;
	   double frac;
	   x = Eegl.gen();
           frac = Eegl.random();
	   System.out.println(frac);
	   } // for each loop
    } // public static void main(String[] args)

} // Binom


