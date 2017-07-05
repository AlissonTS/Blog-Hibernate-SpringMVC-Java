package br.ufsm.csi.seguranca.controller;

import br.ufsm.csi.seguranca.dao.PostDAO;
import br.ufsm.csi.seguranca.dao.UsuarioDAO;
import br.ufsm.csi.seguranca.model.Post;
import br.ufsm.csi.seguranca.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;
// import sun.misc.BASE64Decoder;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;


/**
 * Created by cpol on 29/05/2017.
 */
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioDAO dao; // Singleton

    @Autowired
    private PostDAO daoPost; // Singleton

    @RequestMapping("login.html")
    @Transactional
    public ModelAndView login(String login, String senha, String form, HttpServletRequest request, Map<String, Object> model) throws SQLException {
        ModelAndView mv = new ModelAndView("login"); // Página de retorno
        Collection<Post> posts = daoPost.getPosts10(); // Busca os 10 ultimos posts da página para mostrar na página inicial do blog
        model.put("posts", posts);

        if (form != null && login != null && login.length() > 0 && senha != null && senha.length() > 0) { // Verifica se há ação de realização de login no formulário e os seus campos estão preenchidos
            try {
                Usuario u = dao.login(login); // Busca usuário pelo login informado no formulário de login
                if (u != null) { // Se retornar o usuário entra no if
                    byte[] senhaByte = senha.getBytes(); // transforma a senha informada em um array de bytes

                    MessageDigest md = MessageDigest.getInstance("SHA-256"); // Criptografia SHA-256
                    byte[] hashSenha = md.digest(senhaByte); // Criptografa a senha em um array de bytes

                    byte[] hashSenhaBase = Base64.encodeBase64(hashSenha); // Codifica para base 64
                    String valorSenha = new String(hashSenhaBase, "ISO-8859-1"); // Passa para String

                    if (valorSenha.equals(u.getSenhaUsuario())){ // Verifica se a senha informada no formulário de login é igual a senha do usuário retornado do dao
                        u.setTentativas(0); // Seta tentativas de login em zero
                        if (u.isAtivo()) { // Verifica se o usuário está ativo no sistema
                            request.getSession().invalidate();
                            request.getSession().setAttribute("usuario", u); // Seta usuário na sessão
                            if (u.getTipoUsuario() == 1) { // Se usuário administrador do sistema
                                mv = new ModelAndView("paginaAdm");
                            } else if (u.getTipoUsuario() == 2) {
                                mv = new ModelAndView("paginaUser");
                            } else {
                                mv = new ModelAndView("erro");
                            }
                        } else { // Se usuário estiver desativado o mesmo não entra no sistema
                            System.out.println("Erro ao realizar login: Usuario Desativado");
                            mv.addObject("msg", "Erro ao realizar login: Usuario Desativado");
                            mv.addObject("tipo", "danger");
                        }
                    }else{ //  Se for diferente as duas senhas
                        System.out.println("Senhas diferentes!");
                        if(u.getTentativas()>3){ // Se tentativas for maior que 5 o usuário não consegue entrar no sistema
                            u.setAtivo(false);
                            System.out.println("Senhas diferentes + 3 Tentativas!");
                            mv.addObject("msg", "Erro ao realizar login! Usuário Desativado");
                            mv.addObject("tipo", "danger");
                        }else{
                            System.out.println("Senhas diferentes! Menos de Três tentativas");
                            u.setTentativas(u.getTentativas() + 1);
                            mv.addObject("msg", "Erro ao realizar login!");
                            mv.addObject("tipo", "danger");
                        }
                    }
                } else { // Usuário não encontrado
                    mv.addObject("msg", "Erro ao realizar login!");
                    mv.addObject("tipo", "danger");
                }

            } catch (Exception e) {
            }
        }
        System.out.println("Retornou");
        return mv;
    }

    @RequestMapping("logout.html")
    public ModelAndView logout(HttpServletRequest request){
        ModelAndView mv =new ModelAndView("redirect:login.html");
        request.getSession().invalidate(); // Invalida a sessão e o logout do usuário é feito
        return mv;
    }

    @RequestMapping("GerenciaUsuarios.html")
    public ModelAndView paginaUsuarios(HttpServletRequest request, Map<String, Object> model) throws SQLException{
        Usuario	u = (Usuario)request.getSession().getAttribute("usuario");
        ModelAndView mv = new ModelAndView("redirect:login.html");

        if(u!=null){ // Verifica se tem usuário na sessão
            if(u.getTipoUsuario() == 1){ // Se o usuário for administrador ele pode entrar e administrar os usuários cadastrados
                Collection<Usuario> usuarios = dao.getUsuarios();
                model.put("usuariosCadastrados", usuarios);
                model.put("usuarios", new Usuario());

                mv = new ModelAndView("usuarios");

            }else if(u.getTipoUsuario() == 2) { // Se o usuário for usuário normal ele não pode entrar no gerenciamento de usuários do sistema
                mv = new ModelAndView("paginaUser");
                // mv.addObject("msg", "ERRO: TAREFA DO ADMINISTRADOR!");
            }
        }

        return mv;
    }

    @RequestMapping(value="cadastro-usuario.html", method = RequestMethod.POST)
    @Transactional
    public ModelAndView cadastro(Usuario usuario, HttpServletRequest request, Map<String, Object> model) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:login.html");
        Usuario	u = (Usuario)request.getSession().getAttribute("usuario");

        if(u!=null){ // Verifica se tem usuário na sessão
            if(u.getTipoUsuario() == 1){ // Verifica se o usuário é administrador do sistema
                mv = new ModelAndView("usuarios");

                byte[] senhaByte = usuario.getSenhaUsuario().getBytes(); // transforma a senha informada em um array de bytes

                MessageDigest md = MessageDigest.getInstance("SHA-256"); // Criptografia SHA-256
                byte[] hashSenha = md.digest(senhaByte); // Criptografa a senha em um array de bytes

                byte[] hashSenhaBase = Base64.encodeBase64(hashSenha); // Codifica para base 64
                String valorSenha = new String(hashSenhaBase, "ISO-8859-1"); // Passa para String

                usuario.setSenhaUsuario(valorSenha); // Seta a senha do novo usuário

                usuario.setAtivo(true); // Seta o usuário como ativo no sistema
                usuario.setTentativas(0); // Seta o número de tentativas erradas de realização de acesso em 0
                usuario.setTipoUsuario(2); // Seta o tipo de usuário como 2 (normal)

                dao.inserirUsuario(usuario); // Insere o usuário no sistema
                mv.addObject("msg", "Usuário cadastrado com sucesso!");
                mv.addObject("tipo", "success");

                Collection<Usuario> usuarios = dao.getUsuarios(); // Busca os usuários cadastrados no sistema
                model.put("usuariosCadastrados", usuarios); // Coloca os usuários no model
                model.put("usuarios", new Usuario()); // Seta new Usuario no form spring
            }
            else{ // Se usuário não for administrador o mesmo vai ser direcionado para a página de login
                mv = new ModelAndView("login");
                Collection<Post> posts = daoPost.getPosts10();
                model.put("posts", posts);
                mv.addObject("msg", "Erro: SEM ACESSO!");
                mv.addObject("tipo", "danger");
            }
        }

        return mv;
    }

    @RequestMapping(value="cadastro-usuario.html", method = RequestMethod.GET)
    public String carregaUsuario(Map<String, Object> model) throws Exception {
        model.put("usuarios", new Usuario());
        return "redirect:login.html"; // Redirect para a página inicial por não aceitar cadastro de usuário com requisição GET
    }

    @RequestMapping(value="ativaUsuario.html", method = RequestMethod.POST)
    @Transactional
    public ModelAndView ativaUsuario(String id, HttpServletRequest request, Map<String, Object> model) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:login.html");
        Usuario	us = (Usuario)request.getSession().getAttribute("usuario");
        Usuario u = dao.buscarUsuarioPeloId(Long.valueOf(id));

        if(us!=null && us!=null){ // Verifica se há usuário presente na sessão e se foi passado o identificador do usuário a ser ativado ou desativado
            if(us.getTipoUsuario() == 1){ // Se o usuário da sessão for administrador
                mv = new ModelAndView("usuarios");

                if(u.isAtivo()){ // Se o usuário buscado estiver ativado
                    u.setAtivo(false); // Desativa
                    mv.addObject("msgUsuario", "Usuário desativado com sucesso!");
                }
                else{ // Se o usuário buscado estiver desativado
                    u.setAtivo(true); // Ativa
                    mv.addObject("msgUsuario", "Usuário ativado com sucesso!");
                }
                Collection<Usuario> usuarios = dao.getUsuarios(); // Lista de usuários do blog
                model.put("usuariosCadastrados", usuarios); // Seta a lista em usuariosCadastrados
                model.put("usuarios", new Usuario()); // New Usuário para o form spring

                mv.addObject("tipo", "success");
            }
            else{ // Se o usuário for usuário normal ele é jogado para a página inicial da aplicação
                mv = new ModelAndView("login");
                Collection<Post> posts = daoPost.getPosts10();
                model.put("posts", posts);
                mv.addObject("msg", "Erro: SEM ACESSO!");
            }
        }

        return mv;
    }

    @RequestMapping(value="ativaUsuario.html", method = RequestMethod.GET)
    @Transactional
    public ModelAndView ativaUsuario() throws Exception{
        ModelAndView mv = new ModelAndView("redirect:login.html"); // Redirecionar para a página inicial do sistema por conta de não aceitar requisição GET para ativação/desativação do usuário

        return mv;
    }

    @RequestMapping("paginaAdm.html")
    public ModelAndView paginaAdm(HttpSession session, Map<String, Object> model) throws SQLException {
        Usuario	us = (Usuario)session.getAttribute("usuario");

        ModelAndView mv = new ModelAndView("login");
        Collection<Post> posts = daoPost.getPosts10(); // Lista dos 10 ultimos posts caso o usuário presente na sessão não seja administrador ou não haver usuário na sessão
        model.put("posts", posts); // Seta postagens no model

        if(us!=null){ // Verifica se há usuário na sessão
            if(us.getTipoUsuario() == 1){ // Verifica se o usuário presente na sessão é administrador
                mv = new ModelAndView("paginaAdm");
                return mv; // Direcionado para a página do administrador
            }
            else{
                mv.addObject("msg", "Erro: SEM ACESSO!"); // Caso seja usuário normal ele não possui acesso à página no administrador
            }
        }

        return mv;
    }

    @RequestMapping("paginaUser.html")
    public ModelAndView paginaUser(HttpSession session, Map<String, Object> model) throws SQLException {
        Usuario	us = (Usuario)session.getAttribute("usuario");

        ModelAndView mv = new ModelAndView("login");
        Collection<Post> posts = daoPost.getPosts10(); // Lista dos 10 ultimos posts caso o usuário presente na sessão não seja operador ou não haver usuário na sessão
        model.put("posts", posts); // Seta postagens no model

        if(us!=null){ // Verifica se há usuário na sessão
            if(us.getTipoUsuario() == 2){ // Verifica se o usuário presente na sessão é usuário normal
                mv = new ModelAndView("paginaUser");
                return mv; // Direcionado para a página do usuário normal
            }
            else{
                mv.addObject("msg", "Erro: SEM ACESSO!"); // Caso o usuário presente na sessão não seja usuário normal
            }
        }

        return mv;
    }
}
