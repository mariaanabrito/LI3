#include <stdlib.h>
#include <string.h>
#include "catalogo.h"

#define HASH 65

/**
* A struct sub contém as informações relativas a um nodo de um catálogo, nomeadamente um código, os nodos adjacentes, a altura do nodo,
* bem como tamanho dele.
*/
struct sub
{
    char* p;
    struct sub *left,*right;
    int height;
    int size;
};

Catalogo getCatalogoEsq(Catalogo a)
{
    return (a->left);
}

Catalogo getCatalogoDir(Catalogo a)
{
    return (a->right);
}

char* getCatalogoCodigo(Catalogo a)
{
    return (a->p);
}

int hash(char* c)
{
	int r;
	r = *c - HASH;
	return r;
}

int max( int l, int r)
{
    return l > r ? l: r;
}

int find(Codigo codigo, Catalogo t )
{
    if(t == NULL)
        return 1;
    if( strcmp(codigo, t->p) < 0)
        return find( codigo, t->left );
    else if( strcmp(codigo, t->p) > 0 )
        return find( codigo, t->right );
    else
        return 0;
}

int height( Catalogo n )
{
    if( n == NULL )
        return -1;
    else
        return n->height;
}

int size(Catalogo n)
{
    if (n == NULL)
        return 0;
    else
        return n->size;
}

/**
* rotacao_simples_esq faz uma rotação simples com a esquerda.
*/
Catalogo rotacao_simples_esq( Catalogo k2 )
{
    Catalogo k1 = NULL;

    k1 = k2->left;
    k2->left = k1->right;
    k1->right = k2;

    k2->height = max( height( k2->left ), height( k2->right ) ) + 1;
    k1->height = max( height( k1->left ), k2->height ) + 1;
    k2->size = size(k2->left) + size(k2->right) + 1;
    k1->size = size(k1->left) + size(k1->right) + 1;

    return k1;
}

/**
* rotacao_simples_dir faz uma rotação simpes com a direita.
*/
Catalogo rotacao_simples_dir( Catalogo k1 )
{
    Catalogo k2;

    k2 = k1->right;
    k1->right = k2->left;
    k2->left = k1;

    k1->height = max( height( k1->left ), height( k1->right ) ) + 1;
    k2->height = max( height( k2->right ), k1->height ) + 1;
    k1->size = size(k1->left) + size(k1->right) + 1;
    k2->size = size(k2->left) + size(k2->right) + 1;

    return k2;
}

/**
* rotacao_dupla_esq faz uma rotação dupla com a esquerda.
*/
Catalogo rotacao_dupla_esq( Catalogo k3 )
{
    k3->left = rotacao_simples_dir( k3->left );

    return rotacao_simples_esq( k3 );
}

/**
* rotacao_dupla_dir faz uma rotação dupla com a direita.
*/
Catalogo rotacao_dupla_dir( Catalogo k1 )
{
    k1->right = rotacao_simples_esq( k1->right );

    k1 = rotacao_simples_dir( k1 );

    return k1;
}

Catalogo insert(Catalogo t, Codigo codigo)
{
    int length;
    if( t == NULL )
    {
        t = malloc(sizeof(struct sub));
        if( t == NULL )
            return NULL;
        else
        {
            length = (int)strlen(codigo) + 1;
            t->p = malloc((long unsigned int)length*sizeof(char));
            strcpy(t->p,codigo);
            t->height = 0;
            t->size = 1;
            t->left = t->right = NULL;
        }
    }
    else if(strcmp(codigo, t->p) < 0)
    {
        t->left = insert(t->left, codigo );

        if( height( t->left ) - height( t->right ) == 2 )
        {  
            if(strcmp(codigo, t->left->p) < 0  )
                t = rotacao_simples_esq( t );
            else
                t = rotacao_dupla_esq( t );
    	}
    }
    else if(strcmp(codigo, t->p) > 0 )
    {
        t->right = insert(t->right,codigo );

        if( (height( t->right ) - height( t->left )) == 2 )
        {  
            if(strcmp(codigo, t->right->p) > 0)
                t = rotacao_simples_dir( t );
            else
                t = rotacao_dupla_dir( t );
        }
    }

    t->height = max( height( t->left ), height( t->right ) ) + 1;
    t->size = size(t->left) + size(t->right) + 1;

    return t;
}

void dispose(Catalogo t)
{
    if( t != NULL )
    {
        dispose( t->left );
        dispose( t->right );
        free( t );
    }
}

