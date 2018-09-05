#ifndef lista_H
#define lista_H

#include "catalogo.h"

/**
* A estrutura Conjunto é uma lista de elementos com um dado tamanho, que pode ser navegável.
*/
typedef struct conj *Conjunto;
/**
* A estrutura Pagina corresponde a um subconjunto de um Conjunto, que permite a navegação.
*/
typedef struct pagina *Pagina;

/**
* criarString aloca espaço para o conteúdo passado como argumento. Devolve a posição indicada pelo index já com espaço alocado.
*/
Codigo criarString(Conjunto c, int index, Codigo conteudo);
/**
* getNextElemento devolve o elemento na posição index de um conjunto.
*/
Codigo getNextElemento(Conjunto c, int index);
/**
* initConj inicializa um conjunto com o tamanho passado como argumento.
*/
Conjunto initConj(int tamConj);
/**
* initPage inicializa uma página com o tamanho passado com argumento.
*/
Pagina initPage(int psize);
/**
* getPaginaSize devolve o tamanho de uma página.
*/
int getPaginaSize(Pagina p);
/**
* getPaginaIndex devolve o índice atual de uma página.
*/
int getPaginaIndex(Pagina p);
/**
* getPaginaAtual devolve a página atual de um conjunto.
*/
int getPaginaAtual(Conjunto c);
/**
* getElementosTotal devolve o número total de elemento de um conjunto.
*/
int getElementosTotal(Conjunto c);
/**
* getPaginasTotal devolve o número total de páginas de um conjunto.
*/
int getPaginasTotal(Conjunto c);
/**
* setPaginaAtual estabelece o número de página passado como argumento como sendo o número de página atual.
*/
void setPaginaAtual(Conjunto c, int page);
/**
* getNextString devolve o próxima elemento de uma página.
*/
Codigo getNextElementoPage(Pagina p);
/**
* roundup arrendonda para cima o número passado como argumento.
*/
int roundup(int n);
/**
* getPaginaNext devolve a próxima página de um conjunto, se existir.
*/
Pagina getPaginaNext(Conjunto c);
/**
* getPaginaAnt devolve a página anterior de um conjunto, se existir.
*/
Pagina getPaginaAnt(Conjunto c);
/**
* getPaginaP devolve a página numerada com o número passado por argumento, se existir.
*/
Pagina getPaginaP(Conjunto c, int pagina);
/**
* inOrder faz uma travessia in order de um Catálogo.
*/
Conjunto inOrder(Catalogo a);
/**
* freeConjunto liberta o espaço alocado do conjunto passado como argumento.
*/
void freeConjunto(Conjunto c);
/**
* freePage liberta o espaço alocado da página passada como argumento.
*/
void freePage(Pagina p);

#endif