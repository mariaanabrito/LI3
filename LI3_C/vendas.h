#ifndef vendas_H
#define vendas_H

/**
*A estrutura Vendas contém informação relativa ao preço, ao mês, à filial, à quantidade, ao cliente, ao produto e ao tipo 
* de compra de uma determinada venda.
*/
typedef struct vendas *Vendas;

/**
* Função que coloca o tipo de promoção, N(normal) ou P(Promoção) na estrutura Vendas.
*/
void setModo(Vendas v, char* m);
/**
* Função que coloca um cliente na estrutura Vendas.
*/
void setCliente(Vendas v, char* c);
/**
* Função que coloca um produto na estrutura Vendas.
*/
void setProd(Vendas v, char* p);
/**
* Inicializa a estrutura Vendas.
*/
Vendas initVendas();
/**
* Liberta o espaço relativo à estrutura Vendas.
*/
void freeVendas(Vendas v);
/**
* Função que retorna o tipo de promoção da estrutura Vendas.
*/
char* getModo(Vendas v);
/**
* Função que retorna o cliente da estrutura Vendas.
*/
char* getCliente(Vendas v);
/*
* Função que retorna o produto da estrutura Vendas.
*/
char* getProd(Vendas v);
/**
* Função que coloca um preço na estrutura Vendas.
*/
void setPreco(Vendas v, double p);
/**
* Função que coloca uma filial na estrutura Vendas.
*/
void setFilial(Vendas v, int f);
/**
* Função que coloca um mês na estrutura Vendas.
*/
void setMes(Vendas v, int m);
/**
* Função que coloca uma quantidade na estrutura Vendas.
*/
void setQuantos(Vendas v, int q);
/**
* Função que retorna o preço da estrutura Vendas.
*/
double getPreco(Vendas v);
/**
* Função que retorna o mês da estrutura Vendas.
*/
int getFilial(Vendas v);
/**
* Função que retorna o mês da estrutura Vendas.
*/
int getMes(Vendas v);
/**
* Função que retorna a quantidade da estrutura Vendas.
*/
int getQuantos(Vendas v);

#endif