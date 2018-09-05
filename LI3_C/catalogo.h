#ifndef catalogo_H
#define catalogo_H

/**
* A estrutura Codigo contém a informação relativa a um código de produto ou de cliente.
*/
typedef char* Codigo;
/**
* A estrutura catálogo contém códigos de produtos ou de clientes.
*/
typedef struct sub *Catalogo;

/**
* getCatalogoEsq devolve um catálogo.
*/
Catalogo getCatalogoEsq(Catalogo a);
/**
* getCatalogoDir devolve um catálogo.
*/
Catalogo getCatalogoDir(Catalogo a);
/**
* getCatalogoCodigo devolve um Codigo de um catálogo.
*/
Codigo getCatalogoCodigo(Catalogo a);
/**
* hash devolve o número de hash do caracter passado como argumento.
*/
int hash(char* c);
/**
* max devolve o máximo entre dois números passados como argumento.
*/
int max( int l, int r);
/**
* find devolve o resultado da procura de um código num catálogo, ambos passados como argumentos.
*/
int find(Codigo codigo, Catalogo t );
/**
* size devolve o tamanho de um catálogo.
*/
int size(Catalogo n);
/**
* height devolve a altura de um catálogo.
*/
int height( Catalogo n );
/**
* insert insere um código num catálogo, ambos passados como argumentos.
*/
Catalogo insert(Catalogo t, Codigo codigo);
/**
* dispose liberta o espaço alocado de um catálogo passado como argumento.
*/
void dispose(Catalogo t);


#endif