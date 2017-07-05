package br.ufsm.csi.seguranca.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

import br.ufsm.csi.seguranca.model.Post;
import br.ufsm.csi.seguranca.model.Usuario;

@Repository
public class UsuarioDAO {

	@Autowired
	private SessionFactory sessionFactory; // Singleton da sessionFactory
	
	@Transactional
	public void inserirUsuario(Usuario usuario)throws Exception{ // Método que executa a transação de de inserção de usuário passado por parâmetro
		Session session =  sessionFactory.getCurrentSession();
		session.save(usuario); // Save o usuário
	}
	
	@Transactional
	public Usuario login(String login)throws Exception{ // Método que busca um usuário de acordo com o login passado por parâmetro informado no formulário de login
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Usuario.class);
		crit.add(Restrictions.eq("loginUsuario", login)); // Adiciona na criteria a restrição de login
		return (Usuario) crit.uniqueResult(); // Retorna usuário único de acordo com a criteria de único resultado
	}
	
		
	@Transactional
	public Collection<Usuario> getUsuarios() throws SQLException{ // Método que busca todos os usuários cadastrados no sistema
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Usuario.class);
		return crit.list(); // Retorna lista de usuários do sistema
	}
	
	@Transactional
	public Usuario buscarUsuarioPeloId(Long id) throws Exception{ // Método que busca um usuário de acordo com o identificador passado por parâmetro
		Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.get(Usuario.class, id); // Retorna um objeto usuário a partir do get com identificador chave Id
	}

	
	

}