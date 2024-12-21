package com.medeiros.pdf_generator.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Produto {

    private Integer codigo;
    private String nome;
    private String cx;
    private Integer quantidade;
    private Double valor;

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
