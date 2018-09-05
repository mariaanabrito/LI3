#include <stdlib.h>
#include <string.h>
#include "clientes.h"
#include "catalogo.h"

#define LIMS 25
#define INICIAIS 26

struct clientes
{
	Catalogo client[26];
};


struct cliente
{
	Codigo c;
}Code;


Catalogo getSubCatalogo(CatClientes c, int h)
{
	if ((h >= 0) && (h <= LIMS))
		return (c->client[h]);
	return NULL;
}

Codigo getCodigoCliente(Cliente c)
{
	return c->c;
}

CatClientes initCatClientes()
{
	int i;
	CatClientes c;

	c = malloc(sizeof(struct clientes));
	if(c == NULL)
	{
		return NULL;
	}
	for(i = 0; i < INICIAIS; i++)
		c->client[i] = NULL;
	
	return c;
}

Cliente convCliente(char* linha)
{
	Cliente cl;
	cl = malloc(sizeof(struct cliente));
	cl->c = malloc((strlen(linha)+1)*sizeof(Codigo));
	strcpy(cl->c, linha);
	return cl;
}

int totalClientes(CatClientes catcl)
{
	int i, sum = 0;

	for(i = 0; i < INICIAIS; i++)
		sum += size(catcl->client[i]);
	return sum;
}

int totalClientesLetra(CatClientes catcl, char* letra)
{
	int h = hash(letra);

	return (size(catcl->client[h]));
}


CatClientes insereCliente(CatClientes catcl, Cliente cl)
{
	int h;
	char* letra;
	letra = malloc(sizeof(char));	

	strncpy(letra, cl->c, 1);
	h = hash(letra);
	catcl->client[h] = insert(catcl->client[h], cl->c);

	free(letra);
	return catcl;
}


int existeCliente(CatClientes catcl, Cliente cl)
{
	int res, h;
	char* letra;
	letra = malloc(sizeof(char));

	strncpy(letra, cl->c, 1);
	h = hash(letra);

	res = find(cl->c, catcl->client[h]);

	free(letra);
	return res;
}

void removeCatClientes(CatClientes catcl)
{
	int i;

	for(i = 0; i < INICIAIS; i++)
		dispose(catcl->client[i]);
}

void freeClientes(CatClientes catcl)
{
	if (catcl != NULL)
	{
		removeCatClientes(catcl);
		free(catcl);
	}
}
