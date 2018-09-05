#include <string.h>
#include <stdlib.h>
#include "vendas.h"
#include "catalogoff.h"
#include "prods.h"
#include "faturacao.h"
#include "catalogo.h"
#include "lista.h"

#define SIZEVETOR 10
#define LINHAS 3
#define COLUNAS 12
#define INICIAIS 26
#define DECISAO 36
#define MESES 12

/**
* A struct faturacao armazena uma matriz com número de linhas igual ao número de filiais e número de colunas igual ao número de meses
* com as informações da faturação, nomeadamente os códigos de produtos, os preços, as quantidades vendidas e o modo de venda.
*/
struct faturacao
{
	CatFatFil **matriz;
};

/**
* A struct totalF armazena um array com o total faturado.
*/
struct totalF
{
	double **totF;
};

/**
* A struct totalV armazena um array com o número total de vendas.
*/
struct totalV
{
	int **totV;
};

CatFatFil getPosFaturacao(Faturacao fat, int filial, int mes)
{
	return(fat->matriz[filial][mes]);
}

void setTotalFaturacao(TotalFat f, int filial, int modo, double fat)
{
	f->totF[filial][modo] = fat;
}

void setTotalVendas(TotalVendas v, int filial, int modo, int vend)
{
	v->totV[filial][modo] = vend;
}

double getTotalFaturacao(TotalFat f, int filial, int modo)
{
	return f->totF[filial][modo];
}

int getTotalVendas(TotalVendas v, int filial, int modo)
{
	return v->totV[filial][modo];
}

TotalFat initFat()
{
	TotalFat f;
	int i, j;

	f = malloc(sizeof(struct totalF));
	f->totF = malloc(LINHAS*sizeof(double*));

	for(i = 0; i < LINHAS; i++)
		f->totF[i] = malloc(COLUNAS*sizeof(double));

	for(i = 0; i < LINHAS; i++)
		for(j = 0; j < COLUNAS; j++)
		{ 
			f->totF[i][j] = 0;
		}

	return f;
}

void freeTotalFat(TotalFat tf)
{
	int i;
	if(tf != NULL)
	{
		for(i = 0; i < LINHAS; i++)
			if (tf->totF[i] != NULL)
				free(tf->totF[i]);
		free(tf);
	}
}

TotalVendas initVenda()
{
	TotalVendas v;
	int i, j;

	v = malloc(sizeof(struct totalV));
	v->totV = malloc(LINHAS*sizeof(int*));

	for(i = 0; i < LINHAS; i++)
		v->totV[i] = malloc(COLUNAS*sizeof(int));

	for(i = 0; i < LINHAS; i++)
		for(j = 0; j < COLUNAS; j++)
		{ 
			v->totV[i][j] = 0;
		}

	return v;
}

void freeTotalVendas(TotalVendas tv)
{
	int i;
	if (tv != NULL)
	{
		for(i = 0; i < LINHAS; i++)
			if(tv->totV[i] != NULL)
				free(tv->totV[i]);
		free(tv);
	}
}


Faturacao initFaturacao(int linhas, int colunas)
{
	int l, c;
	Faturacao fat;

	fat = malloc(sizeof(struct faturacao));
	fat->matriz = malloc((long unsigned int)3*sizeof(CatFatFil*));

	for(l = 0; l < linhas; l++)
		fat->matriz[l] = malloc((long unsigned int)12*sizeof(CatFatFil));

	for(l = 0; l < linhas; l++)
		for(c = 0; c < colunas; c++)
			fat->matriz[l][c] = NULL;

	return fat;
}

Faturacao insereFaturacao(Faturacao fat, Codigo produto, Vendas v, int filial, int mes)
{
	fat->matriz[filial-1][mes-1] = insertVendas(fat->matriz[filial-1][mes-1], produto, v, SIZEVETOR);

	return fat;
}

void freeFaturacao(Faturacao fat, int linhas, int colunas)
{
	int l, c;
	if (fat != NULL)
	{
		for(l = 0; l < linhas; l++)
			for(c = 0; c < colunas; c++)
				free(fat->matriz[l][c]);
	}
}

/**
* existeProdNaoVendido verifica se um produto passado como argumento foi ou não vendido.
*/

int existeProdNaoVendido(char* produto, Faturacao fat)
{
	int i, j, decisao;
	decisao = 0;
	for(i = 0; i < LINHAS; i++)
		for(j = 0; j < COLUNAS; j++)
			if(findChave(produto, getPosFaturacao(fat, i, j)) == 1)
				decisao++;
	if (decisao == DECISAO)
		return 1;
	return 0;
}

int quantosNaoVendidos(CatProds catpr, Faturacao fat)
{
	Conjunto prods;
	int i, l, counter;
	counter = 0;
	for(i = 0; i < INICIAIS; i++)
	{
		prods = initConj(size(getSubCatalogoProds(catpr, i)));
		prods = inOrder(getSubCatalogoProds(catpr, i));

		for(l = 0; l < getElementosTotal(prods); l++)
		{
			if(existeProdNaoVendido(getNextElemento(prods, l), fat) == 1)
			{
				counter++;
			}
		}
	}
	return counter;
}

