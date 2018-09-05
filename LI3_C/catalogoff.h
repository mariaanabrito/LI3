#ifndef catalogoff_H
#define catalogoff_H

#include "catalogo.h"
#include "vendas.h"
#include "lista.h"

/**
* A estrutura CatFatFil armazena a informação relativa à faturação ou às filiais.
*/
typedef struct catff *CatFatFil;
/**
* A estrutura Dados armazena a informação detallha de um produto ou de um cliente.
*/
typedef struct dados *Dados;

/**
* getDadosClientes devolve os dados relativos a um cliente passado como argumento.
*/
Dados getDadosCliente(char* cliente, CatFatFil t);
/**
* isEmpty verifica se um catálogo é vazio ou não.
*/
int isEmpty(CatFatFil t);
/**
* getPrecoFF devolve o preço de um dado segundo o parâmetro passado como argumento.
*/
double getPrecoFF(Dados d, int index);
/**
* getUnidades devolve as unidades compradas de um dado segundo o parâmetro passado como argumento.
*/
int getUnidadesFF(Dados d, int index);
/**
* getModoFF devolve o modo de venda de um dado segundo o parâmetro passado como argumento.
*/
char* getModoFF(Dados d, int index);
/**
* getProdFF devolve o produto de um dado segundo o parâmetro passado como argumento.
*/
char* getProdFF(Dados d, int index);
/**
* getCounterFF devolve o índice de um dado.
*/
int getCounterFF(Dados d);
/**
* getSizeFF devolve o tamanho de um dado.
*/
int getSizeFF(Dados d);
/**
* setPrecoFF atribui um preço a um dado segundo o parâmetro passado como argumento.
*/
void setPrecoFF(Dados d, double preco, int index);
/**
* setUnidadesFF atribui um número de unidades compradas a um dado segundo o parâmetro passado como argumento.
*/
void setUnidadesFF(Dados d, int unidades, int index);
/**
* setModoFF atribui um modo de venda a um dado segundo o parâmetro passado como argumento.
*/
void setModoFF(Dados d, char* modo, int index);
/**
* setProdFF atribui um produto a um dado segundo o parâmetro passado como argumento.
*/
void setProdFF(Dados d, char* produto, int index);
/**
* getCatFatFilEsq devolve um catálogo.
*/
CatFatFil getCatFatFilEsq(CatFatFil a);
/**
* getCatFatFilDir devolve um catálogo.
*/
CatFatFil getCatFatFilDir(CatFatFil a);
/**
* getChave devolve o código de uma chave de um catálogo passado como argumento.
*/
Codigo getChave(CatFatFil a);
/**
* getDados devolve o dado de um catálogo passado como argumento.
*/
Dados getDados(CatFatFil a);
/**
* findChave devolve o resultado de procurar uma chave num catálogo passado como argumento.
*/
int findChave(Codigo chave, CatFatFil t);
/**
* heightFatFil devolve a altura de um catálogo passado como argumento.
*/
int heightFatFil(CatFatFil n);
/**
* sizeFatFil devolve o tamanho de um catálogo passado como argumento.
*/
int sizeFatFil(CatFatFil n);
/**
* insertVendas insere uma venda nova num catálogo segundo os parâmetros passados como argumentos. O argumento decisao distingue 
* se a inserção é do tipo Filial ou do tipo Faturacao.
*/
CatFatFil insertVendas(CatFatFil t, Codigo chave, Vendas v, int decisao);
/**
* disposeVendas liberta o espaço alocado de um catálogo passado como argumento.
*/
void disposeVendas(CatFatFil t);

#endif