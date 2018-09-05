#include <stdlib.h>
#include <string.h>
#include "catalogoff.h"
#include "clientes.h"
#include "filial.h"
#include "vendas.h"
#include "lista.h"
#include "prods.h"

#define MESES 12
#define FILIAIS 3
#define INICIAIS 26
#define DECISAOF 100

struct filial
{
	CatFatFil *filial;
};

typedef struct quant
{
	char *p;
	int q;
}*Quantidades;

struct  quantidade
{
	Quantidades *quantos;
	int size;
};

struct prodsfat
{
	char** prods;
	double* fat;
	int indice;
};

double calculaFat(double preco, int unidades)
{
	return(preco*unidades);
}

ProdsFat initProdsFat(int size)
{
	int i;
	ProdsFat pf;

	pf = malloc(sizeof(struct prodsfat));
	pf->prods = malloc((long unsigned int)size*sizeof(char*));
	pf->fat = malloc((long unsigned int)size*sizeof(double));
	for(i = 0; i < size; i++)
		pf->fat[i] = 0.0;

	pf->indice = 0;

	return pf;
}

void freeProdsFat(ProdsFat pf)
{
	int i;
	if (pf != NULL)
	{
		for(i = 0; i < pf->indice; i++)
			if(pf->prods[i] != NULL)
				free(pf->prods[i]);
		free(pf->fat);
		free(pf->prods);
		free(pf);
	}
}

void incIndice(ProdsFat pf)
{
	pf->indice++;
}

void setFaturacaoProdsFat(ProdsFat pf, double fat)
{
	pf->fat[pf->indice] = fat;
}

void setProdutoProdsFat(ProdsFat pf, Codigo prod)
{
	pf->prods[pf->indice] = malloc((strlen(prod)+1)*sizeof(char));
	strcpy(pf->prods[pf->indice], prod);
}

double getFaturacaoProdsFat(ProdsFat pf, int i)
{
	return(pf->fat[i]);
}

Codigo getProdutoProdsFat(ProdsFat pf, int i)
{
	return(pf->prods[i]);
}

int getIndice(ProdsFat pf)
{
	return pf->indice;
}

/**
* Função que incrementa uma dada faturação à que estava na estrutura ProdsFat no índice dado.
*/
void somaFaturacaoProdsFat(ProdsFat pf, double fat, int index)
{
	pf->fat[index] += fat;
}

Filial initFilial()
{
	int i;
	Filial fil;

	fil = malloc(sizeof(struct filial));
	if (fil == NULL)
		return NULL;

	fil->filial = malloc((long unsigned int)12*sizeof(CatFatFil));

	for(i = 0; i < MESES; i++)
	{
		fil->filial[i] = NULL;
	}

	return fil;
}

void freeFilial(Filial fil)
{
	int i;
	if(fil != NULL)
	{
		for(i = 0; i < MESES; i++)
				free(fil->filial[i]);
	}
	free(fil);
}

Filial insereFilial(Filial fil, Codigo cl, Vendas v, int mes)
{
	fil->filial[mes-1] = insertVendas(fil->filial[mes-1], cl, v, DECISAOF);
	return fil;
}

/**
* Função que verifica  se um cliente fez compras numa filial. 
*/
int existeClienteTodasFiliais(Codigo cliente, Filial fil)
{
	int acc, j;
	acc = 0;
	
	for(j = 0; j < MESES; j++)
	{
		if (findChave(cliente, fil->filial[j]) == 0)
			acc++;
	}
	if (acc > 0)
		return 0;

	return 1;
}

/**
* Função que devolve o número de clientes que fizeram compras numa dada filial.
*/
int quantosClientesUmaFilial(CatClientes catcl, Filial f)
{
	Conjunto clientes;
	int i, j, counter;
	counter = 0;

	for(i = 0; i < INICIAIS; i++)
	{
		clientes = initConj(size(getSubCatalogo(catcl, i)));
		clientes = inOrder(getSubCatalogo(catcl, i));
		for(j = 0; j < getElementosTotal(clientes); j++)
			if(existeClienteTodasFiliais(getNextElemento(clientes, j), f) == 0)
				counter++;
	}

	return counter;
}

