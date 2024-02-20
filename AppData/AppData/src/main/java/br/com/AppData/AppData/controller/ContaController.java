package br.com.AppData.AppData.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.AppData.AppData.model.Conta;
import br.com.AppData.AppData.repository.ContaRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ContaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ContaRepository cr;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String fazerLogin() {
		return "/Login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@Valid Conta conta, BindingResult result, RedirectAttributes attributes, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("conta", new Conta());
		if(result.hasErrors()) {
			mv.setViewName("/login");
		}
		
		Conta contaLogin = loginConta(conta.getNome(), conta.getSenha());
		if(contaLogin == null) {
			attributes.addFlashAttribute("mensagem erro", "Conta não encontrada");
			System.out.println("não logado");
		} else {
			session.setAttribute("contaLogada", contaLogin);
			System.out.println("Logado");
			return mv;
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = "/cadastrarConta", method = RequestMethod.GET)
	public String form() {
		return "conta/formConta";
	}
	
	@RequestMapping(value = "/cadastrarConta", method = RequestMethod.POST)
	public String form(@Valid Conta conta, BindingResult result, RedirectAttributes attributes) {
		if(cr.findByNome(conta.getNome()) != null) {
			attributes.addFlashAttribute("mensagem erro", "Essa conta já existe");
			return "redirect:/cadastrarConta";
		}
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem erro", "Preencha todos os campos");
			return "redirect:/cadastrarConta";
		}
		
		cr.save(conta);
		attributes.addFlashAttribute("mensagem", "Conta cadastrada com sucesso!");
		return "redirect:/cadastrarConta";
	}
	
	public Conta loginConta(String nome, String senha) {
		Conta contaLogin = cr.buscarConta(nome, senha);
		return contaLogin;
	}
	
	
	

}
