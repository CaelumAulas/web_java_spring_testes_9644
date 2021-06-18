package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Carrinho {

	private List<Ingresso> ingressos = new ArrayList<>();

	public void adiciona(Ingresso ingresso) {
		this.ingressos.add(ingresso);
	}

	public List<Ingresso> getIngressos() {
		return ingressos;
	}

	public boolean isSelecionado(Lugar lugar) {
		return this.ingressos.stream().map(Ingresso::getLugar).anyMatch(obj -> {
			return obj.equals(lugar);
		});
	}

	public BigDecimal getTotal() {
		return this.ingressos.stream().map(Ingresso::getPreco).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public Compra toCompra() {
		return new Compra(ingressos);
	}

	public void limpa() {
		this.ingressos.clear();
	}

}
