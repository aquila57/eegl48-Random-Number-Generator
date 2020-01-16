#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
#include <errno.h>

#define SMPLS (1000000)

#define EOFKS (-999999999.0)

typedef struct treestruct {
   struct treestruct *left;
   struct treestruct *rght;
   double num;
   } treefmt;

typedef struct xxstruct {
   int eofsw;
   unsigned int maxint;
   double dblsmpls;
   double count;
   double duplicates;
   double modulus;
   double sumx;
   double deltax;
   double maxdiff;
   treefmt *root;
   } xxfmt;

treefmt *treeinit(void);

void isrt(treefmt *node, double num, xxfmt *xx);

void rmtree(treefmt *node);

void traverse(treefmt *node, xxfmt *xx);

double geteegl(xxfmt *xx);
