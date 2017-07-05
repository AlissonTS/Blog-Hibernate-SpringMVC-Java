package br.ufsm.csi.seguranca.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufsm.csi.seguranca.dao.ComentarioDAO;
import br.ufsm.csi.seguranca.dao.PostDAO;
//import br.ufsm.csi.dao.PostDAO;
import br.ufsm.csi.seguranca.dao.UsuarioDAO;
import br.ufsm.csi.seguranca.model.Comentario;
import br.ufsm.csi.seguranca.model.Post;
import br.ufsm.csi.seguranca.model.Usuario;

import org.springframework.web.util.HtmlUtils;

@Controller
public class ComentarioController {

	@Autowired
	private ComentarioDAO daoComentario; // Singleton
	
	@Autowired
	private PostDAO daoPost; // Singleton
	
	@RequestMapping(value="cadastro-comentario.html", method = RequestMethod.POST) // Mapeamento de requisição POST para cadastro de comentário em uma postagem
	@Transactional
	public ModelAndView cadastro(Long idPost, Comentario comentario, Map<String, Object> model) throws Exception{
		ModelAndView mv = new ModelAndView("verPost"); // Página de Retorno
		Post post = new Post();
		post = daoPost.getPostId(idPost); // Busca postagem pelo id identificador passado como parâmetro

		if(post!=null){ // Verifica se retornou a postagem
			comentario.setPost(post); // Seta a postagem em que o comentário pertence
			daoComentario.inserirComentario(comentario); // Insere o comentário com os dados do comentário e a postagens vinculada

			post = daoPost.getPostId(idPost); // Busca postagem pelo id identificador passado como parâmetro
			model.put("post", post); // Seta no model a postagem a fim da mesma ser mostrada na página de retorno após a realização da inserção do comentário
			Collection<Comentario> comentarios = daoComentario.getComentariosPost(post); // Busca a lista de todos comentários da postagem em que o comentário foi inserido
			model.put("comentariosPost", comentarios); // Seta no model os comentários da postagem
			model.put("comentarios", new Comentario()); // New comentário no command do formulário com tag form do spring

			mv.addObject("msg", "Comentário cadastrado com sucesso!"); // Mensagem de comentário cadastrado com sucesso
			mv.addObject("tipo", "success");
		}else{
			mv = new ModelAndView("redirect:login.html"); // Página de Retorno
		}

		return mv;
	}
	
	@RequestMapping(value="cadastro-comentario.html", method = RequestMethod.GET) // Mapeamento de requisição GET de cadastro de comentário em uma postagem
	public String carregaUsuario(Map<String, Object> model) throws Exception {		
			model.put("comentarios", new Comentario());												
		return "redirect:login.html"; // Retorna para a página de login, não sendo feito o cadastro do comentário.
	}
	
}
