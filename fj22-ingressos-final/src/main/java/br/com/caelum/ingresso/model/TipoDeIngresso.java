package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.desconto.Desconto;
import br.com.caelum.ingresso.model.desconto.DescontoParaBancos;
import br.com.caelum.ingresso.model.desconto.DescontoParaEstudantes;
import br.com.caelum.ingresso.model.desconto.DescontoParaFuncionarios;
import br.com.caelum.ingresso.model.desconto.SemDesconto;


public enum TipoDeIngresso {
	
	INTEIRO(new SemDesconto()),
	ESTUDANTE(new DescontoParaEstudantes()),
	BANCO(new DescontoParaBancos()),
	FUNCIONARIO(new DescontoParaFuncionarios())
	;
	
	private Desconto desconto;
	
	TipoDeIngresso(Desconto desconto) {
		this.desconto = desconto;
	}

	public BigDecimal aplicaDesconto(BigDecimal preco) {
		return desconto.aplicarDescontoSobre(preco);
	}

	public String getDescricao() {
		return desconto.getDescricao();
	}
	
	

}
