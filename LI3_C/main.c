#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "catalogo.h"
#include "prods.h"
#include "clientes.h"
#include "lista.h"
#include "catalogoff.h"
#include "faturacao.h"
#include "vendas.h"
#include "filial.h"
#include "queries.h"


#define MESES 12
#define FILIAIS 3
#define BUFF_TAM10 10
#define BUFF_TAM4 4
#define BUFF_TAM25 25


/** 
* A função main é a centro de todo este programa, uma vez que chama as funções necessárias para o carregamento de ficheiros e 
* para a realização das queries.
*/

int main()
{	
	FILE *fd;
	Faturacao fat;
	CatProds catpr;
	CatClientes catcl;
	char com[BUFF_TAM4], car, buff[BUFF_TAM4], buffile[BUFF_TAM25], *ficheiro;
	int narg, i, q, countvalidas;
	Filial fil[FILIAIS];

	narg = 0;
	ficheiro = malloc(BUFF_TAM25*sizeof(char));

	fd = fopen("Clientes.txt", "r");
	catcl = carregarFicheiroClientes(fd);
	fclose(fd);

	fd = fopen("Produtos.txt", "r");
	catpr = carregarFicheiroProdutos(fd);
	fclose(fd);

	fat = initFaturacao(FILIAIS, MESES);
	for(i = 0; i < FILIAIS; i++)
		fil[i] = initFilial();
	fd = fopen("Vendas_1M.txt", "r");
	countvalidas = carregarFicheiroVendas(fd, catcl, catpr, 1, fat, fil[0], fil[1], fil[2]);
	fclose(fd);

	while(1)
	{
		printf("\n");
		printf("       MENU      \n");
		printf("\n");
		printf("Escolha uma das opções abaixo:\n");
		printf("\n");
		printf("c: Ler um ficheiro de vendas.\n");
		printf("q: Avançar para o menu das queries.\n");
		printf("e: Sair da aplicação.\n");

		if(fgets(com, BUFF_TAM4, stdin) != NULL)
			narg = sscanf(com, "%s", &car);
		if (narg > 1)
			getchar();

		if(car == 'c')
		{
			printf("Insira o nome do ficheiro que pretende ler.\n");
			if(fgets(buffile, BUFF_TAM25, stdin) != NULL)
				sscanf(buffile, "%s", ficheiro);

			if(strcmp(ficheiro, "Vendas_1M") == 0)
			{
				freeFaturacao(fat, FILIAIS, MESES);
				freeFilial(fil[0]);
				freeFilial(fil[1]);
				freeFilial(fil[2]);
				fat = initFaturacao(FILIAIS, MESES);
				for(i = 0; i < FILIAIS; i++)
					fil[i] = initFilial();
				fd = fopen("Vendas_1M.txt", "r");
				countvalidas = carregarFicheiroVendas(fd, catcl, catpr, 1, fat, fil[0], fil[1], fil[2]);
				fclose(fd);
			}
			else if(strcmp(ficheiro, "Vendas_3M") == 0)
				{
					freeFaturacao(fat, FILIAIS, MESES);
					freeFilial(fil[0]);
					freeFilial(fil[1]);
					freeFilial(fil[2]);
					fat = initFaturacao(FILIAIS, MESES);
					for(i = 0; i < FILIAIS; i++)
						fil[i] = initFilial();
					fd = fopen("Vendas_3M.txt", "r");
					countvalidas = carregarFicheiroVendas(fd, catcl, catpr, 3, fat, fil[0], fil[1], fil[2]);
					fclose(fd);
				}
				else if(strcmp(ficheiro, "Vendas_5M") == 0)
					{
						freeFaturacao(fat, FILIAIS, MESES);
						freeFilial(fil[0]);
						freeFilial(fil[1]);
						freeFilial(fil[2]);
						fat = initFaturacao(FILIAIS, MESES);
						for(i = 0; i < FILIAIS; i++)
							fil[i] = initFilial();
						fd = fopen("Vendas_5M.txt", "r");
						countvalidas = carregarFicheiroVendas(fd, catcl, catpr, 5, fat, fil[0], fil[1], fil[2]);
						fclose(fd);
					}
					else printf("O ficheiro que pretende carregar não existe.\n");
		}
		if(car == 'q')
		{
			printf("\033[2J\033[0;0f");
			printf("\033[%d;%df", 0, 0);
			printf("      MENU:\n");
			printf("1: Ler novamente o ficheiro Vendas_1M.\n");
			printf("2: Lista e total de produtos que se iniciam com uma letra.\n");
			printf("3: Número total de vendas e total faturado com um produto num determinado mês.\n");
			printf("4: Lista e número total de produtos que não foram comprados.\n");
			printf("5: Tabela de número total de produtos comprados em cada mês por um determinado cliente.\n");
			printf("6: Número total de vendas registadas e total faturado num determinado intervalo de meses\n");
			printf("7: Lista de clientes que compraram em todas as filiais.\n");
			printf("8: Códigos e número total de clientes que compraram um determinado produto numa certa filial.\n");
			printf("9: Lista de produtos que um determinado cliente comprou num certo mês.\n");
			printf("10: Lista dos N produtos mais vendidos durante todo o ano.\n");
			printf("11: Lista dos três produtos em que um determinado cliente gastou mais dinheiro durante o ano.\n");
			printf("12: Lista de clientes que não realizaram compras e lista de produtos que ninguém comprou.\n");
			printf("\n");
			printf("Insira o número da querie.\n");

			if(fgets(buff, BUFF_TAM4, stdin) != NULL)
				sscanf(buff, "%d", &q);
			printf("\033[2J\033[0;0f");
			printf("\033[%d;%df", 0, 0);
			if (q == 1)
			{
				freeFaturacao(fat, FILIAIS, MESES);
				freeFilial(fil[0]);
				freeFilial(fil[1]);
				freeFilial(fil[2]);
				fat = initFaturacao(FILIAIS, MESES);
				for(i = 0; i < FILIAIS; i++)
					fil[i] = initFilial();
				fd = fopen("Vendas_1M.txt", "r");
				countvalidas = carregarFicheiroVendas(fd, catcl, catpr, 1, fat, fil[0], fil[1], fil[2]);
				fclose(fd);
			}
			if (q == 2)
				query2(catpr);
			if (q == 3)
				query3(catpr, fat);
			if (q == 4)
				query4(catpr, fat);
			if (q == 5)
				query5(catcl, fil[0], fil[1], fil[2]);
			if (q == 6)
				query6(fat);
			if (q == 7)
				query7(catcl, fil[0], fil[1], fil[2]);
			if (q == 8)
				query8(catpr, fil[0], fil[1], fil[2]);
			if (q == 9)
				query9(catcl, fil[0], fil[1], fil[2], totalProdutos(catpr));
			if (q == 10)
				query10(fil[0], fil[1], fil[2], countvalidas);
			if (q == 11)
				query11(catcl, fil[0], fil[1], fil[2], countvalidas);
			if (q == 12)
				query12(catcl, catpr, fat, fil[0], fil[1], fil[2]);
			printf("\033[2J\033[0;0f");
			printf("\033[%d;%df", 0, 0);
		}
		if(car == 'e')
			break;
	}

	freeProds(catpr);
	freeClientes(catcl);
	freeFaturacao(fat, FILIAIS, MESES);
	freeFilial(fil[0]);
	freeFilial(fil[1]);
	freeFilial(fil[2]);
	free(ficheiro);

	return 0;
}