/**
* Função que devolve o número de clientes que fizeram compras em todas as filiais.
*/
int quantosClientesTodasFiliais(CatClientes catcl, Filial f1, Filial f2, Filial f3)
{
	Conjunto clientes;
	int i, j, counter;
	counter = 0;

	for(i = 0; i < INICIAIS; i++)
	{
		clientes = initConj(size(getSubCatalogo(catcl, i)));
		clientes = inOrder(getSubCatalogo(catcl, i));
		for(j = 0; j < getElementosTotal(clientes); j++)
			if((existeClienteTodasFiliais(getNextElemento(clientes, j), f1) == 0) 
				&& (existeClienteTodasFiliais(getNextElemento(clientes, j), f2) == 0) 
				&& (existeClienteTodasFiliais(getNextElemento(clientes, j), f3) == 0))
				counter++;
	}

	return counter;
}

int quantosClientesNenhumaCompra(CatClientes catcl, Filial f1, Filial f2, Filial f3)
{
	Conjunto clientes;
	int i, j, counter;
	counter = 0;

	for(i = 0; i < INICIAIS; i++)
	{
		clientes = initConj(size(getSubCatalogo(catcl, i)));
		clientes = inOrder(getSubCatalogo(catcl, i));
		for(j = 0; j < getElementosTotal(clientes); j++)
			if((existeClienteTodasFiliais(getNextElemento(clientes, j), f1) == 1) 
				|| (existeClienteTodasFiliais(getNextElemento(clientes, j), f2) == 1) 
				|| (existeClienteTodasFiliais(getNextElemento(clientes, j), f3) == 1))
				counter++;
	}

	return counter;
}

/**
* Função que preenche um conjunto com todos os clientes que fizeram compras em qualquer das filiais.
*/
Conjunto procuraClientesTodasFiliais(CatClientes catcl, Filial f1, Filial f2, Filial f3, Conjunto c)
{
	Conjunto clientes; 
	int i, j, index;
	char* s;

	index = 0;

	for(i = 0; i < INICIAIS; i++)
	{
		clientes = initConj(size(getSubCatalogo(catcl, i)));
		clientes = inOrder(getSubCatalogo(catcl, i));

		for(j = 0; j < getElementosTotal(clientes); j++)
		{
			if((existeClienteTodasFiliais(getNextElemento(clientes, j), f1) == 0) 
				&& (existeClienteTodasFiliais(getNextElemento(clientes, j), f2) == 0) 
				&& (existeClienteTodasFiliais(getNextElemento(clientes, j), f3) == 0))
			{
				s = criarString(c, index, getNextElemento(clientes, j));
				strcpy(s, getNextElemento(clientes, j));
				index++;
			}
		}	
	}
	return c;
}

Conjunto clientesTodasFiliais(CatClientes catcl, Filial f1, Filial f2, Filial f3)
{
	Conjunto c;
	c = initConj(quantosClientesTodasFiliais(catcl, f1, f2, f3));

	c =procuraClientesTodasFiliais(catcl, f1, f2, f3, c);

	return c;
}

int procuraClienteProdsTotal(Codigo cliente, Filial f, int mes)
{
	Dados d;
	int counter, j;
	counter = 0;

	d = getDadosCliente(cliente, f->filial[mes]);
	if(d != NULL)
	{
		for(j = 0; j < getCounterFF(d); j++)
			counter += getUnidadesFF(d, j);
	}
	return counter;
}

/**
* quantosProdutosMes devolve o número de vezes que um produto foi comprado num mês.
*/
int quantosProdutosMes(char* produto, CatFatFil a, char* modo, int counter)
{
	int i;
	if(a != NULL)
	{
		counter = quantosProdutosMes(produto, getCatFatFilEsq(a), modo, counter);

		for(i = 0; i < getCounterFF(getDados(a)); i++)
			if(strcmp(produto, getProdFF(getDados(a),i)) == 0 && strcmp(modo, getModoFF(getDados(a),i)) == 0)
				counter++;

		counter = quantosProdutosMes(produto, getCatFatFilDir(a), modo, counter);
	}
	return counter;
}

