#include <string.h>
#include <stdlib.h>
#include "catalogo.h"
#include "lista.h"

#define MAXPAGE 20
#define ANTERIOR 40

/**
* A estrutura Conj contém a lista (array) dos elementos de um Conjunto, bem como o seu tamanho e o índice da página atual.
*/
struct conj
{
	char **lista;
	int npage;
	int tamanho;
}Conj;

/**
* A estrutura struct pagina contém um sub-array de elementos do Conjunto, bem como o tamanho da página e o índice atual.
*/
struct pagina
{
	char **page;
	int pagesize;
	int index;
};

Codigo criarString(Conjunto c, int index, Codigo conteudo)
{
	c->lista[index] = malloc(((strlen(conteudo)+1)*sizeof(char)));
	return(c->lista[index]);
}

Codigo getNextElemento(Conjunto c, int index)
{
	return(c->lista[index]);
}

Conjunto initConj(int tamConj)
{
	Conjunto c;

	c = malloc(sizeof(struct conj));

	if(c == NULL)
		return NULL;

	c->lista = (char**)malloc((long unsigned int)tamConj*sizeof(char*));

	if (c->lista == NULL)
	{
		free(c);
		return NULL;
	}

	c->tamanho = tamConj;
	c->npage = 0;

	return c;
}

Pagina initPage(int psize)
{
	Pagina p;

	p = malloc(sizeof(struct pagina));

	if(p == NULL)
		return NULL;

	p->page = (char**)malloc((long unsigned int)psize*sizeof(char*));

	if(p->page == NULL)
	{
		free(p);
		return NULL;
	}

	p->pagesize = psize;
	p->index = 0;

	return p;
}

void freePage(Pagina p)
{
	int i;
	if(p != NULL)
	{
		for(i = 0; i< p->pagesize; i++)
			if(p->page[i] != NULL)
				free(p->page[i]);
		free(p);
	}
}

Codigo getElemLista(Conjunto c, int index)
{
	return (c->lista[index]);
}

int getPaginaSize(Pagina p)
{
	return (p->pagesize);
}

int getPaginaIndex(Pagina p)
{
	return (p->index);
}

int getPaginaAtual(Conjunto c)
{
	return (c->npage);
}

int getElementosTotal(Conjunto c)
{
	return (c->tamanho);
}

int getPaginasTotal(Conjunto c)
{
	return(roundup(c->tamanho));
}

void setPaginaAtual(Conjunto c, int page)
{
	c->npage = page;
}

Codigo getNextElementoPage(Pagina p)
{
	if (p->index < 0 || p->index > p->pagesize)
		return NULL;

	return(p->page[p->index++]);
}

int roundup(int n)
{
	int t = n / MAXPAGE;

	if (n % MAXPAGE != 0)
		t += 1;
	return t;
}

Pagina getPaginaNext(Conjunto c)
{
	int paginasTotal, patual, tamrest, tamcopy, i, length;
	Pagina p;

	paginasTotal = roundup(c->tamanho);

	if (c->npage > paginasTotal || c->npage < 0)
	{
		return NULL;
	}

	patual = c->npage * MAXPAGE;

	if (patual < 0 || patual > c->tamanho)
		return NULL;

	c->npage++;

	tamrest = c->tamanho - patual;

	if (tamrest > MAXPAGE)
		tamcopy = MAXPAGE;
	else tamcopy = tamrest;

	p = initPage(tamcopy);

	for(i = 0; i < tamcopy; i++, patual++)
	{
		length = (int)strlen(c->lista[patual]) + 1;
		p->page[i] = malloc((long unsigned int)length*sizeof(char));
		strcpy(p->page[i], c->lista[patual]);
	}

	return p;
}

Pagina getPaginaAnt(Conjunto c)
{
	int paginasTotal, patual, tamrest, tamcopy, i, length;
	Pagina p;

	paginasTotal = roundup(c->tamanho);

	if (c->npage > paginasTotal || c->npage < 0)
		return NULL;

	patual = c->npage * MAXPAGE - ANTERIOR;

	if (patual < 0)
		return NULL; 

	c->npage--;

	tamrest = c->tamanho - patual;

	if (tamrest > MAXPAGE)
		tamcopy = MAXPAGE;
	else tamcopy = tamrest;


	p = initPage(tamcopy);

	for(i = 0; i < tamcopy; i++, patual++)
	{
		length = (int)strlen(c->lista[patual]) + 1;
		p->page[i] = malloc((long unsigned int)length*sizeof(char));
		strcpy(p->page[i], c->lista[patual]);
	}

	return p;
}

Pagina getPaginaP(Conjunto c, int pagina)
{
	int paginasTotal, patual, tamrest, tamcopy, i, length;
	Pagina p;

	paginasTotal = roundup(c->tamanho);

	if (c->npage > paginasTotal || c->npage < 0)
		return NULL;

	patual = pagina * MAXPAGE - MAXPAGE;

	if (patual < 0 || patual >= c->tamanho)
		return NULL;

	c->npage = pagina;

	tamrest = c->tamanho - patual;

	if (tamrest > MAXPAGE)
		tamcopy = MAXPAGE;
	else tamcopy = tamrest;

	p = initPage(tamcopy);

	for(i = 0; i < tamcopy; i++, patual++)
	{
		length = (int)strlen(c->lista[patual]) + 1;
		p->page[i] = malloc((long unsigned int)length*sizeof(char));
		strcpy(p->page[i], c->lista[patual]);
	}

	return p;
}

/**
* inOrderAux insere os elementos do Catálogo no Conjunto, ambos passados como argumentos.
*/
int inOrderAux(Catalogo a, Conjunto c, int index)
{
    if (a != NULL)
    {
        index = inOrderAux(getCatalogoEsq(a), c, index);

        c->lista[index] = malloc((strlen(getCatalogoCodigo(a))+1)*sizeof(char)); 
        strcpy(c->lista[index], getCatalogoCodigo(a));
        index++;

        index = inOrderAux(getCatalogoDir(a), c, index);
    }
    return index;
}

Conjunto inOrder(Catalogo a)
{
    Conjunto aux;
    aux = initConj(size(a));

    inOrderAux(a, aux, 0);
    return aux;
}

void freeConjunto(Conjunto c)
{
	int i;
	if (c->lista != NULL)
	{
		for(i = 0; i < c->tamanho; i++)
			if (c->lista[i] != NULL)
				free(c->lista[i]);
		free(c->lista);
	}

	if (c != NULL)
		free(c);
}

