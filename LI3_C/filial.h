#ifndef filial_H
#define filial_H

#include "clientes.h"
#include "vendas.h"
#include "lista.h"
#include "prods.h"
#include "catalogo.h"

/**
* Estrutura que contém todos os dados de uma filial ao longo de um ano.
*/
typedef struct filial *Filial;
/**
* Estrutura que contém valores relativos a vários produtos e às respectivas quantidades.
*/
typedef struct quantidade *QuantProds;
/**
* Estrutura que contém valores relativos a vários produtos e à respectiva faturação.
*/
typedef struct prodsfat *ProdsFat;

/**
* Função que inicializa uma filial.
*/
Filial initFilial();
/**
* Função que liberta o espaço relativo a uma filial.
*/
void freeFilial(Filial fil);
/**
* Função que insere um cliente e as suas compras num dado mês numa filial.
*/
Filial insereFilial(Filial f, Codigo cl, Vendas v, int mes);
/**
* Função que conta quantos clientes não realizaram nenhuma compra em todas as filiais.
*/
int quantosClientesNenhumaCompra(CatClientes catcl, Filial f1, Filial f2, Filial f3);
/**
* Função que retorna um conjunto com os clientes que fizeram compras em todas as filiais.
*/
Conjunto clientesTodasFiliais(CatClientes catcl, Filial f1, Filial f2, Filial f3);
/**
* Função que atualiza os dados do cliente, caso tenha feito compras na filial.
*/
int procuraClienteProdsTotal(Codigo cliente, Filial f, int mes);
/**
* Função que dado um cliente e um mês retorna o total de quantidades compradas numa filial.
*/
Conjunto procuraProdutoFilial(Codigo produto, Filial f, char* modo);
/**
* Função que preenche a estrutura ProdsFat com produtos e a sua informação relativa à faturação.
*/
ProdsFat preencheProdsFat(Codigo cliente, Filial f, ProdsFat pf);
/**
*Função que coloca num conjunto os três produtos com maior valor de faturação.
*/
Conjunto top3Produtos(ProdsFat pf);
/**
* Função que coloca num conjunto os produtos que mais foram comprados por um cliente num mês e em todas as filiais. 
*/
Conjunto produtosMaisCompradosCli(Filial f1, Filial f2, Filial f3, Cliente cl, int mes, int tamP);
/**
* Função que preenche uma estrutura ProdsFat com as faturações das filiais dos argumentos.
*/
ProdsFat preencheProdsTotFat(Filial f1, Filial f2, Filial f3, ProdsFat pf);
/**
* Função que devolve um Conjunto dos N produtos mais vendidos.
*/
Conjunto topNProdutos(ProdsFat pf, int N);
/**
* Função que inicializa a estrutura ProdsFat.
*/
ProdsFat initProdsFat(int size);
/**
* Função que devolve o índice da estrutura ProdsFat.
*/
int getIndice(ProdsFat pf);
/**
* Função que incrementa o índice da estrutura ProdsFat.
*/
void incIndice(ProdsFat pf);
/**
* Função que actualiza a estrutura ProdsFat com o novo valor de faturação.
*/
void setFaturacaoProdsFat(ProdsFat pf, double fat);
/**
* Função que actualiza a estrutura ProdsFat com o novo produto.
*/
void setProdutoProdsFat(ProdsFat pf, Codigo prod);
/**
* Função que retorna um valor de faturação.
*/
double getFaturacaoProdsFat(ProdsFat pf, int i);
/**
* Função que retorna um código relativo ao produto.
*/
Codigo getProdutoProdsFat(ProdsFat pf, int i);
/**
* Devolve o índice da estrutura ProdsFat.
*/
int getIndice(ProdsFat pf);
/**
* Função que atualiza a faturação de uma posição da estrutura ProdsFat.
*/
void somaFaturacaoProdsFat(ProdsFat pf, double fat, int index);
/**
* Função que liberta o espaço alocado da estrutura ProdsFat.
*/
void freeProdsFat(ProdsFat pf);

#endif


