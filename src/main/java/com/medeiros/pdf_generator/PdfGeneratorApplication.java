package com.medeiros.pdf_generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.medeiros.pdf_generator.entity.Cliente;
import com.medeiros.pdf_generator.entity.Produto;
import com.medeiros.pdf_generator.entity.Venda;
import com.medeiros.pdf_generator.relatorio.Nota;
import com.medeiros.pdf_generator.relatorio.Relatorio;

@SpringBootApplication
public class PdfGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfGeneratorApplication.class, args);

		List<Produto> produtos = new ArrayList<>();
		Venda venda = new Venda(new Faker().random().nextInt(0, 1000), "Paulo", produtos);
		Cliente cliente = new Cliente(00001036, "ACAI FRUTOS DE PARA LTDA", "RUA WELD DE SOUZA MAIA", 32421030,
				"BELA VISTA", "IBIRITE", "MG", 319975706479L, 0040581980093D, 42127082000180L);

		Faker f = new Faker();

		IntStream.range(1, 20)
				.boxed()
				.map(i -> new Produto(i, f.pokemon().name(), "CX", f.random().nextInt(1, 10), f.random().nextDouble()))
				.forEach(venda::addProdutoAoCarrinho);

		Relatorio relatorioSimples = new Nota(venda, cliente);

		relatorioSimples.gerarCabecalho();
		relatorioSimples.gerarCorpo();
		relatorioSimples.gerarRodape();
		relatorioSimples.imprimir();
	}

}
