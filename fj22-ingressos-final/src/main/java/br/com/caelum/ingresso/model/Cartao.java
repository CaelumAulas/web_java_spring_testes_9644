package br.com.caelum.ingresso.model;

import java.time.YearMonth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Cartao {

	@Size(min = 5)
	private String cartaoDeCredito;
	
	@NotNull
	private Integer cvv;
	
	@NotNull
	private YearMonth vencimento;

	public String getCartaoDeCredito() {
		return cartaoDeCredito;
	}

	public void setCartaoDeCredito(String cartaoDeCredito) {
		this.cartaoDeCredito = cartaoDeCredito;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public YearMonth getVencimento() {
		return vencimento;
	}

	public void setVencimento(YearMonth vencimento) {
		this.vencimento = vencimento;
	}

	@Override
	public String toString() {
		return "Cartao [cartaoDeCredito=" + cartaoDeCredito + ", cvv=" + cvv + ", vencimento=" + vencimento + "]";
	}

	public boolean isValido() {
		return vencimento.isAfter(YearMonth.now());
	}
	
	

}
