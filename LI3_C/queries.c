#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "catalogo.h"
#include "prods.h"
#include "clientes.h"
#include "lista.h"
#include "catalogoff.h"
#include "faturacao.h"
#include "vendas.h"
#include "filial.h"
#include <time.h>

#define MAXCP 64
#define MAXV 128
#define MESES 12
#define FILIAIS 3
#define BUFF_TAM10 10
#define BUFF_TAM7 7
#define BUFF_TAM5 5
#define BUFF_TAM4 4

CatClientes carregarFicheiroClientes(FILE *fc)
{
	CatClientes catcl;
	Cliente cl;
	char *linha, buff[MAXCP];

	catcl = initCatClientes();

	while(fgets(buff, MAXCP, fc) != NULL)
	{
		linha = strtok(buff, "\r\n");
		cl = convCliente(linha);
		catcl = insereCliente(catcl, cl);
	}	

	printf("Foi lido o ficheiro Clientes.\n");
	printf("O número total de clientes é %d.\n", totalClientes(catcl));
	printf("\n");

	return catcl;
}

CatProds carregarFicheiroProdutos(FILE *fp)
{
	CatProds catpr;
	Produto pr;
	char *linha, buff[MAXCP];

	catpr = initCatProds();

	while(fgets(buff, MAXCP, fp) != NULL)
	{
		linha = strtok(buff, "\r\n");
		pr = convProduto(linha);
		catpr = insereProduto(catpr, pr);
	}	

	printf("Foi lido o ficheiro Produtos.\n");
	printf("O número total de produtos é %d.\n", totalProdutos(catpr));
	printf("\n");

	return catpr;
}

int carregarFicheiroVendas(FILE *fv, CatClientes catcl, CatProds catpr, int numfile, Faturacao fat, Filial f1, Filial f2, Filial f3)
{
	Vendas v;
	Cliente cl;
	Produto pr;
	int countvalidas, countvtotal, narg, yesprint, n;
	double d;
	char *linha, buffv[MAXV];

	countvalidas = countvtotal = narg = 0;	

	while(fgets(buffv, MAXV, fv) != NULL)
	{
		yesprint = 1;
		v = initVendas();

		linha = strtok(buffv, "\r\n");
		countvtotal++;

		setProd(v, strtok(linha, " "));
		pr = convProduto(getProd(v));
		if(existeProduto(catpr, pr) == 1)
			yesprint = 0;

		d = atof(strtok(NULL, " "));
		setPreco(v, d);
		if ((getPreco(v) < 0.00) || (getPreco(v) > 999.990))
			yesprint = 0;

		n = atoi(strtok(NULL, " "));
		setQuantos(v, n);
		if ((getQuantos(v) < 1) || (getQuantos(v) > 200))
			yesprint = 0;

		setModo(v, strtok(NULL, " "));
		if ((strcmp(getModo(v), "P") != 0) && (strcmp(getModo(v), "N") != 0))
			yesprint = 0;

		setCliente(v, strtok(NULL, " "));
		cl = convCliente(getCliente(v));
		if(existeCliente(catcl, cl) == 1)
			yesprint = 0;

		n = atoi(strtok(NULL, " "));
		setMes(v, n);
		if ((getMes(v) < 1) || (getMes(v) > MESES))
			yesprint = 0;

		n = atoi(strtok(NULL, " "));
		setFilial(v, n);
		if ((getFilial(v) < 0) || (getFilial(v) > FILIAIS))
			yesprint = 0;
		if (yesprint)
		{
			countvalidas++;
			fat = insereFaturacao(fat, getProd(v), v, getFilial(v), getMes(v));
			if (getFilial(v) == 1)
				f1 = insereFilial(f1, getCliente(v), v, getMes(v));
			if (getFilial(v) == 2)
				f2 = insereFilial(f2, getCliente(v), v, getMes(v));
			if (getFilial(v) == 3)
				f3 = insereFilial(f3, getCliente(v), v, getMes(v));
		}

		freeVendas(v);
	}

	printf("Foi lido o ficheiro Vendas_%dM.\n", numfile);
	printf("O número total de linhas lidas é %d\n", countvtotal);
	printf("O número total de vendas válidas é %d\n", countvalidas);
	printf("\n");
	printf("\n");
	return countvalidas;
}