/**
* Função que conta quantos produtos há em determinada filial.
*/
int quantosProdutosFilial(Codigo produto, Filial f, char* modo)
{
	int i, counter;
	counter = 0;

	for(i = 0; i < MESES; i++)
		counter += quantosProdutosMes(produto, f->filial[i], modo, 0);

	return counter;
}

/**
* recolheClientes insere num Conjunto os clientes que compraram um produto num modo de venda passados como argumentos.
*/
int recolheClientes(char* produto, CatFatFil a, char* modo, Conjunto c, int index)
{
	int i;
	char* s;
	if (a != NULL)
	{
		index = recolheClientes(produto, getCatFatFilEsq(a), modo, c, index);

		for(i = 0; i < getCounterFF(getDados(a)); i++)
		{
			if(strcmp(produto, getProdFF(getDados(a),i)) == 0 && strcmp(modo, getModoFF(getDados(a),i)) == 0)
			{
				s = criarString(c, index, getChave(a));
				strcpy(s, getChave(a));
				index++;
			}
		}

		index = recolheClientes(produto, getCatFatFilDir(a), modo, c, index);
	}
	return index;
}

Conjunto procuraProdutoFilial(Codigo produto, Filial f, char* modo)
{
	Conjunto c;
	int index, i;
	c = initConj(quantosProdutosFilial(produto, f, modo));
	index = 0;

	for(i = 0; i < MESES; i++)
	{
		index = recolheClientes(produto, f->filial[i], modo, c, index);
	}

	return c;
}

/**
* Função que verifica se existe um produto na estrutura ProdsFat.
*/
int existeProdutoProdsFat(Codigo produto, ProdsFat pf)
{
	int i;
	for (i = 0; i < getIndice(pf); i++)
		if(strcmp(produto, getProdutoProdsFat(pf, i)) == 0)
			return i;
	return -1;
}

/**
* Função que preenche ou atualiza a estrutura ProdsFat.
*/
ProdsFat preencheProdsMes(Codigo cliente, CatFatFil a, ProdsFat pf)
{
	int i, ind;
	if (a != NULL)
	{
		if (strcmp(cliente, getChave(a)) < 0)
			pf = preencheProdsMes(cliente, getCatFatFilEsq(a), pf);

		if(strcmp(cliente, getChave(a)) == 0)
		{
			for(i = 0; i < getCounterFF(getDados(a)); i++)
			{
				if((ind = existeProdutoProdsFat(getProdFF(getDados(a),i), pf)) != -1)
				{
					somaFaturacaoProdsFat(pf,calculaFat(getPrecoFF(getDados(a),i), getUnidadesFF(getDados(a),i)), ind);
				}
				else
				{
					setProdutoProdsFat(pf, getProdFF(getDados(a),i));
					setFaturacaoProdsFat(pf, calculaFat(getPrecoFF(getDados(a),i), getUnidadesFF(getDados(a),i)));
					incIndice(pf);
				}
			}
		}
		if (strcmp(cliente, getChave(a)) > 0)
			pf = preencheProdsMes(cliente, getCatFatFilDir(a), pf);
	}
	return pf;
}

ProdsFat preencheProdsFat(Codigo cliente, Filial f, ProdsFat pf)
{
	int i;

	for(i = 0; i < MESES; i++)
		pf = preencheProdsMes(cliente, f->filial[i], pf);

	return pf;
}

/**
* Função que preenche ou atualiza a faturação e o conjunto de produtos na estrutura ProdsFat.
*/
ProdsFat preencheProdsTotMes(CatFatFil a, ProdsFat pf)
{
	int i, ind;
	if(a != NULL)
	{
		pf = preencheProdsTotMes(getCatFatFilEsq(a), pf);

		for(i = 0; i < getCounterFF(getDados(a)); i++)
		{
			if((ind = existeProdutoProdsFat(getProdFF(getDados(a),i), pf)) != -1)
				{
					somaFaturacaoProdsFat(pf, getUnidadesFF(getDados(a),i), ind);
				}
				else
				{
					setProdutoProdsFat(pf, getProdFF(getDados(a),i));
					setFaturacaoProdsFat(pf, getUnidadesFF(getDados(a),i));
					incIndice(pf);
				}	
		}

		pf = preencheProdsTotMes(getCatFatFilDir(a), pf);
	}

	return pf;
}

