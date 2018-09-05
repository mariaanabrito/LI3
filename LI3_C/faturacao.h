#ifndef faturacao_H
#define faturacao_H

#include "vendas.h"
#include "catalogoff.h"
#include "prods.h"
#include "catalogo.h"
#include "lista.h"

/**
* A estrutura Faturacao armazena todas as informações relativas a produtos, preços, unidades compradas e modos de venda.
*/
typedef struct faturacao *Faturacao;
/**
* A estrutura TotalFat armazena a informação relativa ao total faturado.
*/
typedef struct totalF *TotalFat;
/**
* A estrutura TotalVendas armazena a informação relativa ao número total de vendas.
*/
typedef struct totalV *TotalVendas;

/**
* getPosFaturacao devolve uma posição da estrutura Faturacao segundo os parâmetros passados como argumentos.
*/
CatFatFil getPosFaturacao(Faturacao fat, int filial, int mes);
/**
* setTotalFaturacao atribui uma faturação a uma posição, segundo os parâmetros passados como argumentos, da estrutura TotalFat.
*/
void setTotalFaturacao(TotalFat f, int filial, int modo, double fat);
/**
* setTotalVendas atribui um número de vendas a uma posição, segundo os parâmetros passados como argumentos, da estrutura TotalVendas.
*/
void setTotalVendas(TotalVendas v, int filial, int modo, int vend);
/**
* getTotalFaturacao devolve uma faturação segundo os parâmetros passados como argumentos.
*/
double getTotalFaturacao(TotalFat f, int filial, int modo);
/**
* getTotalFaturacao devolve um número de vendas segundo os parâmetros passados como argumentos.
*/
int getTotalVendas(TotalVendas v, int filial, int modo);
/**
* initFat inicializa a estrutura TotalFat.
*/
TotalFat initFat();
/**
* initVendas inicializa a estrutura TotalVendas.
*/
TotalVendas initVenda();
/**
* initFaturacao inicializa a estrutura Faturacao segundo os parâmetros passados como argumentos.
*/
Faturacao initFaturacao(int linhas, int colunas);
/**
* insereFaturacao insere uma nova faturação, segundo os parâmetros passados como argumentos, na estrutura Faturacao.
*/
Faturacao insereFaturacao(Faturacao fat, Codigo produto, Vendas v, int filial, int mes);
/**
* quantosNaoVendidos devolve o número de produtos não vendidos.
*/
int quantosNaoVendidos(CatProds catpr, Faturacao fat);
/**
* freeFaturacao liberta o espaço alocado da estrutura Faturacao, segundo os parâmetros passados como argumentos.
*/
void freeFaturacao(Faturacao fat, int linhas, int colunas);
/**
* produtosNaoVendidos devolve o Conjunto de produtos não vendidos.
*/
Conjunto produtosNaoVendidos(CatProds catprods, Faturacao fat);
/**
* freeTotalFat liberta o espaço alocado da estrutura TotalFat passada como argumento.
*/
void freeTotalFat(TotalFat tf);
/**
* freeTotalVendas liberta o espaço alocado da estrutura TotalVendas passada como argumento.
*/
void freeTotalVendas(TotalVendas tv);
/**
* fateVendasMesPro devolve as faturações de um produto, numa filial, num mês passados como argumentos.
*/
TotalFat fateVendasMesPro(Faturacao fa, Produto pr, int mes, int filial, TotalVendas v);
/**
* intervaloVeF devolve as faturações de um intervalo de meses passados como argumentos.
*/
TotalFat intervaloVeF(Faturacao f, int mesI, int mesS,  TotalVendas v);
/**
* produtosNaoVendidosFil devolve o conjunto de produtos não vendidos numa filial passada como argumento.
*/
Conjunto produtosNaoVendidosFil(CatProds catpr, Faturacao fat, int filial);

#endif