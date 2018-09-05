CC= gcc
CFLAGS= -ansi -pedantic -Wall -Wconversion -Wextra -O2 -g
FILES= prods.h prods.c clientes.c clientes.h catalogo.h catalogo.c vendas.h vendas.c lista.h lista.c \
catalogoff.h catalogoff.c faturacao.h faturacao.c filial.h filial.c queries.c queries.h main.c
OUT= gerevendas

gerevendas: $(FILES)
	$(CC) $(CFLAGS) -o $(OUT) $(FILES)

clean:
	rm -f *.o $(OUT)