/**
* procuraProdutos devolve os produtos não vendidos.
*/
Conjunto procuraProdutos(CatProds catpr, Faturacao fat, Conjunto c)
{
	Conjunto prods;
	int i, l, index;
	char* s;
	index = 0;
	for(i = 0; i < INICIAIS; i++)
	{
		prods = initConj(size(getSubCatalogoProds(catpr, i)));
		prods = inOrder(getSubCatalogoProds(catpr, i));

		for(l = 0; l < getElementosTotal(prods); l++)
			if (existeProdNaoVendido(getNextElemento(prods, l), fat) == 1)
			{
				s = criarString(c, index, getNextElemento(prods, l));
				strcpy(s, getNextElemento(prods, l));
				index++;
			}
	}
	return c;
}

Conjunto produtosNaoVendidos(CatProds catpr, Faturacao fat)
{
	Conjunto c;
	c = initConj(quantosNaoVendidos(catpr, fat));

	c = procuraProdutos(catpr, fat, c);

	return c;
}

/**
* existeProdutoAno verifica se um produto foi vendido numa filial passada como argumento durante o ano.
*/
int existeProdutoAno(char* produto, Faturacao fat, int filial)
{
	int l, res;

	res = 1;

	for(l = 0; l < MESES; l++)
		if (findChave(produto, fat->matriz[filial][l]) == 0)
			res = 0;

	return res;
}

/**
* quantosNaoVendidosFil devolve o número de produtos não vendidos numa filial passada como argumento duranto o ano.
*/
int quantosNaoVendidosFil(CatProds catpr, Faturacao fat, int filial)
{
	Conjunto prods;
	int i, j, counter;

	counter = 0;

	for(i = 0; i < INICIAIS; i++)
	{
		prods = initConj(size(getSubCatalogoProds(catpr, i)));
		prods = inOrder(getSubCatalogoProds(catpr, i));

		for(j = 0; j < getElementosTotal(prods); j++)
			if(existeProdutoAno(getNextElemento(prods, j), fat, filial-1) == 1)
					counter++;
	}
	return counter;
}

Conjunto produtosNaoVendidosFil(CatProds catpr, Faturacao fat, int filial)
{
	Conjunto prods, c;
	int i, j, index;
	char* s;

	index = 0;
	c = initConj(quantosNaoVendidosFil(catpr, fat, filial));

	for(i = 0; i < INICIAIS; i++)
	{
		prods = initConj(size(getSubCatalogoProds(catpr, i)));
		prods = inOrder(getSubCatalogoProds(catpr, i));
		for(j = 0; j < getElementosTotal(prods); j++)
			if(existeProdutoAno(getNextElemento(prods, j), fat, filial-1) == 1)
			{
				s = criarString(c, index, (getNextElemento(prods, j)));
				strcpy(s, getNextElemento(prods, j));
				index++;
			}
	}
	return c;
}

/**
* atualizaNP atualiza o total faturado e o número total de vendas de um produto segundo o modo de vendas N ou P.
*/
TotalFat atualizaNP(Dados d, TotalFat f, TotalVendas v, int filial)
{
	int i;


	for(i = 0; i < getCounterFF(d); i++)
	{

		if(strcmp(getModoFF(d,i),"N") == 0)
		{
			v->totV[filial][0]++;
			f->totF[filial][0] += getUnidadesFF(d,i) * getPrecoFF(d,i);
		}
		else
		{
			v->totV[filial][1]++;
			f->totF[filial][1] += getUnidadesFF(d,i) * getPrecoFF(d,i);
		}
	}
	return f;
}

/**
* fateVendasMPaux procura um produto num catálogo.
*/
TotalFat fateVendasMPaux(CatFatFil a, TotalFat f,TotalVendas v, Codigo p, int filial)
{
	if(a != NULL)
	{
		if(strcmp(p,getChave(a)) == 0)
			f = atualizaNP(getDados(a),f, v,filial);

		else if(strcmp(p, getChave(a)) < 0)
			fateVendasMPaux(getCatFatFilEsq(a), f, v, p, filial);

		else fateVendasMPaux(getCatFatFilDir(a), f, v, p, filial);
	}
	return f;
}

TotalFat fateVendasMesPro(Faturacao fa, Produto pr, int mes, int filial, TotalVendas v)
{
	TotalFat f;
	f = initFat();
	f = fateVendasMPaux(fa->matriz[filial][mes-1], f, v, devolveCodigoPr(pr), filial);
	return f;
}

/**
* atualizaFatV atualiza a estrutura TotalFat com o total faturado e o número total de vendas de um novo dado.
*/
TotalFat atualizaFatV(Dados d, TotalFat f, TotalVendas v, int filial)
{
	int i;
	for(i = 0; i < getCounterFF(d); i++)
	{
		v->totV[filial][0]++;
		f->totF[filial][0] += getUnidadesFF(d, i) * getPrecoFF(d, i);
	}
	return f;
}

/**
* atualizaFatVTotal faz a atualização total do total faturado e do número total de vendas de um catálogo.
*/
TotalFat atualizaFatVTotal(CatFatFil a, TotalFat f, TotalVendas v, int filial)
{
	if(a != NULL)
	{
			f = atualizaFatV(getDados(a), f, v, filial);
			atualizaFatVTotal(getCatFatFilEsq(a), f, v, filial);
			atualizaFatVTotal(getCatFatFilDir(a), f, v, filial);
	}
	return f;
}

TotalFat intervaloVeF(Faturacao f, int mesI, int mesS,  TotalVendas v)
{
	TotalFat fa;
	int i, filial;
	fa = initFat();
	
	for(i = mesI -1; i < mesS; i++)
	{
		for(filial = 0; filial < 3; filial++)
			fa =atualizaFatVTotal(f->matriz[filial][i], fa, v, filial);
	}
	return fa;
}

