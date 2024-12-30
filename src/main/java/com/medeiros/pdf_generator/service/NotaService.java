package com.medeiros.pdf_generator.service;

import org.springframework.stereotype.Service;

import com.medeiros.pdf_generator.domain.entity.Venda;
import com.medeiros.pdf_generator.relatorio.Nota;

@Service
public class NotaService {

    public String gerarPdf(Venda venda) {

        Nota relatorioSimples = new Nota(venda);

        relatorioSimples.gerarCabecalho();
        relatorioSimples.gerarCorpo();
        relatorioSimples.gerarRodape();
        return relatorioSimples.imprimirComRetornoDoNome();
    }
}
