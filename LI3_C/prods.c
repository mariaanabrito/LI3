#include <stdlib.h>
#include <string.h>
#include "prods.h"
#include "catalogo.h"

#define INICIAIS 26

struct prods
{
	Catalogo product[INICIAIS];
};

struct prod
{
	Codigo p;
}Code;


Catalogo getSubCatalogoProds(CatProds catpr, int h)
{
	return(catpr->product[h]);
}

CatProds initCatProds()
{
	int i;
	CatProds p;
	p = malloc(sizeof(struct prods));
	if(p == NULL)
		return NULL;

	for(i = 0; i < INICIAIS; i++)
		{
			p->product[i] = NULL;
		}
	return p;
}

CatProds insereProduto(CatProds catpr, Produto pr)
{
	int h;
	char* letra;
	letra = malloc(sizeof(char));	

	strncpy(letra, pr->p, 1);
	h = hash(letra);

	catpr->product[h] = insert(catpr->product[h], pr->p);
	free(letra);
	return catpr;
}

Codigo devolveCodigoPr(Produto pr)
{
	return (pr->p);
}

int existeProduto(CatProds catpr, Produto pr)
{
	int res, h;
	char* letra;
	letra = malloc(sizeof(char));

	strncpy(letra, pr->p, 1);
	h = hash(letra);

	res = find(pr->p, catpr->product[h]);
	free(letra);
	return res;
}

Produto convProduto(char* linha)
{
	Produto pr;
	pr = malloc(sizeof(struct prod));
	pr->p = malloc((strlen(linha)+1)*sizeof(Codigo));
	strcpy(pr->p, linha);
	return pr;
}


int totalProdutos(CatProds catpr)
{
	int i, sum = 0;

	for(i = 0; i < INICIAIS; i++)
		sum += size(catpr->product[i]);

	return sum;
}

int totalProdutosLetra(CatProds catpr, char* letra)
{
	int h = hash(letra);

	return (size(catpr->product[h]));
}

void removeCatProds(CatProds catpr)
{
	int i;

	for(i = 0; i < INICIAIS; i++)
		dispose(catpr->product[i]);
}

void freeProds(CatProds catpr)
{
	if (catpr != NULL)
	{
		removeCatProds(catpr);
		free(catpr);
	}
}
