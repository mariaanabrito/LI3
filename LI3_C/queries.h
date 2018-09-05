#ifndef queries_H
#define queries_H

#include <stdio.h>
#include "catalogoff.h"
#include "prods.h"
#include "clientes.h"
#include "lista.h"
#include "catalogo.h"
#include "faturacao.h"
#include "vendas.h"
#include "filial.h"

/**
* Função que carrega o conteúdo do ficheiro de clientes para o catálogo destes.
*/
CatClientes carregarFicheiroClientes(FILE *fc);
/**
* Função que carrega o conteúdo do ficheiro de produtos para o catálogo destes.
*/
CatProds carregarFicheiroProdutos(FILE *fp);
/**
* Função que carrega o counteúdo de um ficheiro a ser introduzido pelo utilizador (relativo ao ficheiro de vendas) e o carrega 
* para as três estruturas Filial e para a estrutura Faturacao.
*/
int carregarFicheiroVendas(FILE *fv, CatClientes catcl, CatProds catpr, int numfile, Faturacao fat, Filial f1, Filial f2, Filial f3);
/**
* Função que determina a lista e o total de produtos cujo código se inicia por uma dada letra.
*/
int query2(CatProds catpr);
/**
*Função que, dado um mês e um código de produto, determina o número total de vendas e o total faturado .
*/
int query3(CatProds catpr, Faturacao fat);
/**
* Função que determina a lista ordenada de códigos dos produtos que ninguém comprou.
*/
int query4(CatProds catpr, Faturacao fat);
/**
* Função que, dado um código de cliente, cria uma tabela com o número total de produtos comprados. 
*/
int query5(CatClientes catcl, Filial f1, Filial f2, Filial f3);
/**
* Função que, dado um intervalo fechado de meses, determina o total de vendas registadas nesse intervalo e o total faturado.
*/
int query6(Faturacao f);
/**
* Função que determina a lista ordenada de códigos de clientes que realizaram compras em todas as filiais.
*/
int query7(CatClientes catcl, Filial f1, Filial f2, Filial f3);
/**
* Função que , dado um código de produto e uma filial, determina os códigos dos clientes que o compraram.
*/
int query8(CatProds catpr, Filial f1, Filial f2, Filial f3);
/**
* Função que, dado um código de cliente e um mês, determina a lista de códigos de produtos que mais comprou.
*/
int query9(CatClientes catcl, Filial f1, Filial f2, Filial f3, int prodsvalidos);
/**
* Função que cria uma lista com os N produtos mais vendidos todo o ano.
*/
int query10(Filial f1, Filial f2, Filial f3, int countvalidas);
/**
* Função que, dado um código de cliente, determina quais os códigos dos 3 produtos em que gastou mais dinheiro durante o ano.
*/
int query11(CatClientes catcl, Filial f1, Filial f2, Filial f3, int countvalidas);
/**
* Função que determina o número de clientes registados que não realizaram compras, além do número de produtos que ninguém comprou.
*/
int query12(CatClientes catcl, CatProds catpr, Faturacao fat, Filial f1, Filial f2, Filial f3);

#endif