int query2(CatProds catpr)
{
	Conjunto conjProds;
	Pagina p;
	char *s, buff[BUFF_TAM7], *letra, car;
	int i, h, nulo, sinal, npagina;
	letra = (char*)malloc(sizeof(char));
	sinal = nulo = 0;

	printf("Insira a letra que deseja para ver os produtos iniciados por ela.\n");

	if(fgets(buff, BUFF_TAM4, stdin) != NULL)
		sscanf(buff, "%s", letra);
	h = hash(letra);
	
	conjProds = initConj(size(getSubCatalogoProds(catpr, h)));
	conjProds = inOrder(getSubCatalogoProds(catpr, h));

	printf("Produtos que começam com a letra %s:\n", letra);
	printf("\n");

	p = getPaginaNext(conjProds);

	for(i = 0; i < getPaginaSize(p); i++)
	{
		s = getNextElementoPage(p);
		printf("%s\n", s);
	}
	printf("\n");
	printf("Página 1 de %d\n", getPaginasTotal(conjProds));
	printf("\n");

	printf("       MENU:\n");
	printf("\n");
	printf("a: Ir para a página anterior.\n");
	printf("s: Ir para a página seguinte.\n");
	printf("p <número>: Ir para a página número <número>.\n");
	printf("q: Saber o número total de produtos da lista.\n");
	printf("v: Voltar para o menu principal.\n");
	printf("\n");
	printf("Insira a opção que pretende:\n");

	while(1)
	{
		if(fgets(buff, BUFF_TAM7, stdin) != NULL)
			sscanf(buff, "%s %d", &car, &npagina);
		if(car == 's')
		{
			p = getPaginaNext(conjProds);
			if (p == NULL)
			{
				printf("Não é possível avançar para a próxima página.\n");
				nulo = 1;
			}
		}
		if(car == 'a')
		{
			p = getPaginaAnt(conjProds);
			if(p == NULL)
			{
				printf("Não é possível aceder à página anterior.\n");
				nulo = 1;
			}
		}
		if(car == 'p')
		{
			printf("npagina= %d\n", npagina);
			p = getPaginaP(conjProds, npagina);
			if(p == NULL)
			{
				printf("A página a que pretende aceder não existe.\n");
				nulo = 1;
			}
		}
		if(car == 'q')
		{
			printf("\n");
			printf("Existem %d produtos começados pela letra %s\n", getElementosTotal(conjProds), letra);
			sinal = 1;
		}
		if(car == 'v')
		{
			free(letra);
			return 0;
		}
		if (car != 'v' && car != 'p' && car != 'q' && car != 's' && car != 'a')
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
		if((nulo == 0) && (sinal == 0))
		{
			printf("\n");
			printf("Produtos que começam com a letra %s:\n", letra);
			printf("\n");

			for(i = 0; i < getPaginaSize(p); i++)
			{
				s = getNextElementoPage(p);
				printf("%s\n", s);
			}
			printf("\n");
			printf("Página %d de %d\n", getPaginaAtual(conjProds), getPaginasTotal(conjProds));
		}
		printf("\n");
		printf("       MENU:\n");
		printf("\n");
		printf("a: Ir para a página anterior.\n");
		printf("s: Ir para a página seguinte.\n");
		printf("p <número>: Ir para a página número <número>.\n");
		printf("q: Saber o número total de produtos da lista.\n");
		printf("v: Voltar para o menu principal.\n");
		printf("\n");
		printf("Insira a opção que pretende:\n");
	}
	free(s);
	free(letra);
	return 0;
}

