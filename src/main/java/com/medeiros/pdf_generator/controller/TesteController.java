package com.medeiros.pdf_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medeiros.pdf_generator.entity.Venda;
import com.medeiros.pdf_generator.service.NotaService;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private NotaService notaService;

    @PostMapping("/pdf-teste")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<FileSystemResource> testeGerarPdf(@RequestBody Venda venda) {
        return notaService.testeGerarPdf(venda);
    }
}
