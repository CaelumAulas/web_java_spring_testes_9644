package br.com.caelum.ingresso.model.desconto;

import java.math.BigDecimal;

public class DescontoParaEstudantes implements Desconto {

	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		return precoOriginal.multiply(new BigDecimal("0.5"));
	}
	
	@Override
	public String getDescricao() {
		return "Estudante";
	}

}