int query3(CatProds catpr, Faturacao fat)
{
	int j, sinal, mes, globalVP, globalVN;
	double globalFN, globalFP;
	Produto p;
	TotalVendas tv;
	TotalFat tf;
	char buff1[BUFF_TAM10], buff2[BUFF_TAM5], *produto, car;
	produto = (char*)malloc(BUFF_TAM10*sizeof(sizeof(char)));
	sinal = 0;
	tv = initVenda();

	while(1)
	{
		globalVN = globalVP = 0;
		globalFN = globalFP = 0.0;
		if(sinal == 0)
		{
			printf("Insira o código de produto que pretende ver.\n");

			if(fgets(buff1, BUFF_TAM10, stdin) != NULL)
				sscanf(buff1, "%s", produto);

			p = convProduto(produto);
			if(existeProduto(catpr, p) == 1)
			{
				printf("O produto que inseriu não existe.\n");
				freeTotalVendas(tv);
				free(produto);
				return 0;
			}

			printf("Agora insira o mês.\n");
			if(fgets(buff2, BUFF_TAM5, stdin) != NULL)
				sscanf(buff2, "%d", &mes);

			if(mes < 1 && mes > MESES)
			{
				printf("O mês que inseriu não é válido.\n");
				freeTotalVendas(tv);
				free(produto);
				return 0;
			}

			printf("Se deseja ver o resultados global prima 'g'.\n");
			printf("Se deseja ver os resultados por filial prima 'f'.\n");
			if(fgets(buff2, BUFF_TAM5, stdin) != NULL)
				sscanf(buff2, "%c", &car);
			if (car != 'g' && car != 'f')
			{
					printf("O modo de visualização que escolheu não é válido.\n");
					freeTotalVendas(tv);
					free(produto);
					return 0;
			}	
			for(j = 0; j < FILIAIS; j++)
			{
				tf = fateVendasMesPro(fat, p, mes, j, tv);
				if (car == 'f')
				{
					printf("Filial %d:\n", j + 1);
					printf("\n");
					printf("Número total de vendas com modo N: %d\n", getTotalVendas(tv, j, 0));
					printf("Total faturado com modo N: %f\n", getTotalFaturacao(tf, j, 0));
					printf("\n");
					printf("Número total de vendas com modo P: %d\n", getTotalVendas(tv, j, 1));
					printf("Total faturado com modo P: %f\n", getTotalFaturacao(tf, j, 1));
					printf("\n");
				}
				else 
				{
					globalVN = globalVN + getTotalVendas(tv, j, 0);
					globalVP = globalVP + getTotalVendas(tv, j, 1);
					globalFN = globalFN + getTotalFaturacao(tf, j, 0);
					globalFP = globalFP + getTotalFaturacao(tf, j, 1);
				}
			}
			if (car == 'g')
			{
				printf("O valor global de vendas com modo N é: %d\n", globalVN);
				printf("O valor global de vendas com modo P é: %d\n", globalVP);
				printf("O valor global faturado com modo N é: %f\n", globalFN);
				printf("O valor global faturado com modo P é: %f\n", globalFP);
			}
				setTotalVendas(tv, 0, 0, 0);
				setTotalFaturacao(tf, 0, 0, 0.0);
				setTotalVendas(tv, 0, 1, 0);
				setTotalFaturacao(tf, 0, 1, 0.0);
				setTotalVendas(tv, 1, 0, 0);
				setTotalFaturacao(tf, 1, 0, 0.0);
				setTotalVendas(tv, 1, 1, 0);
				setTotalFaturacao(tf, 1, 1, 0.0);
				setTotalVendas(tv, 2, 0, 0);
				setTotalFaturacao(tf, 2, 0, 0.0);
				setTotalVendas(tv, 2, 1, 0);
				setTotalFaturacao(tf, 2, 1, 0.0);
		}
		printf("\n");
		printf("      MENU:\n");
		printf("\n");
		printf("c: Voltar a inserir um código de produto e um mês.\n");
		printf("v: Voltar ao menu inicial.\n");

		if(fgets(buff1, BUFF_TAM10, stdin) != NULL)
			sscanf(buff1, "%s", &car);
		if(car == 'v')
			break;
		else if(car != 'c')
		{
			sinal = 1;
			printf("\n");
			printf("Comando incorreto!\n");
		}
	}
	freeTotalVendas(tv);
	free(produto);
	return 0;
}

int query4(CatProds catpr, Faturacao fat)
{
	int i, npagina, nulo, sinal, fil, quantos;
	char *s, buff1[BUFF_TAM4], buff2[BUFF_TAM4], buff3[BUFF_TAM4], car, tipo;
	Conjunto c;
	Pagina p;

	sinal = nulo = quantos = 0;
	

	printf("Se deseja ver os valores totais prima 't'.\n");
	printf("Se deseja ver os valores de uma filial prima 'f'.\n");
	if(fgets(buff2, BUFF_TAM4, stdin) != NULL)
		sscanf(buff2, "%c", &tipo);
	if(tipo == 't')
	{
		c = produtosNaoVendidos(catpr, fat);
		printf("\n");
		printf("Existem %d produtos não vendidos.\n", getElementosTotal(c));
		printf("\n");
	}
	else if(tipo == 'f')
		{
			printf("Insira a filial que pretende.\n");
			if(fgets(buff3, BUFF_TAM4, stdin) != NULL)
				sscanf(buff3, "%d", &fil);
			if(fil < 0 || fil > FILIAIS)
			{
				printf("A filial que inseriu não existe.\n");
				return 0;
			}

			c = produtosNaoVendidosFil(catpr, fat, fil);

			printf("\n");
			printf("Existem %d produtos não vendidos na filial %d.\n", getElementosTotal(c), fil);
			printf("\n");
		}
		else 
		{
			printf("O modo de visualização inserido não é válido.\n");
			return 0;
		}

	p = getPaginaNext(c);

	for(i = 0; i < getPaginaSize(p); i++)
	{
		s = getNextElementoPage(p);
		printf("%s\n", s);
	}
	
	printf("\n");
	printf("Página 1 de %d\n", getPaginasTotal(c));
	printf("\n");

	printf("       MENU:\n");
	printf("\n");
	printf("a: Ir para a página anterior.\n");
	printf("s: Ir para a página seguinte.\n");
	printf("p <número>: Ir para a página número <número>.\n");
	printf("v: Voltar para o menu principal.\n");
	printf("\n");
	printf("Insira a opção que pretende:\n");

	while(1)
	{
		if(fgets(buff1, BUFF_TAM4, stdin) != NULL)
			sscanf(buff1, "%s %d", &car, &npagina);
		if(car == 's')
		{
			p = getPaginaNext(c);
			if (p == NULL)
			{
				printf("Não é possível avançar para a próxima página.\n");
				nulo = 1;
			}
		}
		if(car == 'a')
		{
			p = getPaginaAnt(c);
			if(p == NULL)
			{
				printf("Não é possível aceder à página anterior.\n");
				nulo = 1;
			}
		}
		if(car == 'p')
		{
			p = getPaginaP(c, npagina);
			if(p == NULL)
			{
				printf("A página a que pretende aceder não existe.\n");
				nulo = 1;
			}
		}
		if(car == 'v')
		{
			freeConjunto(c);
			free(p);
			return 0;
		}
		if (car != 'v' && car != 'p' && car != 'q' && car != 's' && car != 'a')
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
		if((nulo == 0) && (sinal == 0))
		{
			printf("\n");
			printf("Produtos que não foram vendidos:\n");
			printf("\n");

			for(i = 0; i < getPaginaSize(p); i++)
			{
				s = getNextElementoPage(p);
				printf("%s\n", s);
			}
			printf("\n");
			printf("Página %d de %d\n", getPaginaAtual(c), getPaginasTotal(c));
		}
		printf("\n");
		printf("       MENU:\n");
		printf("\n");
		printf("a: Ir para a página anterior.\n");
		printf("s: Ir para a página seguinte.\n");
		printf("p <número>: Ir para a página número <número>.\n");
		printf("v: Voltar para o menu principal.\n");
		printf("\n");
		printf("Insira a opção que pretende:\n");
	}
	freeConjunto(c);
	freePage(p);
	return 0;
}

