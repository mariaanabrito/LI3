#ifndef prods_H
#define prods_H

#include "catalogo.h"

/**
* Estrutura que representa o catálogo de produtos.
*/
typedef struct prods* CatProds;
/**
* Estrutura que representa um produto.
*/
typedef struct prod* Produto;
/**
* Estrutura que representa um produto.
*/
Catalogo getSubCatalogoProds(CatProds catpr, int h);
/**
* Função que inicializa o catálogo de produtos.
*/
CatProds initCatProds();
/**
* Função que insere um produto no catálogo.
*/
CatProds insereProduto(CatProds cps, Produto pr);
/**
* Função que verifica se existe um produto no catálogo.
*/
int existeProduto(CatProds cps, Produto pr);
/*
* Função que converte uma string num produto.
*/
Produto convProduto(char* linha);
/*
* Função que obtém uma string relativa a um produto.
*/
Codigo devolveCodigoPr(Produto pr);
/**
* Função que obtém o número total de produtos de um catálogo.
*/
int totalProdutos(CatProds cps);
/**
*Função que obtém o número total de produtos de um subcatálogo relativo a uma letra.	
*/
int totalProdutosLetra(CatProds cps, char* letra);
/**
*Função que liberta o espaço relativo a um catálogo de produtos.
*/
void freeProds(CatProds catpr);

#endif
