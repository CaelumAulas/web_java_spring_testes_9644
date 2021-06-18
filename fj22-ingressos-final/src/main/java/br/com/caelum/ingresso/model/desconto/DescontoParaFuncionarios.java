package br.com.caelum.ingresso.model.desconto;

import java.math.BigDecimal;

public class DescontoParaFuncionarios implements Desconto {

	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		return precoOriginal.multiply(new BigDecimal("0.8"));
	}

	@Override
	public String getDescricao() {
		return "Funcionario";
	}
	
}