int query5(CatClientes catcl, Filial f1, Filial f2, Filial f3)
{
	int i, sinal;
	Cliente c;
	char buff[BUFF_TAM10], *cliente, car;
	cliente = (char*)malloc(BUFF_TAM10*sizeof(sizeof(char)));
	sinal = 0;

	while(1)
	{
		if(sinal == 0)
		{
			printf("Insira o código de cliente que pretende ver.\n");

			if(fgets(buff, BUFF_TAM10, stdin) != NULL)
				sscanf(buff, "%s", cliente);

			c = convCliente(cliente);
			if(existeCliente(catcl, c) == 1)
				printf("O cliente que inseriu não existe.\n");
			else
			{	
				printf("Filial 1:\n");
				printf("\n");
				for(i = 0; i < MESES; i++)
				{
					printf("Mês %d: %d\n", i + 1, procuraClienteProdsTotal(cliente, f1, i));
				}
				printf("\n");
				printf("Filial 2:\n");
				printf("\n");
				for(i = 0; i < MESES; i++)
				{
					printf("Mês %d: %d\n", i + 1, procuraClienteProdsTotal(cliente, f2, i));
				}
				printf("\n");
				printf("Filial 3:\n");
				printf("\n");
				for(i = 0; i < MESES; i++)
				{
					printf("Mês %d: %d\n", i + 1, procuraClienteProdsTotal(cliente, f3, i));
				}
			}
		}
		printf("\n");
		printf("      MENU:\n");
		printf("\n");
		printf("c: Voltar a inserir um código de cliente.\n");
		printf("v: Voltar ao menu inicial.\n");

		if(fgets(buff, BUFF_TAM10, stdin) != NULL)
			sscanf(buff, "%s", &car);

		if(car == 'v')
			break;
		else if(car != 'c')
		{
			sinal = 1;
			printf("\n");
			printf("Comando incorreto!\n");
		}
	}
	free(cliente);
	return 0;
}

int query6(Faturacao f)
{
	TotalFat fa;
	TotalVendas v;
	int resV, i, mesI, mesS;
	double resF;
	char buff1[BUFF_TAM5], buff2[BUFF_TAM5], buff3[BUFF_TAM4], car;

	resV = 0;
	resF = 0;
	v = initVenda();


	printf("Insira o mês que pretende (limite inferior).\n");
	if(fgets(buff1, BUFF_TAM5, stdin) != NULL)
		sscanf(buff1, "%d", &mesI);

	if(mesI < 0 || mesI > MESES)
	{
		printf("O mês que foi inserido não é válido.\n");
		freeTotalVendas(v);
		return 0;
	}

	printf("Insira o mês que pretende (limite superior).\n");
	if(fgets(buff2, BUFF_TAM5, stdin) != NULL)
		sscanf(buff2, "%d", &mesS);

	if(mesS < 0 || mesS > MESES)
	{
		printf("O mês que foi inserido não é válido.\n");
		freeTotalVendas(v);
		return 0;
	}

	if(mesS < mesI)
	{
		printf("O intervalo de meses que foi inserido não é válido.\n");
		freeTotalVendas(v);
		return 0;
	}

	if((mesI > 0) && (mesS  < 13))
	{ 
		fa = intervaloVeF(f, mesI, mesS, v);

		for(i = 0; i < FILIAIS; i++)
		{
			resV += getTotalVendas(v, i, 0);
			resF += getTotalFaturacao(fa, i , 0);
		}

		printf("Total de vendas %d\n", resV);
		printf("Total faturado %f\n", resF);
	}
	printf("Para voltar para o menu principal prima 'v'.\n");
	if(fgets(buff3, BUFF_TAM4, stdin) != NULL)
		sscanf(buff3, "%c", &car);
	if (car == 'v')
		return 0;

	freeTotalVendas(v);
	return 0;
}

