package br.ufsm.csi.seguranca.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufsm.csi.seguranca.dao.ComentarioDAO;
import br.ufsm.csi.seguranca.dao.PostDAO;
import br.ufsm.csi.seguranca.dao.UsuarioDAO;
import br.ufsm.csi.seguranca.model.Comentario;
import br.ufsm.csi.seguranca.model.Post;
import br.ufsm.csi.seguranca.model.Usuario;

@Controller
public class PostController {

	@Autowired
	private PostDAO daoPost; // Singleton
	
	@Autowired
	private ComentarioDAO daoComentario; // Singleton
	
	@RequestMapping("GerenciaPosts.html")
	public ModelAndView gerenciaPost(HttpServletRequest request) {
		Usuario	u = (Usuario)request.getSession().getAttribute("usuario");
		ModelAndView mv = new ModelAndView("redirect:login.html"); // Caso não haja o mesmo é redirecionado para a página inicial

		if(u!=null){ // Verifica se há usuário na sessão, logado
			mv = new ModelAndView("gerenciaPost");
		}

		return mv;
	}
	
	@RequestMapping("meusPosts.html")
	public ModelAndView paginaPosts(HttpServletRequest request, Map<String, Object> model) throws SQLException{
		Usuario	u = (Usuario)request.getSession().getAttribute("usuario");
		ModelAndView mv = new ModelAndView("login");

		if(u != null){ // Se houver usuário na sessão
			Collection<Post> posts = daoPost.getPostsUsuario(u); // Lista de postagens do usuário logado no blog
			model.put("postsUsuario", posts); // Seta as postagens no model
			mv = new ModelAndView("posts"); // Página em que o usuário será direcionado
			model.put("posts", new Post()); // New Post form spring
		}
		else{
			Collection<Post> posts = daoPost.getPosts10(); // Lista dos 10 ultimos posts caso o usuário presente na sessão não seja operador ou não haver usuário na sessão
			model.put("posts", posts); // Seta postagens no model

			mv.addObject("msg", "ERRO: SEM ACESSO!");
			mv.addObject("tipo", "danger");
		}
		return mv;
	}
	
	@RequestMapping(value="cadastro-post.html", method = RequestMethod.POST)
	@Transactional
	public ModelAndView cadastro(Post post, HttpServletRequest request, Map<String, Object> model) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:login.html");
		Usuario	u = (Usuario)request.getSession().getAttribute("usuario");

		if(u!=null){ // Verifica se há usuário na sessão, logado
			mv = new ModelAndView("posts");
			if(u.isAtivo()){ // Se o usuário estiver ativo no sistema
				if(post.getIdPost() == null){ // Verificação se há passagem de id de postagem, se for null é realização de cadastro de postagem
					post.setUsuario(u); // Seta o dono da postagem, usuário presente na sessão
					Date d = new Date(new java.util.Date().getTime()); // Gera data atual
					post.setDataPost(d); // Seta data atual da criação da postagem
					daoPost.inserirPost(post); // Insere a postagem

					mv.addObject("msg", "Post cadastrado com sucesso!");
					mv.addObject("tipo", "success");

					Collection<Post> posts = daoPost.getPostsUsuario(u); // Lista de postagem do usuário logado
					model.put("postsUsuario", posts); // Seta a lista de postagens em postUsuario
					model.put("posts", new Post()); // New Post para form spring
				}
				else{ // Alteração de postagem pois o id não é null
					Post postPersiste = daoPost.getPostId(post.getIdPost()); // Busca postagem pelo id passado como parâmetro
					if(u.getTipoUsuario() == 1 || (postPersiste!=null && u.getIdUsuario().equals(postPersiste.getUsuario().getIdUsuario()))){ // Verifica se o usuário é o dono da postagem ou administrador do sistema

						postPersiste.setTituloPost(post.getTituloPost()); // Seta alteração do titulo da postagem
						postPersiste.setTextoPost(post.getTextoPost()); // seta alteração do texto de descrição da postagem
						daoPost.alterarPost(postPersiste); // Realiza a alteração da postagem

						if(u.getIdUsuario().equals(postPersiste.getUsuario().getIdUsuario())){ // Se o usuário que realizou a alteração é o dono da postagem
							Collection<Post> posts = daoPost.getPostsUsuario(u); // Busca postagens do usuário
							mv = new ModelAndView("posts");
							model.put("postsUsuario", posts); // Seta postagens do usuário no model
							model.put("posts", new Post()); // seta model posts new post() para o form spring
						}else{ // Se o usuário que realizou a alteração é o administrador
							if(u.getTipoUsuario()==1){
								Collection<Post> posts = daoPost.getPosts(); // Busca todas as postagens do blog
								mv = new ModelAndView("todosPostsAdm"); // Volta a página de todas as postagens do blog para gerenciamento pelo adm
								model.put("posts", posts);
							}
						}

						mv.addObject("msgPost", "Postagem alterada com sucesso!");
						mv.addObject("tipo", "success");
					}
					else{
						mv = new ModelAndView("redirect:login.html");
					}
				}
			}
			else{ // Se o usuário estar disativado no sistema
				mv = new ModelAndView("login");
				Collection<Post> posts = daoPost.getPosts10(); // Busca os ultimos 10 posts para ser retornado para a página inicial
				model.put("posts", posts); // Seta no model os 10 últimos posts
				mv.addObject("msg", "Sem Acesso!");
				mv.addObject("tipo", "danger");
			}
		}

