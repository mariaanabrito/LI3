#include <string.h>
#include <stdlib.h>
#include "vendas.h"

struct vendas
{
	double preco;
	int filial, mes, quantos;
	char *modo;
	char *cliente;
	char *prod ;
};

double getPreco(Vendas v)
{
	return (v->preco);
}

int getFilial(Vendas v)
{
	return (v->filial);
}

int getMes(Vendas v)
{
	return (v->mes);
}

int getQuantos(Vendas v)
{
	return (v->quantos);
}

char* getModo(Vendas v)
{
	return (v->modo);
}

char* getCliente(Vendas v)
{
	return (v->cliente);
}

char* getProd(Vendas v)
{
	return (v->prod);
}

void setPreco(Vendas v, double p)
{
	v->preco = p;
}

void setFilial(Vendas v, int f)
{
	v->filial = f;
}

void setMes(Vendas v, int m)
{
	v->mes = m;
}

void setQuantos(Vendas v, int q)
{
	v->quantos = q;
}

void setModo(Vendas v, char* m)
{
	int length;
	length = (int)strlen(m) + 1;
	v->modo = (char*)malloc((long unsigned int)length*sizeof(char));
	strcpy(v->modo, m);
}

void setCliente(Vendas v, char* c)
{
	int length;
	length = (int)strlen(c) + 1;
	v->cliente = (char*)malloc((long unsigned int)length*sizeof(char));
	strcpy(v->cliente, c);
}

void setProd(Vendas v, char* p)
{
	int length;
	length = (int)strlen(p) + 1;
	v->prod = (char*)malloc((long unsigned int)length*sizeof(char));
	strcpy(v->prod, p);
}

Vendas initVendas()
{
	Vendas v;

	v = malloc(sizeof(struct vendas));

	if (v == NULL)
		return NULL;

	v->preco = 0.0;
	v->filial = v->mes = v->quantos = 0;

	return v;
}

void freeVendas(Vendas v)
{
	if (v != NULL)
	{
		if (v->modo != NULL)
			free(v->modo);
		if (v->cliente != NULL)
			free(v->cliente);
		if (v->prod != NULL)
			free(v->prod);
	}

	free(v);
}