int query7(CatClientes catcl, Filial f1, Filial f2, Filial f3)
{
	int i, npagina, nulo, sinal;
	char* s, buff[BUFF_TAM10], car;
	Conjunto c;
	Pagina p;

	sinal = nulo = 0;
	c = clientesTodasFiliais(catcl, f1, f2, f3);
	p = getPaginaNext(c);

	for(i = 0; i < getPaginaSize(p); i++)
	{
		s = getNextElementoPage(p);
		printf("%s\n", s);
	}
	
	printf("\n");
	printf("Página 1 de %d\n", getPaginasTotal(c));
	printf("\n");

	printf("       MENU:\n");
	printf("\n");
	printf("a: Ir para a página anterior.\n");
	printf("s: Ir para a página seguinte.\n");
	printf("p <número>: Ir para a página número <número>.\n");
	printf("q: Saber o número total de clientes que compraram em todas as filiais.\n");
	printf("v: Voltar para o menu principal.\n");
	printf("\n");
	printf("Insira a opção que pretende:\n");

	while(1)
	{
		if(fgets(buff, BUFF_TAM7, stdin) != NULL)
			sscanf(buff, "%s %d", &car, &npagina);
		if(car == 's')
		{
			p = getPaginaNext(c);
			if (p == NULL)
			{
				printf("Não é possível avançar para a próxima página.\n");
				nulo = 1;
			}
		}
		if(car == 'a')
		{
			p = getPaginaAnt(c);
			if(p == NULL)
			{
				printf("Não é possível aceder à página anterior.\n");
				nulo = 1;
			}
		}
		if(car == 'p')
		{
			p = getPaginaP(c, npagina);
			if(p == NULL)
			{
				printf("A página a que pretende aceder não existe.\n");
				nulo = 1;
			}
		}
		if(car == 'q')
		{
			printf("Existem %d clientes que compraram em todas as filiais.\n", getElementosTotal(c));
			sinal = 1;
		}
		if(car == 'v')
		{
			freeConjunto(c);
			freePage(p);
			return 0;
		}
		if (car != 'v' && car != 'p' && car != 'q' && car != 's' && car != 'a')
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
		if((nulo == 0) && (sinal == 0))
		{
			printf("\n");
			printf("Clientes que compraram em todas as filiais:\n");
			printf("\n");

			for(i = 0; i < getPaginaSize(p); i++)
			{
				s = getNextElementoPage(p);
				printf("%s\n", s);
			}
			printf("\n");
			printf("Página %d de %d\n", getPaginaAtual(c), getPaginasTotal(c));
		}	
		printf("\n");
		printf("       MENU:\n");
		printf("\n");
		printf("a: Ir para a página anterior.\n");
		printf("s: Ir para a página seguinte.\n");
		printf("p <número>: Ir para a página número <número>.\n");
		printf("q: Saber o número total de clientes que compraram em todas as filiais.\n");
		printf("v: Voltar para o menu principal.\n");
		printf("\n");
		printf("Insira a opção que pretende:\n");
	}
	freeConjunto(c);
	freePage(p);
	return 0;
}