ProdsFat preencheProdsTotFat(Filial f1, Filial f2, Filial f3, ProdsFat pf)
{
	int i;

	for(i = 0; i < MESES; i++)
		pf = preencheProdsTotMes(f1->filial[i], pf);
	for(i = 0; i < MESES; i++)
		pf = preencheProdsTotMes(f2->filial[i], pf);
	for(i = 0; i < MESES; i++)
		pf = preencheProdsTotMes(f3->filial[i], pf);

	return pf;
}

/**
* Função que verifica se existe um elemento num array de doubles.
*/
int existeMaxes(double max, double* maxes, int conteudo)
{
	int i;
	for(i = 0; i < conteudo; i++)
		if (max == maxes[i])
			return 0;
	return 1;
}

/**
* Função que verifica se existe um produto no conjunto dado.
*/
int existeTop(Codigo produto, Conjunto c, int conteudo)
{
	int i;
	for(i = 0; i < conteudo; i++)
		if(strcmp(produto, getNextElemento(c, i)) == 0)
			return 0;
	return 1;
}

Conjunto topNProdutos(ProdsFat pf, int N)
{
	int i, j, indice, conteudo;
	char *s;
	double max, maxes[1000];
	Conjunto c;

	conteudo = 0;
	c = initConj(N);
	for(i = 0; i < N; i++)
		maxes[i] = -1.0;

	for(i = 0; i < N; i++)
	{
		indice = -1;
		max = -1.0;
		for(j = 0; j < getIndice(pf); j++)
		{
			if (max < getFaturacaoProdsFat(pf, j) && existeMaxes(getFaturacaoProdsFat(pf, j), maxes, conteudo) == 1)
				{
					max = getFaturacaoProdsFat(pf, j);
					indice = j;
				}
			if (max < getFaturacaoProdsFat(pf, j) && existeMaxes(getFaturacaoProdsFat(pf, j), maxes, conteudo) == 0 
			&& existeTop(getProdutoProdsFat(pf, j), c, conteudo) == 1)
				{
					max = getFaturacaoProdsFat(pf, j);
					indice = j;
				}
		}
		s = criarString(c, i, getProdutoProdsFat(pf, indice));
		strcpy(s, getProdutoProdsFat(pf, indice));

		maxes[i] = max;

		conteudo++;
	}

	return c;
}

Conjunto top3Produtos(ProdsFat pf)
{
	int i, j, indice, conteudo;
	char *s;
	double max, maxes[FILIAIS];
	Conjunto c;

	conteudo = 0;
	c = initConj(FILIAIS);
	for(i = 0; i < FILIAIS; i++)
		maxes[i] = -1.0;

	for(i = 0; i < FILIAIS; i++)
	{
		indice = -1;
		max = -1.0;
		for(j = 0; j < getIndice(pf); j++)
		{
			if (max < getFaturacaoProdsFat(pf, j) && existeMaxes(getFaturacaoProdsFat(pf, j), maxes, conteudo) == 1)
				{
					max = getFaturacaoProdsFat(pf, j);
					indice = j;
				}
			if (max < getFaturacaoProdsFat(pf, j) && existeMaxes(getFaturacaoProdsFat(pf, j), maxes, conteudo) == 0 
			&& existeTop(getProdutoProdsFat(pf, j), c, conteudo) == 1)
				{
					max = getFaturacaoProdsFat(pf, j);
					indice = j;
				}
		}
		s = criarString(c, i, getProdutoProdsFat(pf, indice));
		strcpy(s, getProdutoProdsFat(pf, indice));

		maxes[i] = max;

		conteudo++;
	}

	return c;
}

/**
* Função que inicializa a struct QuantProds.	
*/
QuantProds initQuantidades(int tam)
{
	QuantProds q;
	int i;

	q =  malloc(sizeof(struct quantidade));
	if (q == NULL)
		return NULL;

	q->size = 0;

	q->quantos = malloc((long unsigned int)tam*sizeof(struct quant));
	for(i = 0; i < tam; i++)
	{
		q->quantos[i] = malloc(sizeof(struct quant));
		q->quantos[i]->p = NULL;
		q->quantos[i]->q = 0;
	}
	
	return q;
}

