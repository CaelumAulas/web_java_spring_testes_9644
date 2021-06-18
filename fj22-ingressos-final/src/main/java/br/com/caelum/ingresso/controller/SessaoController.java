package br.com.caelum.ingresso.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.client.OmdbClient;
import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

@Controller
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@Autowired
    private OmdbClient client;
	
	@Autowired
	private Carrinho carrinho;
	
	@GetMapping("admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");
		modelAndView.addObject("filmes", filmeDao.findAll());
		modelAndView.addObject("sala", salaDao.findOne(salaId));
		modelAndView.addObject("form", form);
		
		return modelAndView;
	}
	
	@PostMapping("admin/sessao")
	@Transactional
	public ModelAndView salvar(@Valid SessaoForm form, BindingResult result) {
		
		if (result.hasErrors()) {
			form(form.getSalaId(), form);
		}
		
		Sessao novaSessao = form.toSessao(filmeDao, salaDao);
		
		List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(novaSessao.getSala());
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		
		if (gerenciador.cabe(novaSessao)) {
			sessaoDao.save(novaSessao);
			return new ModelAndView("redirect:/admin/sala/"+ form.getSalaId() +"/sessoes");
		}
		
		return form(form.getSalaId(), form);
	}
	
	
	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId) {
		
		Sessao sessao = sessaoDao.findOne(sessaoId);
		
		Optional<ImagemCapa> imagemCapaOptional = client.request(sessao.getFilme(), ImagemCapa.class);
		
		ModelAndView modelAndView = new ModelAndView("sessao/lugares");
		modelAndView.addObject("sessao", sessao);
		modelAndView.addObject("carrinho", carrinho);
		modelAndView.addObject("imagemCapa", imagemCapaOptional.orElse(new ImagemCapa()));
		modelAndView.addObject("tiposDeIngressos", TipoDeIngresso.values());
		
		return modelAndView;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
