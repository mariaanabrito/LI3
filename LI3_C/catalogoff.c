#include <stdlib.h>
#include <string.h>
#include "catalogo.h"
#include "catalogoff.h"
#include "vendas.h"
#include "lista.h"

#define DECISAOF 100

/**
* A estrutura struct catff contém a informação relativa à faturação ou às filiais, nomeadamente a chave para aceder aos dados, os dados
* dessa chave, a altura e tamanho do nodo, bem como os nodos adjacentes.
*/
struct catff
{
	char* chave;
	Dados d;
	int height;
	int size;
	struct catff *left, *right;
};

/**
* A struct dados contém a informação relativa a uma dada chave, nomeadamente os preços, as unidades compradas, os modo de venda e os
* produtos, caso se trate de um tipo Filial. Ainda armazena, também, o tamanho dos arrays, assim como o índice atual. 
*/
struct dados
{
	double *preco;
	int *unidades;
	char **modo;
	char **prods;
	int counter;
	int size;
};


double getPrecoFF(Dados d, int index)
{
	return(d->preco[index]);
}

int getUnidadesFF(Dados d, int index)
{
	return(d->unidades[index]);
}

char* getModoFF(Dados d, int index)
{
	return(d->modo[index]);
}

char* getProdFF(Dados d, int index)
{
	return(d->prods[index]);
}

int getCounterFF(Dados d)
{
	return(d->counter);
}

int getSizeFF(Dados d)
{
	return(d->size);
}

void setPrecoFF(Dados d, double preco, int index)
{
	d->preco[index] = preco;
}

void setUnidadesFF(Dados d, int unidades, int index)
{
	d->unidades[index] = unidades;
}

void setModoFF(Dados d, char* modo, int index)
{
	int length;
	length = (int)strlen(modo) + 1;
	d->modo[index] = (char*)malloc((long unsigned int)length*sizeof(char));
	strcpy(d->modo[index], modo);
}

void setProdFF(Dados d, char* produto, int index)
{
	int length;
	length = (int)strlen(produto) + 1;
	d->prods[index] = (char*)malloc((long unsigned int)length*sizeof(char));
	strcpy(d->prods[index], produto);
}

CatFatFil getCatFatFilEsq(CatFatFil a)
{
	return (a->left);
}

CatFatFil getCatFatFilDir(CatFatFil a)
{
	return (a->right);
}

Codigo getChave(CatFatFil a)
{
	return(a->chave);
}

Dados getDados(CatFatFil a)
{
	return a->d;
}

Dados initDadosFat(int size)
{
	Dados d;
	d = malloc(sizeof(struct dados));
	if (d == NULL)
		return NULL;
	d->modo = (char**)malloc((long unsigned int)size*sizeof(char*));
	if (d->modo == NULL)
	{
		free(d);
		return NULL;
	}
	d->unidades = (int*)malloc((long unsigned int)size*sizeof(int));
	if (d->unidades == NULL)
	{
		free(d->modo);
		free(d);
		return NULL;
	}
	d->preco = (double*)malloc((long unsigned int)size*sizeof(double));
	if (d->preco == NULL)
	{
		free(d->unidades);
		free(d->modo);
		free(d);
		return NULL;
	}
	d->prods = NULL;
	d->counter = 0;
	d->size = size;
	return d;
}

Dados initDadosFil(int size)
{
	Dados d;
	d = malloc(sizeof(struct dados));
	if (d == NULL)
		return NULL;
	d->modo = (char**)malloc((long unsigned int)size*sizeof(char*));
	if (d->modo == NULL)
	{
		free(d);
		return NULL;
	}
	d->unidades = (int*)malloc((long unsigned int)size*sizeof(int));
	if (d->unidades == NULL)
	{
		free(d->modo);
		free(d);
		return NULL;
	}
	d->preco = (double*)malloc((long unsigned int)size*sizeof(double));
	if (d->preco == NULL)
	{
		free(d->unidades);
		free(d->modo);
		free(d);
		return NULL;
	}
	d->prods = (char**)malloc((long unsigned int)size*sizeof(char*));
	if (d->prods == NULL)
	{
		free(d->preco);
		free(d->unidades);
		free(d->modo);
		free(d);
		return NULL;
	}
	d->counter = 0;
	d->size = size;
	return d;
}

int isEmpty(CatFatFil t)
{
	if (t == NULL)
		return 1;
	return 0;
}

int findChave(char* chave, CatFatFil t)
{
    if(t == NULL)
        return 1;
    if(strcmp(chave, t->chave) < 0)
        return findChave(chave, t->left);
    else if(strcmp(chave, t->chave) > 0)
        return findChave(chave, t->right);
    	else
        	return 0;
}

Dados getDadosCliente(char* cliente, CatFatFil t)
{
	if (t == NULL)
		return NULL;
	if(strcmp(cliente, t->chave) < 0)
		return getDadosCliente(cliente, t->left);
	else if(strcmp(cliente, t->chave) > 0)
		return getDadosCliente(cliente, t->right);
	else
		return t->d;
}

int heightFatFil(CatFatFil n)
{
    if( n == NULL )
        return -1;
    else
        return n->height;
}

int sizeFatFil(CatFatFil n)
{
    if (n == NULL)
        return 0;
    else
        return n->size;
}

/**
* rotacao_simples_esqFatFil faz uma rotação simples com a esquerda.
*/
CatFatFil rotacao_simples_esqFatFil(CatFatFil k2)
{
	CatFatFil k1 = NULL;

    k1 = k2->left;
    k2->left = k1->right;
    k1->right = k2;

    k2->height = max(heightFatFil(k2->left), heightFatFil(k2->right)) + 1;
    k1->height = max(heightFatFil(k1->left), k2->height) + 1;
    k2->size = sizeFatFil(k2->left) + sizeFatFil(k2->right) + 1;
    k1->size = sizeFatFil(k1->left) + sizeFatFil(k1->right) + 1;

    return k1;
}

