package br.com.caelum.livraria.login;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.bean.MenuBean;
import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@RequestScoped
public class LoginBean {
	
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao dao;
	
	@Inject
	UsuarioLogadoBean usuarioLogado;
	
	@Inject
	MenuBean menu;

	public Usuario getUsuario() {
		return usuario;
	}
	
	public String efetuaLogin() {
		
		Usuario usuarioEncontrado = this.dao.buscaPeloLogin(usuario.getLogin());
		
		if(usuarioEncontrado!= null && possuiMesmaSenha(usuarioEncontrado)) {
			usuarioLogado.logar(usuarioEncontrado);
			return menu.paginaLivros();
		}
		
		criaMensagem("Usuário não encontrado!");
		limparForm();
		
		return "";
	}
	
	public String efetuaLogout() {
		this.usuarioLogado.deslogar();
		return this.menu.paginaLogin();
	}

	
	private void limparForm() {
		this.usuario = new Usuario();
	}

	private void criaMensagem(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}

	private boolean possuiMesmaSenha(Usuario usuarioEncontrado) {
		return usuarioEncontrado.getSenha().equals(usuario.getSenha());
	}
}
