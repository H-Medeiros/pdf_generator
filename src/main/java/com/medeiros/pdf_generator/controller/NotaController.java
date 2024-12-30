package com.medeiros.pdf_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medeiros.pdf_generator.domain.entity.Venda;
import com.medeiros.pdf_generator.service.NotaService;

import java.io.File;

@RestController
@RequestMapping("/api/v1/nota")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @PostMapping("/pdf-generator")
    public ResponseEntity<FileSystemResource> gerarPdf(@RequestBody Venda venda) {
        String nomeArquivo = notaService.gerarPdf(venda);

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
