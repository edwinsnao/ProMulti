%module Example


%{
     /* Put header files here or function declarations like below */
     extern  double  PI;
     extern  int  fact ( int  n ) ;
     extern  int  mod ( int  x,  int  y ) ;
     extern  char  * getTime () ;
     extern  char  * toUpperCase ( char  * str ) ;
%}
typedef unsigned int uid_t;

extern uid_t getuid(void);
extern  double  PI;
extern  int  fact ( int  n ) ;
extern  int  mod ( int  x,  int  y ) ;
extern  char  * getTime () ;
extern  char  * toUpperCase ( char  * str ) ;