int query8(CatProds catpr, Filial f1, Filial f2, Filial f3)
{
	int i, npagina, nulo, sinal, fil;
	char *s, buffc[BUFF_TAM10], buffF[BUFF_TAM5], buffm[BUFF_TAM4], buff[BUFF_TAM7], car, *codigo, *modo, *modop, *modon;
	Pagina p;
	Conjunto c;
	Produto pr;
	clock_t begin, end;
	double time_spent;

	begin = clock();
	sinal = nulo = 0;
	c = NULL;
	codigo = malloc(BUFF_TAM10*sizeof(char));
	modo = malloc(sizeof(char));
	modop = malloc(sizeof(char));
	modon = malloc(sizeof(char));
	modop = "P";
	modon = "N";

	printf("Insira o código do produto que pretende.\n");
	if(fgets(buffc, BUFF_TAM10, stdin) != NULL)
		sscanf(buffc, "%s", codigo);

	pr = convProduto(codigo);
	if(existeProduto(catpr, pr) == 1)
	{
		printf("O código do produto que inseriu não é válido.\n");
		return 0;
	}

	printf("Insira a filial que pretende.\n");
	if(fgets(buffF, BUFF_TAM5, stdin) != NULL)
		sscanf(buffF, "%d", &fil);

	if(fil < 1 || fil > FILIAIS)
	{
		printf("A filial que inseriu não existe.\n");
		return 0;
	}

	printf("Se deseja ver a lista e o número total em modo N prima 'N'.\n");
	printf("Se deseja ver a lista e o número total em modo P prima 'P'.\n");

	if (fgets(buffm, BUFF_TAM4, stdin) != NULL)
		sscanf(buffm, "%s", modo);

	if(strcmp(modo, modop) != 0 && strcmp(modo, modon) != 0)
	{
		printf("O modo que inseriu não é válido.\n");
		return 0;
	}
	if(sinal == 0)
	{
		if (fil == 1)
			c = procuraProdutoFilial(codigo, f1, modo);
		if (fil == 2)
			c = procuraProdutoFilial(codigo, f2, modo);
		if (fil == 3)
			c = procuraProdutoFilial(codigo, f3, modo);
	}
	p = getPaginaNext(c);

	for(i = 0; i < getPaginaSize(p); i++)
	{
		s = getNextElementoPage(p);
		printf("%s\n", s);
	}

	printf("\n");
	end = clock();
	time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
	printf("Tempo decorrido: %f segundos.\n", time_spent);
	printf("\n");
	printf("Página 1 de %d\n", getPaginasTotal(c));
	printf("\n");

	printf("       MENU:\n");
	printf("\n");
	printf("a: Ir para a página anterior.\n");
	printf("s: Ir para a página seguinte.\n");
	printf("p <número>: Ir para a página número <número>.\n");
	printf("q: Saber o número total de clientes que comprou o produto no modo escolhido.\n");
	printf("v: Voltar para o menu principal.\n");
	printf("\n");
	printf("Insira a opção que pretende:\n");

	while(1)
	{
		if(fgets(buff, BUFF_TAM7, stdin) != NULL)
			sscanf(buff, "%s %d", &car, &npagina);
		if(car == 's')
		{
			p = getPaginaNext(c);
			if (p == NULL)
			{
				printf("Não é possível avançar para a próxima página.\n");
				nulo = 1;
			}
		}
		if(car == 'a')
		{
			p = getPaginaAnt(c);
			if(p == NULL)
			{
				printf("Não é possível aceder à página anterior.\n");
				nulo = 1;
			}
		}
		if(car == 'p')
		{
			p = getPaginaP(c, npagina);
			if(p == NULL)
			{
				printf("A página a que pretende aceder não existe.\n");
				nulo = 1;
			}
		}
		if(car == 'q')
		{
			printf("\n");
			printf("Existem %d clientes que compraram o produto %s no modo %s na filial %d.\n", getElementosTotal(c), codigo, modo, fil);
			sinal = 1;
		}
		if(car == 'v')
		{
			freeConjunto(c);
			freePage(p);
			free(codigo);
			free(modo);
			free(pr);
			return 0;
		}
		if (car != 'v' && car != 'p' && car != 'q' && car != 's' && car != 'a')
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
		if((nulo == 0) && (sinal == 0))
		{
			printf("\n");
			printf("Clientes que compraram em todas as filiais:\n");
			printf("\n");

			for(i = 0; i < getPaginaSize(p); i++)
			{
				s = getNextElementoPage(p);
				printf("%s\n", s);
			}
			printf("\n");
			printf("Página %d de %d\n", getPaginaAtual(c), getPaginasTotal(c));
		}	
		printf("\n");
		printf("       MENU:\n");
		printf("\n");
		printf("a: Ir para a página anterior.\n");
		printf("s: Ir para a página seguinte.\n");
		printf("p <número>: Ir para a página número <número>.\n");
		printf("q: Saber o número total de clientes que comprou o produto no modo escolhido.\n");
		printf("v: Voltar para o menu principal.\n");
		printf("\n");
		printf("Insira a opção que pretende:\n");
	}
	freeConjunto(c);
	freePage(p);
	free(codigo);
	free(modo);
	free(pr);
	return 0;
}

