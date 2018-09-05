#ifndef clientes_H
#define clientes_H

#include "catalogo.h"

/**
* Estrutura que representa o catálogo de clientes.
*/
typedef struct clientes* CatClientes;
/**
* Estrutura que representa um cliente.
*/
typedef struct cliente* Cliente;

/*
	Função que retorna um subcatálogo de clientes relativo a uma especificação.
*/
Catalogo getSubCatalogo(CatClientes c, int h);
/**
* Função que inicializa o catálogo de clientes.
*/
CatClientes initCatClientes();
/**
* Função que obtém uma string relativa a um cliente.
*/
Codigo getCodigoCliente(Cliente c);
/**
* Função que insere um cliente no catálogo.
*/
CatClientes insereCliente(CatClientes catcl, Cliente cl);
/**
* Função que verifica se um cliente existe num catálogo.
*/
int existeCliente(CatClientes catcl, Cliente cl);
/**
* Função que obtém o número total de clientes de um catálogo.
*/
Cliente convCliente(char* linha);
/**
* Função que obtém o número total de clientes de um catálogo.
*/
int totalClientes(CatClientes catcl);
/**
* Função que obtém o número total de clientes de um subcatálogo relativo a uma letra.	
*/
int totalClientesLetra(CatClientes catcl, char* letra);
/**
* Função que liberta o espaço relativo a um catálogo de clientes.
*/
void freeClientes(CatClientes catcl);

#endif