/**
* Função que troca dois elementos do array quantos da estrutura QuantProds.
*/
QuantProds swapQ(QuantProds qp, int i, int f)
{
	char *paux;
	int qaux, compr;

	compr = (int)strlen(qp->quantos[i]->p) + 2;
	paux = malloc((long unsigned int)compr*sizeof(char));

	qaux = qp->quantos[i]->q;
	strcpy(paux, qp->quantos[i]->p);

	qp->quantos[i]->q = qp->quantos[f]->q;
	strcpy(qp->quantos[i]->p, qp->quantos[f]->p);

	qp->quantos[f]->q = qaux;
	strcpy(qp->quantos[f]->p, paux);

	free(paux);
	return qp;
}

/**
* Função que ordena o array quantos da estrutura QuantProds.
*/
QuantProds sorta(QuantProds qp)
{
	int i, j;
	for(i = 0; i < qp->size ; i++)
		for(j = i+1; j < qp->size; j++)
		{
			if(qp->quantos[i]->q < qp->quantos[j]->q)
				swapQ(qp, i, j);
		}
	return qp;
}

/**
* Função que verifica se existe um produto na estrutura QuantProds.
*/
int existsP(Produto p, QuantProds qp)
{
	int i;

	for(i = 0; i < qp->size; i++)
		if(strcmp(devolveCodigoPr(p), qp->quantos[i]->p) == 0)
			return i;
	return -1;
}

/**
* Função que insere a quantidade e o produto à estrutura QuantProds. 	
*/
QuantProds insereQuantidadeProduto(Produto pr, int quant, QuantProds qp)
{
	char *p;
	int compr;
	p = devolveCodigoPr(pr);
	compr = (int)strlen(p) + 2;
	qp->quantos[qp->size]->p = malloc((long unsigned int)compr*sizeof(char));
	strcpy(qp->quantos[qp->size]->p, p);
	qp->quantos[qp->size]->q = quant;
	qp->size++;
	return qp;
}

/**
* Função que preenche a estrutura QuantProds com todos os produtos e quantidades dentro da estrutura Dados.	
*/
QuantProds preencheQuant(Dados d, QuantProds qp)
{
	int i, indice;
	for(i = 0; i < getCounterFF(d); i++)
	{
		indice = existsP(convProduto(getProdFF(d,i)), qp);
		if(indice == -1)
			qp = insereQuantidadeProduto(convProduto(getProdFF(d,i)), getUnidadesFF(d,i), qp);
		else qp->quantos[indice]->q += getUnidadesFF(d,i);
	}
	return qp;
}

/**
* Função que procura um cliente num catálogo ... e prosegue para a actualização das informações deste na estrutura QuantProds.
*/
QuantProds procuraCli(CatFatFil a, Cliente cl, QuantProds qp)
{
	if (a != NULL)
	{
		if(strcmp(getCodigoCliente(cl), getChave(a)) == 0)
			qp = preencheQuant(getDados(a), qp);

		else if (strcmp(getCodigoCliente(cl), getChave(a)) < 0)
			procuraCli(getCatFatFilEsq(a), cl, qp);


			else 
				procuraCli(getCatFatFilDir(a), cl, qp);
	}
	return qp;
}

Conjunto produtosMaisCompradosCli(Filial f1, Filial f2, Filial f3, Cliente cl, int mes, int tamP)
{
	Conjunto c;
	QuantProds qp;
	char *s;
	int i;

	qp = initQuantidades(tamP);

	qp = procuraCli(f1->filial[mes-1], cl, qp);
	qp = procuraCli(f2->filial[mes-1], cl, qp);
	qp = procuraCli(f3->filial[mes-1], cl, qp);

	qp = sorta(qp);

	c = initConj(qp->size);

	for(i = 0; i < qp->size; i++)
	{
		s = criarString(c, i, qp->quantos[i]->p);
		strcpy(s, qp->quantos[i]->p);
	}
	return c;
}