		return mv;
	}
	
	@RequestMapping(value="cadastro-post.html", method = RequestMethod.GET)
	public ModelAndView carregaUsuario() throws Exception {
		ModelAndView mv = new ModelAndView("redirect:login.html"); // Redirecionamento para a página inicial do blog por conta de não aceitar cadastro e alteração de postagem via GET

		return mv;
	}
	
	@RequestMapping(value="alterarPost.html", method = RequestMethod.POST)
	public ModelAndView alterarPost(Long id, Map<String, Object> model, HttpServletRequest request) throws Exception {
		Usuario	u = (Usuario)request.getSession().getAttribute("usuario");
		ModelAndView mv = new ModelAndView("redirect:login.html");

		if(u!=null){ // Verifica se há usuário na sessão
			mv = new ModelAndView("alteraPost");
			Post post = new Post();
			post = daoPost.getPostId(id); // Busca postagem pelo id passado por parâmetro informado pelo formulário
			if(post!=null){ // Verifica se retornou uma postagem
				System.out.println(post.getUsuario().getIdUsuario());
				if(u.getTipoUsuario() == 1 || u.getIdUsuario().equals(post.getUsuario().getIdUsuario())){ // Verifica se a postagem pertence ao usuário logado ou se o mesmo é administrador do sistema
					model.put("posts", post); // Seta a postagem no formulário spring
				}
				else{ // Se não for dono da postagem ou administrador
					mv = new ModelAndView("login"); // Retorna para a página inicial
					Collection<Post> posts = daoPost.getPosts10(); // Busca as ultimas 10 postagem do blog
					model.put("posts", posts); // Seta postagens em posts para mostrar na tela inicial
					mv.addObject("msg", "Sem Acesso!");
				}
			}
		}

		return mv;
	}

	@RequestMapping(value="alterarPost.html", method = RequestMethod.GET)
	public ModelAndView alterarPost() throws Exception{
		ModelAndView mv = new ModelAndView("redirect:login.html"); // Usuário é direcionado para a página inicial por conta do carregar postagem para alteração não poder ser feita via GET

		return mv;
	}
	
	@RequestMapping(value="verPost.html", method = RequestMethod.POST)
	public ModelAndView verPost(Long id, HttpServletRequest request, Map<String, Object> model) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:login.html");

		if(id!=null){ // Verifica se o id da postagem foi recebido
			Post post = new Post();
			post = daoPost.getPostId(id); // Busca postagem pelo id recebido, passando o mesmo como parâmetro para busca

			if(post!=null){ // Se retornou a postagem a ser vista
				mv = new ModelAndView("verPost");
				model.put("post", post); // Seta postagem
				Collection<Comentario> comentarios = daoComentario.getComentariosPost(post); // Busca todos os comentários referentes a postagem buscada
				model.put("comentariosPost", comentarios); // Comentários a serem exibidos na tela
				model.put("comentarios", new Comentario()); // New Comentario no form spring
			}
		}

		return mv;
	}

	@RequestMapping(value="verPost.html", method = RequestMethod.GET)
	public ModelAndView verPost() throws Exception{
		ModelAndView mv = new ModelAndView("redirect:login.html"); // Usuário é direcionado para a página inicial por conta da visualização da posstagem não poder ser feita via GET

		return mv;
	}

	@RequestMapping(value="todosPosts.html")
	public ModelAndView todosPost(HttpServletRequest request, Map<String, Object> model) throws Exception{
		Usuario	u = (Usuario)request.getSession().getAttribute("usuario");
		ModelAndView mv;

		if(u!=null){ // Verifica se há usuário logado no sistema
			if(u.getTipoUsuario() == 1){ // Se o usuário for do tipo administrador
				mv = new ModelAndView("todosPostsAdm");
			}
			else{ // Se o usuário for normal
				mv = new ModelAndView("todosPosts");
			}
		}
		else{ // Se não houver usuário na sessão
			mv = new ModelAndView("todosPosts");
		}

		Collection<Post> posts = daoPost.getPosts(); // Busca todas as postagens cadastradas no blog e coloca em uma lista (coleção)
		model.put("posts", posts); // Seta a lista no model

		return mv;
	}
	
	@RequestMapping(value="deletaPost.html", method = RequestMethod.POST)
	public ModelAndView excluirPost(Long id, HttpServletRequest request, Map<String, Object> model) throws Exception{
		ModelAndView mv = new ModelAndView("redirect: login.html");
		Usuario	u = (Usuario)request.getSession().getAttribute("usuario");

		if(u!=null && id!=null){ // Verifica se há usuário na sessão e se o id da postagem passado pelo formulário de exclusão não é null
			Post post = new Post();
			post = daoPost.getPostId(id); // Busca postagem pelo identificador da postagem passado no formulário
			if(u.getTipoUsuario() == 1 || u.getIdUsuario().equals(post.getUsuario().getIdUsuario())){ // Verifica se o usuário logado é dono da postagem ou é administrador
				daoPost.excluirPost(post); // Realiza a exclusão da postagem

				if(u.getIdUsuario().equals(post.getUsuario().getIdUsuario())){ // Se o usuário for dono da postagem
					Collection<Post> posts = daoPost.getPostsUsuario(u); // Busca todas as postagens do usuário e coloca em uma lista (coleção)
					mv = new ModelAndView("posts");
					model.put("postsUsuario", posts); // Seta postagens do usuário para ser mostrada
					model.put("posts", new Post()); // New Post para formulário com tag spring
				}else{ // Se usuário for administrador do sistema e não for dono da postagem
					if(u.getTipoUsuario()==1){ // Adm do sistema
						Collection<Post> posts = daoPost.getPosts(); // Busca todas as postagens do blog cadastradas
						mv = new ModelAndView("todosPostsAdm");
						model.put("posts", posts); // Seta postagens do blog para serem mostradas
					}
				}
				mv.addObject("msgPost", "Postagem deletada com sucesso!");
				mv.addObject("tipo", "success");
			}
			else{
				mv = new ModelAndView("login"); // Retorna para a página inicial
				Collection<Post> posts = daoPost.getPosts10(); // Busca as ultimas 10 postagem do blog
				model.put("posts", posts); // Seta postagens em posts para mostrar na tela inicial
				mv.addObject("msg", "Sem Acesso!");
			}
		}

		return mv;
	}

	@RequestMapping(value="deletaPost.html", method = RequestMethod.GET)
	public ModelAndView excluirPost() throws Exception{
		ModelAndView mv = new ModelAndView("redirect:login.html"); // Usuário é direcionado para a página inicial por conta da exclusão de uma postagem não poder ser feita via GET

		return mv;
	}
	
}
