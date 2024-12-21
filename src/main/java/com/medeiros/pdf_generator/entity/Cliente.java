package com.medeiros.pdf_generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cliente {

    public Integer codigo;
    public String nome;
    public String endereço;
    public Integer cep;
    public String bairro;
    public String cidade;
    public String estado;
    public Long telefone;
    public Double inscest;
    public Long cnpjCpe;

}