int query9(CatClientes catcl, Filial f1, Filial f2, Filial f3, int prodsvalidos)
{
	int i, npagina, nulo, sinal, mes;
	char* s, buff1[BUFF_TAM10], buff2[BUFF_TAM5], buff3[BUFF_TAM7], car, *codigo;
	Conjunto c;
	Pagina p;
	Cliente cl;
	clock_t begin, end;
	double time_spent;

	begin = clock();
	sinal = nulo = 0;
	codigo = malloc(BUFF_TAM10*sizeof(char));

	
	printf("Insira o código de cliente que pretende.\n");
	if(fgets(buff1, BUFF_TAM10, stdin) != NULL)
		sscanf(buff1, "%s", codigo);

	cl = convCliente(codigo);
	if(existeCliente(catcl, cl) == 1)
	{
		printf("O cliente que inseriu não existe.\n");
		free(cl);
		free(codigo);
		return 0;
	}

	printf("\n");
	printf("Agora insira o mês que pretende.\n");
	if(fgets(buff2, BUFF_TAM5, stdin) != NULL)
		sscanf(buff2, "%d", &mes);

	if(mes < 1 || mes > MESES)
	{
		printf("O mês que inseriu não é válido.\n");
		free(cl);
		free(codigo);
		return 0;
	}

	c = produtosMaisCompradosCli(f1, f2, f3, cl, mes, prodsvalidos);
	p = getPaginaNext(c);

	for(i = 0; i < getPaginaSize(p); i++)
	{
		s = getNextElementoPage(p);
		printf("%s\n", s);
	}
	printf("\n");
	end = clock();
	time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
	printf("Tempo decorrido: %f segundos.\n", time_spent);

	printf("\n");
	printf("Página 1 de %d\n", getPaginasTotal(c));
	printf("\n");
	printf("       MENU:\n");
	printf("\n");
	printf("a: Ir para a página anterior.\n");
	printf("s: Ir para a página seguinte.\n");
	printf("p <número>: Ir para a página número <número>.\n");
	printf("v: Voltar para o menu principal.\n");
	printf("\n");
	printf("Insira a opção que pretende:\n");

	while(1)
	{
		if(fgets(buff3, BUFF_TAM7, stdin) != NULL)
			sscanf(buff3, "%s %d", &car, &npagina);
		if(car == 's')
		{
			p = getPaginaNext(c);
			if (p == NULL)
			{
				printf("Não é possível avançar para a próxima página.\n");
				nulo = 1;
			}
		}
		if(car == 'a')
		{
			p = getPaginaAnt(c);
			if(p == NULL)
			{
				printf("Não é possível aceder à página anterior.\n");
				nulo = 1;
			}
		}
		if(car == 'p')
		{
			p = getPaginaP(c, npagina);
			if(p == NULL)
			{
				printf("A página a que pretende aceder não existe.\n");
				nulo = 1;
			}
		}
		if(car == 'v')
		{
			freeConjunto(c);
			freePage(p);
			free(codigo);
			free(cl);
			return 0;
		}
		if (car != 'v' && car != 'p' && car != 's' && car != 'a')
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
		if((nulo == 0) && (sinal == 0))
		{
			printf("\n");
			printf("Produtos que não foram vendidos:\n");
			printf("\n");

			for(i = 0; i < getPaginaSize(p); i++)
			{
				s = getNextElementoPage(p);
				printf("%s\n", s);
			}
			printf("\n");
			printf("Página %d de %d\n", getPaginaAtual(c), getPaginasTotal(c));
		}
		printf("\n");
		printf("       MENU:\n");
		printf("\n");
		printf("a: Ir para a página anterior.\n");
		printf("s: Ir para a página seguinte.\n");
		printf("p <número>: Ir para a página número <número>.\n");
		printf("q: Saber o número total de produtos não vendidos.\n");
		printf("v: Voltar para o menu principal.\n");
		printf("\n");
		printf("Insira a opção que pretende:\n");

	}
	freeConjunto(c);
	freePage(p);
	free(codigo);
	free(cl);

	return 0;
}

