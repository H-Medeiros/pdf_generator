package com.medeiros.pdf_generator.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Produto {

    private Integer codigo;
    private String nome;
    private String cx;
    private Integer quantidade;
    private Double valor;

    public Produto(Integer codigo, String nome, String cx, Integer quantidade, Double valor) {
        this.codigo = codigo;
        this.nome = nome;
        this.cx = cx;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Double calcularPreco() {
        return this.quantidade * this.valor;
    }

    public String getCodigoFormatado() {
        DecimalFormat df = new DecimalFormat("######"); // Formato para 6 dígitos inteiros
        return df.format(codigo); // Supõe que 'codigo' é uma variável numérica
    }

    public String getValorFormatado() {
        BigDecimal valorFormatado = new BigDecimal(valor).setScale(2, RoundingMode.HALF_DOWN);
        return valorFormatado.toString();
    }

    public String calcularPrecoFormatado() {
        BigDecimal precoFormatado = new BigDecimal(calcularPreco()).setScale(2, RoundingMode.HALF_DOWN);
        return precoFormatado.toString();
    }
}
