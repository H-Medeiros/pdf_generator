package com.medeiros.pdf_generator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    public Integer codigo;
    public String nome;
    public String endereco;
    public Integer cep;
    public String bairro;
    public String cidade;
    public String estado;
    public Long telefone;
    public Double inscest;
    public Long cnpjCpe;

    public Cliente(Integer codigo, String nome, String endereco, Integer cep, String bairro, String cidade,
            String estado, String telefone, String inscest, String cnpjCpe) {
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = Long.parseLong(telefone);
        this.inscest = Double.parseDouble(inscest);
        this.cnpjCpe = Long.parseLong(cnpjCpe);
    }

}
