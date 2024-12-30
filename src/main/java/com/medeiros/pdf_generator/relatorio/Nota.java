package com.medeiros.pdf_generator.relatorio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.medeiros.pdf_generator.domain.entity.Cliente;
import com.medeiros.pdf_generator.domain.entity.Produto;
import com.medeiros.pdf_generator.domain.entity.Venda;

public class Nota implements Relatorio {

    private Venda venda;
    private Cliente cliente;
    private Document documentoPDF;
    private final String caminhoDoRelatorio;
    private Font font9;
    private PdfPTable table;

    public Nota(Venda venda) {
        cliente = venda.getCliente();
        this.venda = venda;

        this.documentoPDF = new Document(PageSize.A4);
        this.font9 = FontFactory.getFont(FontFactory.COURIER, 8);

        this.caminhoDoRelatorio = "nota" + new Date().toInstant().toEpochMilli() + ".pdf";

        try {
            PdfWriter.getInstance(this.documentoPDF, new FileOutputStream(caminhoDoRelatorio));
            this.documentoPDF.open();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gerarCabecalho() {
        Paragraph paragrafoTitulo = new Paragraph();
        paragrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        paragrafoTitulo.add(new Chunk("DOCUMENTO AUXILIAR DE VENDA\nNAO E UM DOCUMENTO FISCAL.", font9));
        this.documentoPDF.add(paragrafoTitulo);

        this.documentoPDF.add(new Paragraph("NAO E VALIDO COMO GARANTIA DE MERCADORIA", font9));
        this.documentoPDF.add(new Paragraph(
                "------------------------------------------------------------------------------------------------------------",
                font9));
        float width = documentoPDF.getPageSize().getWidth() - 72;
        float[] columnDefinitionSize = { 40F, 30F, 30F };

        table = new PdfPTable(columnDefinitionSize);
        table.getDefaultCell().setBorder(0);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setTotalWidth(width);
        table.setLockedWidth(true);

        table.addCell(new Phrase("Vendedor.: " + "JORGE", font9));
        table.addCell(new Phrase("Pedido.: " + String.format("%08d", venda.getCodigo()), font9));
        table.addCell(new Phrase(venda.getDataVenda().toString(), font9));

        table.addCell(new Phrase("CLIENTE..: " + cliente.getCodigo().toString(), font9));
        table.addCell(new Phrase(cliente.getNome(), font9));
        table.addCell(new Phrase("/ MAQUINA:", font9));

        table.addCell(new Phrase("ENDERECO.: " + cliente.getEndereco(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("CEP: " + cliente.getCep().toString(), font9));

        table.addCell(new Phrase("BAIRRO...: " + cliente.getBairro(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("CIDADE...: " + cliente.getCidade(), font9));

        table.addCell(new Phrase("ESTADO...: " + cliente.getEstado(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("TELEFONE.: " + cliente.getTelefone(), font9));

        table.addCell(new Phrase("INSCEST..: " + cliente.getInscest().toString(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("CNPJ/CPE.: " + cliente.getCnpjCpe().toString(), font9));

        try {
            this.documentoPDF.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void gerarCorpo() {
        float width = documentoPDF.getPageSize().getWidth() - 72;
        Paragraph pitensVendidos = new Paragraph();
        pitensVendidos.setAlignment(Element.ALIGN_LEFT);
        documentoPDF.add(new Paragraph(pitensVendidos));
        int tamanhoDaVenda = venda.getProdutosVendidos().size();

        table = new PdfPTable(new float[] { 8F, 54F, 7F, 6F, 12F, 12F });
        table.getDefaultCell().setBorder(0);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setTotalWidth(width);
        table.setLockedWidth(true);

        // Cabeçalhos
        PdfPCell cabecalhoCodigo = new PdfPCell(new Phrase("Código", font9));
        cabecalhoCodigo.setHorizontalAlignment(Element.ALIGN_LEFT);
        cabecalhoCodigo.setBorder(0);
        table.addCell(cabecalhoCodigo);

        PdfPCell cabecalhoDescricao = new PdfPCell(new Phrase("Descrição", font9));
        cabecalhoDescricao.setHorizontalAlignment(Element.ALIGN_LEFT);
        cabecalhoDescricao.setBorder(0);
        table.addCell(cabecalhoDescricao);

        PdfPCell cabecalhoQuantidade = new PdfPCell(new Phrase("Quant.", font9));
        cabecalhoQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
        cabecalhoQuantidade.setBorder(0);
        table.addCell(cabecalhoQuantidade);

        PdfPCell cabecalhoUnid = new PdfPCell(new Phrase("Unid.", font9));
        cabecalhoUnid.setHorizontalAlignment(Element.ALIGN_CENTER);
        cabecalhoUnid.setBorder(0);
        table.addCell(cabecalhoUnid);

        PdfPCell cabecalhoPrUnit = new PdfPCell(new Phrase("Pr.Unit.", font9));
        cabecalhoPrUnit.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cabecalhoPrUnit.setBorder(0);
        table.addCell(cabecalhoPrUnit);

        PdfPCell cabecalhoTotProduto = new PdfPCell(new Phrase("Tot.Produto", font9));
        cabecalhoTotProduto.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cabecalhoTotProduto.setBorder(0);
        table.addCell(cabecalhoTotProduto);

        try {
            this.documentoPDF.add(table);

            this.documentoPDF.add(new Paragraph(
                    "============================================================================================================",
                    font9));

            table = new PdfPTable(new float[] { 8F, 54F, 7F, 6F, 12F, 12F });
            table.getDefaultCell().setBorder(0);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.setTotalWidth(width);
            table.setLockedWidth(true);

            for (Produto produto : this.venda.getProdutosVendidos()) {
                PdfPCell codigoCell = new PdfPCell(new Phrase(String.format("%06d", produto.getCodigo()), font9));
                codigoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                codigoCell.setBorder(0);
                table.addCell(codigoCell);

                table.addCell(new Phrase(produto.getNome(), font9));

                PdfPCell cxCell = new PdfPCell(new Phrase(produto.getCx(), font9));
                cxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cxCell.setBorder(0);
                table.addCell(cxCell);

                PdfPCell quantidadeCell = new PdfPCell(new Phrase(produto.getQuantidade().toString(), font9));
                quantidadeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                quantidadeCell.setBorder(0);
                table.addCell(quantidadeCell);

                PdfPCell valorCell = new PdfPCell(new Phrase(produto.getValorFormatado(), font9));
                valorCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                valorCell.setBorder(0);
                table.addCell(valorCell);

                PdfPCell precoCell = new PdfPCell(new Phrase(produto.calcularPrecoFormatado(), font9));
                precoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                precoCell.setBorder(0);
                table.addCell(precoCell);
            }
            if (tamanhoDaVenda < 10) {
                for (int i = tamanhoDaVenda; i < 10; i++) {
                    PdfPCell codigoCell = new PdfPCell(new Phrase(" ", font9));
                    codigoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    codigoCell.setBorder(0);
                    table.addCell(codigoCell);

                    PdfPCell descricaoCell = new PdfPCell(new Phrase(" ", font9));
                    descricaoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    descricaoCell.setBorder(0);
                    table.addCell(descricaoCell);

                    PdfPCell quantidadeCell = new PdfPCell(new Phrase(" ", font9));
                    quantidadeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    quantidadeCell.setBorder(0);
                    table.addCell(quantidadeCell);

                    PdfPCell unidadeCell = new PdfPCell(new Phrase(" ", font9));
                    unidadeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    unidadeCell.setBorder(0);
                    table.addCell(unidadeCell);

                    PdfPCell precoUnitarioCell = new PdfPCell(new Phrase(" ", font9));
                    precoUnitarioCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    precoUnitarioCell.setBorder(0);
                    table.addCell(precoUnitarioCell);

                    PdfPCell totalCell = new PdfPCell(new Phrase(" ", font9));
                    totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    totalCell.setBorder(0);
                    table.addCell(totalCell);
                }
            }

            this.documentoPDF.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Paragraph pTotal = new Paragraph();
        pTotal.setAlignment(Element.ALIGN_RIGHT);
        pTotal.add(new Chunk("Total da Venda: " + this.venda.calcularValorTotalCarrinho(), font9));

        this.documentoPDF.add(pTotal);
        this.documentoPDF.add(new Paragraph(
                "------------------------------------------------------------------------------------------------------------",
                font9));

    }

    @Override
    public void gerarRodape() {
        float width = documentoPDF.getPageSize().getWidth() - 72;

        table = new PdfPTable(3);
        table.getDefaultCell().setBorder(0);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setTotalWidth(width);
        table.setLockedWidth(true);

        table.addCell(new Phrase("Obs: " + this.venda.getObs(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("", font9));

        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("ACRESCIMO......: ", font9));
        table.addCell(new Phrase("", font9));

        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("SUBTOTAL.......: " + this.venda.calcularValorTotalCarrinho(), font9));

        table.addCell(new Phrase("DESCONTO.......: ", font9));
        table.addCell(new Phrase("FRETE..........: ", font9));
        table.addCell(new Phrase("TOTAL GERAL....: " + this.venda.getTotal().toString(), font9));

        table.addCell(new Phrase("VALOR DA ENTRADA: " + venda.getValorDaEntrada().toString(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("", font9));

        table.addCell(new Phrase("FORMA DE PAGAMENTO:  " + venda.getFormaDePagamento(), font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("", font9));

        documentoPDF.add(table);

        table = new PdfPTable(4);
        table.getDefaultCell().setBorder(0);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setTotalWidth(width);
        table.setLockedWidth(true);

        table.addCell(new Phrase("DATA", font9));
        table.addCell(new Phrase("VALOR", font9));
        table.addCell(new Phrase("DATA", font9));
        table.addCell(new Phrase("VALOR", font9));

        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("", font9));
        table.addCell(new Phrase("(*) ITEM REFERENTE A TROCA", font9));
        table.addCell(new Phrase("", font9));

        documentoPDF.add(table);

        this.documentoPDF.add(new Paragraph("É vedada a autenticação deste documento.", font9));
        this.documentoPDF.add(new Paragraph("                              ---------", font9));
    }

    @Override
    public void imprimir() {
        if (this.documentoPDF != null && this.documentoPDF.isOpen()) {
            this.documentoPDF.close();
        }
    }

    public String imprimirComRetornoDoNome() {
        if (this.documentoPDF != null && this.documentoPDF.isOpen()) {
            this.documentoPDF.close();
        }

        return this.caminhoDoRelatorio;
    }
}
