#include <stdio.h>
#include <unistd.h>
int main (){
    printf("Soy el padre , por cojones y generare 8 procesos en forma de arbol binario \n");
    fork();
        //p0 y p1
    /**
     * Despues puedo ser p0 o p1
     */
    fork(); //p0 o p2     p1 o p5
    /**
     * partiendo de p0 , despues puedo ser p0 o p2
     * partiendo de p1 , despues puedo ser p1 o p5
     */
    fork();
    /**
     * Partiendo de p0, despues puedo ser p0 o p2
     * partiendo de p1 despues puedes ser p1 o p6
     * 
     */
    printf("Soy padre o un hijo?\n");
    return 0 ;
}