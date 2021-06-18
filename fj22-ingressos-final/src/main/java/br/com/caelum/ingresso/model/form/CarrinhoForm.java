package br.com.caelum.ingresso.model.form;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.ingresso.dao.LugarDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Ingresso;

public class CarrinhoForm {

	private List<Ingresso> ingressos = new ArrayList<>();

	public List<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

	public List<Ingresso> toIngressos(SessaoDao sessaoDao, LugarDao lugarDao) {
		return this.ingressos.stream().map(ingresso -> {
			return new Ingresso(sessaoDao.findOne(ingresso.getSessao().getId()), 
					ingresso.getTipoDeIngresso(),
					lugarDao.findOne(ingresso.getLugar().getId()));
		}).collect(Collectors.toList());
	}

}
