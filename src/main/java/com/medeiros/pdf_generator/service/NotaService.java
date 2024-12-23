package com.medeiros.pdf_generator.service;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medeiros.pdf_generator.entity.Venda;
import com.medeiros.pdf_generator.relatorio.Nota;

@Service
public class NotaService {

    public ResponseEntity<FileSystemResource> testeGerarPdf(Venda venda) {

        Nota relatorioSimples = new Nota(venda);

        relatorioSimples.gerarCabecalho();
        relatorioSimples.gerarCorpo();
        relatorioSimples.gerarRodape();
        String nomeArquivo = relatorioSimples.imprimirComRetornoDoNome();

        File file = new File(nomeArquivo);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        FileSystemResource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento.pdf");
        return ResponseEntity.ok().headers(headers).body(resource);

    }

}