int query10(Filial f1, Filial f2, Filial f3, int countvalidas)
{
	ProdsFat pf;
	Conjunto c;
	Pagina p;
	char *s, buff[BUFF_TAM5], buff2[BUFF_TAM7], car;
	int i, N, npagina, nulo, sinal;
	clock_t begin, end;
	double time_spent;

	begin = clock();
	nulo = sinal = 0;

	printf("Insira o número de produtos que deseja ver.\n");
	if(fgets(buff, BUFF_TAM5, stdin) != NULL)
		sscanf(buff, "%d", &N);

	pf = initProdsFat(countvalidas);
	pf = preencheProdsTotFat(f1, f2, f3, pf);
	c = topNProdutos(pf, 3);
	p = getPaginaNext(c);

	printf("%d produtos mais vendidos:\n", N);
	for(i = 0; i < getPaginaSize(p); i++)
	{
		s = getNextElementoPage(p);
		printf("%s\n", s);
	}

	printf("\n");
	end = clock();
	time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
	printf("Tempo decorrido: %f segundos.\n", time_spent);

	printf("\n");
	printf("Página 1 de %d\n", getPaginasTotal(c));
	printf("\n");
	printf("       MENU:\n");
	printf("\n");
	printf("a: Ir para a página anterior.\n");
	printf("s: Ir para a página seguinte.\n");
	printf("p <número>: Ir para a página número <número>.\n");
	printf("v: Voltar para o menu principal.\n");
	printf("\n");
	printf("Insira a opção que pretende:\n");

	while(1)
	{
		if(fgets(buff2, BUFF_TAM7, stdin) != NULL)
			sscanf(buff2, "%s %d", &car, &npagina);
		if(car == 's')
		{
			p = getPaginaNext(c);
			if (p == NULL)
			{
				printf("Não é possível avançar para a próxima página.\n");
				nulo = 1;
			}
		}
		if(car == 'a')
		{
			p = getPaginaAnt(c);
			if(p == NULL)
			{
				printf("Não é possível aceder à página anterior.\n");
				nulo = 1;
			}
		}
		if(car == 'p')
		{
			p = getPaginaP(c, npagina);
			if(p == NULL)
			{
				printf("A página a que pretende aceder não existe.\n");
				nulo = 1;
			}
		}
		if(car == 'v')
		{
			freeConjunto(c);
			freePage(p);
			freeProdsFat(pf);
			return 0;
		}
		if (car != 'v' && car != 'p' && car != 's' && car != 'a')
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
		if((nulo == 0) && (sinal == 0))
		{
			printf("\n");
			printf("Produtos que não foram vendidos:\n");
			printf("\n");

			for(i = 0; i < getPaginaSize(p); i++)
			{
				s = getNextElementoPage(p);
				printf("%s\n", s);
			}
			printf("\n");
			printf("Página %d de %d\n", getPaginaAtual(c), getPaginasTotal(c));
		}
		printf("\n");
		printf("       MENU:\n");
		printf("\n");
		printf("a: Ir para a página anterior.\n");
		printf("s: Ir para a página seguinte.\n");
		printf("p <número>: Ir para a página número <número>.\n");
		printf("q: Saber o número total de produtos não vendidos.\n");
		printf("v: Voltar para o menu principal.\n");
		printf("\n");
		printf("Insira a opção que pretende:\n");
	}
	freeConjunto(c);
	freePage(p);
	freeProdsFat(pf);

	return 0;
}

int query11(CatClientes catcl, Filial f1, Filial f2, Filial f3, int countvalidas)
{
	ProdsFat pf;
	Conjunto c;
	Pagina p;
	Cliente cl;
	char buff1[BUFF_TAM10], buff2[BUFF_TAM4], car, *codigo, *s;
	int i, sinal;
	clock_t begin, end;
	double time_spent;

	begin = clock();
	sinal = 0;
	pf = initProdsFat(countvalidas);
	codigo = malloc(BUFF_TAM10*sizeof(char));

	while(1)
	{
		if(sinal == 0)
		{
			printf("Insira o código de cliente que pretende.\n");
			if(fgets(buff1, BUFF_TAM10, stdin) != NULL)
				sscanf(buff1, "%s", codigo);

			cl = convCliente(codigo);
			if(existeCliente(catcl, cl) == 1)
			{
				printf("O cliente que inseriu não existe.\n");
				free(codigo);
				freeProdsFat(pf);
				free(cl);
				return 0;
			}
			if (sinal == 0)
			{
				pf = preencheProdsFat(codigo, f1, pf);
				pf = preencheProdsFat(codigo, f2, pf);
				pf = preencheProdsFat(codigo, f3, pf);

				c = top3Produtos(pf);

				p = getPaginaNext(c);

				printf("\n");
				printf("Top 3 de produtos em que o cliente %s gastou mais dinheiro:\n", codigo);
				printf("\n");
				for(i = 0; i < getPaginaSize(p); i++)
				{
					s = getNextElementoPage(p);
					printf("%s\n", s);
				}
			}
		}
		printf("\n");
		end = clock();
		time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
		printf("Tempo decorrido: %f segundos.\n", time_spent);
		printf("\n");
		printf("Prima 'v' para voltar ao menu principal.\n");

		if(fgets(buff2, BUFF_TAM4, stdin) != NULL)
			sscanf(buff2, "%c", &car);
		if(car == 'v')
			break;
		else
		{
			printf("Comando incorreto!\n");
			sinal = 1;
		}
	}
	freeProdsFat(pf);
	free(cl);
	freePage(p);
	free(c);
	free(codigo);

	return 0;
}


int query12(CatClientes catcl, CatProds catpr, Faturacao fat, Filial f1, Filial f2, Filial f3)
{
	char buff[BUFF_TAM4], car;
	clock_t begin, end;
	double time_spent;

	begin = clock();
	printf("Existem %d clientes que não realizaram compras.\n", quantosClientesNenhumaCompra(catcl, f1, f2, f3));
	printf("Existem %d produtos que não foram comprados.\n", quantosNaoVendidos(catpr, fat));

	printf("\n");
	end = clock();
	time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
	printf("Tempo decorrido: %f segundos.\n", time_spent);
	printf("\n");

	printf("Para voltar para o menu principal insira 'v'.\n");

	if(fgets(buff, BUFF_TAM4, stdin) != NULL)
		sscanf(buff, "%c", &car);
	if(car == 'v')
		return 0;
	else printf("O comando inserido não é válido!\n");

	return 0;
}
