package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.CompraDao;
import br.com.caelum.ingresso.dao.LugarDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.Cartao;
import br.com.caelum.ingresso.model.form.CarrinhoForm;

@Controller
public class CompraController {

	@Autowired
	private LugarDao lugarDao;

	@Autowired
	private SessaoDao sessaoDao;

	@Autowired
	private Carrinho carrinho;
	
	@Autowired
	private CompraDao compraDao;

	@PostMapping("/compra/ingressos")
	public ModelAndView enviarParaPagamento(CarrinhoForm form) {

		form.toIngressos(sessaoDao, lugarDao).forEach(carrinho::adiciona);

//		carrinho.getIngressos().sort(Comparator.comparing(Ingresso::getPreco));

//		this.carrinho.getIngressos().forEach(ingresso -> System.out.println(ingresso.getSessao().getFilme().getNome()));

		return new ModelAndView("redirect:/compra");
	}

	@GetMapping("/compra")
	public ModelAndView checkout(Cartao cartao) {
		ModelAndView modelAndView = new ModelAndView("compra/pagamento");
		modelAndView.addObject("carrinho", carrinho);
		modelAndView.addObject("cartao", cartao);

		return modelAndView;
	}

	@PostMapping("compra/comprar")
	@Transactional
	public ModelAndView comprar(@Valid Cartao cartao, BindingResult result) {

		if (cartao.isValido()) {
			compraDao.save(carrinho.toCompra());
		} else {
			result.rejectValue("vencimento", "vencimento", "Vencimento inválido");
			return checkout(cartao);
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/");

		compraDao.save(carrinho.toCompra());

		return modelAndView;
	}

}