/**
* rotacao_simples_dirFatFil faz uma rotação simpes com a direita.
*/
CatFatFil rotacao_simples_dirFatFil(CatFatFil k1)
{
	CatFatFil k2;

	k2 = k1->right;
    k1->right = k2->left;
    k2->left = k1;

    k1->height = max(heightFatFil(k1->left), heightFatFil(k1->right)) + 1;
    k2->height = max(heightFatFil(k2->right), k1->height) + 1;
    k1->size = sizeFatFil(k1->left) + sizeFatFil(k1->right) + 1;
    k2->size = sizeFatFil(k2->left) + sizeFatFil(k2->right) + 1;

    return k2;
}

/**
* rotacao_dupla_esqFatFil faz uma rotação dupla com a esquerda.
*/
CatFatFil rotacao_dupla_esqFatFil(CatFatFil k3)
{
	k3->left = rotacao_simples_dirFatFil(k3->left);
	return rotacao_simples_esqFatFil(k3);
}

/**
* rotacao_dupla_dirFatFil faz uma rotação dupla com a direita.
*/
CatFatFil rotacao_dupla_dirFatFil(CatFatFil k1)
{
	k1->right = rotacao_simples_esqFatFil(k1->right);
	k1 = rotacao_simples_dirFatFil(k1);
	return k1;
}

CatFatFil insertVendas(CatFatFil t, char* chave, Vendas v, int decisao)
{
	int length;
	if(t == NULL)
	{
		t = malloc(sizeof(struct catff));
		if (t == NULL)
			return NULL;
		else
		{
			length = (int)strlen(chave) + 1;
			t->chave = malloc((long unsigned int)length*sizeof(char));
			strcpy(t->chave, chave);
			if (decisao == DECISAOF)
				t->d = initDadosFil(decisao);
			else t->d = initDadosFat(decisao);
			if (t->d == NULL)
			{
				free(t->chave);
				free(t);
				return NULL;
			}
			setModoFF(t->d, getModo(v), t->d->counter);
			if (getModoFF(t->d, t->d->counter) == NULL)
			{
				free(t->d);
				free(t->chave);
				free(t);
				return NULL;
			}
			if (decisao == 100)
			{
				setProdFF(t->d, getProd(v), t->d->counter);
				if (getProdFF(t->d, t->d->counter) == NULL)
				{
					free(t->d);
					free(t->chave);
					free(t);
					return NULL;
				}
			}
			setPrecoFF(t->d, getPreco(v), t->d->counter);
			setUnidadesFF(t->d, getQuantos(v), t->d->counter);
			t->height = 0;
			t->size = 1;
			t->d->counter++;
			if (t->d->counter == t->d->size)
			{
				t->d->modo = realloc(t->d->modo, (long unsigned int)decisao);
				t->d->preco = realloc(t->d->preco, (long unsigned int)decisao);
				t->d->unidades = realloc(t->d->unidades, (long unsigned int)decisao);
				if (decisao == DECISAOF)
					t->d->prods = realloc(t->d->prods, (long unsigned int)decisao);
				t->size += decisao;
			}
			t->left = t->right = NULL;
		}
	}
	else if(strcmp(chave, t->chave) < 0)
	{
		t->left = insertVendas(t->left, chave, v, decisao);

		if (heightFatFil(t->left) - heightFatFil(t->right) == 2)
		{
			if(strcmp(chave, t->left->chave) < 0)
				t = rotacao_simples_esqFatFil(t);
			else
				t = rotacao_dupla_esqFatFil(t);
		}
	}
	else if(strcmp(chave, t->chave) > 0)
	{
		t->right = insertVendas(t->right, chave, v, decisao);

		if((heightFatFil(t->right) - heightFatFil(t->left)) == 2)
		{
			if(strcmp(chave, t->right->chave) > 0)
				t = rotacao_simples_dirFatFil(t);
			else
				t = rotacao_dupla_dirFatFil(t);
		}
	}
	else
	{
		setModoFF(t->d, getModo(v), t->d->counter);
		if (getModoFF(t->d, t->d->counter) == NULL)
		{
			free(t->d);
			free(t->chave);
			free(t);
			return NULL;
		}
		if (decisao == DECISAOF)
		{
			setProdFF(t->d, getProd(v), t->d->counter);
			if (getProdFF(t->d, t->d->counter) == NULL)
			{
				free(t->d);
				free(t->chave);
				free(t);
				return NULL;
			}
		}
		setPrecoFF(t->d, getPreco(v), t->d->counter);
		setUnidadesFF(t->d, getQuantos(v), t->d->counter);
		t->d->counter++;
		if (t->d->counter == t->d->size)
		{
			t->d->modo = realloc(t->d->modo, (long unsigned int)decisao);
			t->d->preco = realloc(t->d->preco, (long unsigned int)decisao);
			t->d->unidades = realloc(t->d->unidades, (long unsigned int)decisao);
			if (decisao == DECISAOF)
				t->d->prods = realloc(t->d->prods, (long unsigned int)decisao);
			t->size += decisao;
		}
	}

	t->height = max(heightFatFil(t->left), heightFatFil(t->right)) + 1;
	t->size = sizeFatFil(t->left) + sizeFatFil(t->right) + 1;

	return t;
}

void disposeVendas(CatFatFil t)
{
	if (t != NULL)
	{
		disposeVendas(t->left);
		disposeVendas(t->right);
		free(t);
	}
}


