package com.medeiros.pdf_generator.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.medeiros.pdf_generator.entity.Cliente;
import com.medeiros.pdf_generator.entity.Produto;
import com.medeiros.pdf_generator.entity.Venda;
import com.medeiros.pdf_generator.relatorio.Nota;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @GetMapping("/pdf-teste")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<FileSystemResource> gerarPdfDog() {

        List<Produto> produtos = new ArrayList<>();
        Venda venda = new Venda(new Faker().random().nextInt(0, 1000), "Paulo", produtos);
        Cliente cliente = new Cliente(00001036, "ACAI FRUTOS DE PARA LTDA", "RUA WELD DE SOUZA MAIA", 32421030,
                "BELA VISTA", "IBIRITE", "MG", 319975706479L, 0040581980093D, 42127082000180L);

        Faker f = new Faker();

        IntStream.range(1, 20)
                .boxed()
                .map(i -> new Produto(i, f.pokemon().name(), "CX", f.random().nextInt(1, 10), f.random().nextDouble()))
                .forEach(venda::addProdutoAoCarrinho);

        Nota relatorioSimples = new Nota(venda, cliente);

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
