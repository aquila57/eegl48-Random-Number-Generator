/* binom.c - Binomial Distribution Test Version 0.1.0                */
/* Copyright (C) 2019-2020 aquila57 at github.com                    */

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

/* This program performs a chi square test on a sample */
/* population in a binomial distribution */
/* The program flips a "coin" 19 times and counts the */
/* number of heads in the tuple */
/* The program samples the population 1 million times. */
/* The expected results are based on Pascal's triangle */
/* for 2^19 */
/* To determine heads or tails, */
/* the java eegl48 generator is used */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
#include <errno.h>
#include <gsl/gsl_cdf.h>
#include <gsl/gsl_sf_erf.h>
#include <gsl/gsl_rng.h>

#define EOFKS (-999999999.0)

typedef struct xxstruct {
   int eofsw;
   } xxfmt;

double geteegl(xxfmt *xx)
   {
   int len;
   double num;
   char buf[256];
   len = scanf("%s", buf);
   if (len < 1)
      {
      if (feof(stdin))
	 {
	 xx->eofsw = 1;
         return(EOFKS);
	 } /* if end of file */
      if (ferror(stdin))
         {
         perror("geteegl: read error\n");
         exit(1);
         } /* read error */
      fprintf(stderr,"geteegl: blank line\n");
      exit(1);
      } /* if eof or read error */
   if (len > 1)
      {
      fprintf(stderr,"geteegl: read error\n");
      fprintf(stderr,"scanf read more than "
         "one token %d\n", len);
      exit(1);
      } /* invalid input */
   num = atof(buf);
   return(num);
   } /* geteegl */

int main()
   {
   int i;                  /* loop counter for #samples */
   int heads;              /* Number of heads in a trial */
   int smpls;              /* total number of samples */
   double chisq;           /* chi square total */
   double maxchi;          /* chi square total */
   double minchi;          /* chi square total */
   double df;              /* chi square total */
   double pvalue;          /* chi square total */
   double *p,*q,*r;        /* pointers to actual and expected */
   double actual[64];      /* actual sample totals */
   double expected[64];    /* expected totals */
   xxfmt *xx;

   /*************************************************************/
   /* Allocate memory for the global structure.                 */
   /* This is a re-entrant program.                             */
   /*************************************************************/

   xx = (xxfmt *) malloc(sizeof(xxfmt));
   if (xx == NULL)
      {
      fprintf(stderr,"main: out of memory "
         "allocating xx\n");
      exit(1);
      } /* out of memory */


   /*************************************************************/
   /* initialize global data                                    */
   /*************************************************************/
   xx->eofsw = 0;
   /*********************************************************/
   /* initialize total arrays                               */
   /*********************************************************/
   p = (double *) actual;
   q = (double *) actual + 64;
   while (p < q) *p++ = 0.0;
   p = (double *) expected;
   q = (double *) expected + 64;
   while (p < q) *p++ = 0.0;
   /*********************************************************/
   /* set the expected totals based on Pascal's triangle    */
   /*********************************************************/
   p = (double *) expected;
   *p++ = 1.0;
   *p++ = 17.0;
   *p++ = 136.0;
   *p++ = 680.0;
   *p++ = 2380.0;
   *p++ = 6188.0;
   *p++ = 12376.0;
   *p++ = 19448.0;
   *p++ = 24310.0;
   *p++ = 24310.0;
   *p++ = 19448.0;
   *p++ = 12376.0;
   *p++ = 6188.0;
   *p++ = 2380.0;
   *p++ = 680.0;
   *p++ = 136.0;
   *p++ = 17.0;
   *p++ = 1.0;

   smpls = 0;
   p = (double *) expected;
   q = (double *) expected + 18;
   while (p < q)
      {
      smpls += (int) *p++;
      } /* for each # heads */
   printf("Total expected %d\n", smpls);
   printf("\n");

   /*********************************************************/
   /* Count actual number of heads                          */
   /*********************************************************/
   i = smpls;        /* loop counter for #samples */
   while (i--)
      {
      int j;           /* loop counter for counting heads    */
      int tothd;       /* total number of heads in one tuple */
      tothd = 0;       /* initialize total number of heads   */
      j = 17;          /* set loop counter                   */
      while (j--)
         {
	 double num;
	 num = geteegl(xx);
	 if (xx->eofsw)
	    {
	    fprintf(stderr,"End of file\n");
	    exit(1);
	    } /* end of file */
	 if (num >= 0.5) tothd++;     /* tally #heads in tuple  */
	 } /* for each flip */
      p = (double *) actual + tothd;   /* point to correct total */
      *p += 1.0;                   /* tally n # of heads */
      } /* for each m-bit sample */
   /*********************************************************/
   /* Calculate chi square                                  */
   /*********************************************************/
   printf("#Heads       Actual      Expected     Difference\n");
   heads = 0;
   chisq = 0.0;                /* initialize chi square total */
   p = (double *) actual;      /* point to actual array */
   q = (double *) actual + 18;    /* end of actual array */
   r = (double *) expected;    /* point to expected array */
   while (p < q)               /* for each actual total */
      {
      double diff;             /* difference = actual - expected */
      double diffsq;           /* difference squared */
      diff = *p - *r;          /* difference = actual - expected */
      diffsq = diff * diff;    /* square the difference */
      chisq += (diffsq / *r);     /* add to the chi square total */
      printf("%4d %13.0f %13.0f %12.0f\n",
         heads, *p, *r, diff);
      heads++;
      p++;                     /* next actual total */
      r++;                     /* next expected total */
      } /* for each actual total */
   printf("\n");
   printf("Binomial Distribution Test\n");
   printf("Java eegl48 Random Number Generator\n");
   printf("Total trials %d\n", smpls);
   df = 17;    /* degreees of freedom */
   /* 95% confidence level, calculate range */
   minchi = gsl_cdf_chisq_Pinv(0.025,df);
   maxchi = gsl_cdf_chisq_Pinv(0.975,df);
   /* calculate chi square P-value */
   pvalue = gsl_cdf_chisq_P(chisq,df);
   /* print results of chi square test */
   printf("Chi square %f  P-value %f\n", chisq, pvalue);
   printf("\n");
   printf("At 95%c probability and %.0f degrees of freedom,\n"
      "Chi square ranges from %f to %f\n",
      '%', df, minchi, maxchi);
   printf("\n");
   printf("Press CTL-C at end of job\n");
   return(0);
   } /* main */
