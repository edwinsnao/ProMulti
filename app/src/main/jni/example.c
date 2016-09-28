#include <time.h>

/**A variable*/
double  PI =  3.1415927 ;

/**Return n!*/
int  fact ( int  n ) {
    if  ( n <=  1 )
        return  1 ;
    else
        return  n*fact ( n- 1 ) ;
}

/**mod function*/
int  mod ( int  x,  int  y ) {
    return  ( x%y ) ;
}

/**Get time as String*/
char  * getTime (){
    time_t ltime;
    time ( &ltime ) ;
    return  ctime ( &ltime ) ;
}

/**to upper case*/
char  * toUpperCase ( char  * result ){
    char  * p = result;
    while ( '\0' !=*p ){
        char  c = *p;
        if  ( ( c >  'a' ) && ( c <  'x' ) ){
            *p = c- 32 ;
        }
        p++;
    }
    return  result;
}