//@author Pedro Gutiérrez Vico 2025

#include <stdio.h>
#include <unistd.h>
int main (){
	fork();
	printf("Soy el proceso padre o hijo?\n");
	return 0;
}
