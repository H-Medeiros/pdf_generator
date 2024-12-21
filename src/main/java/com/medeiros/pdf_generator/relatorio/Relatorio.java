package com.medeiros.pdf_generator.relatorio;

public interface Relatorio {
    void gerarCabecalho();

    void gerarCorpo();

    void gerarRodape();

    void imprimir();
}
