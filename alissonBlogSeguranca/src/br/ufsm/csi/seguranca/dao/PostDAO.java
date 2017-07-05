package br.ufsm.csi.seguranca.dao;

import java.sql.SQLException;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.ufsm.csi.seguranca.model.Post;
import br.ufsm.csi.seguranca.model.Usuario;

@Repository
public class PostDAO {
	
	@Autowired
	private SessionFactory sessionFactory; // Singleton sessionFactory
	
	@Transactional
	public void inserirPost(Post post)throws Exception{	// Método que executa transação de inserção de postagem, sendo passada a postagem via parâmetro
		Session session = sessionFactory.getCurrentSession();
		session.save(post); // Save (inserir) a postagem
	}
	
	@Transactional
	public void alterarPost(Post post) throws Exception{ // Método que executa a transação de alteração de postagem, sendo passada a postagem via parâmetro
		Session session = sessionFactory.getCurrentSession();
		session.update(post); // Update (alterar) a postagem
	}
	
	@Transactional
	public void excluirPost(Post post) throws Exception{ // Método que executa a transação de exclusão de postagem, sendo passada a postagem via parâmetro
		Session session = sessionFactory.getCurrentSession();
		session.delete(post); // Delete (excluir) a postagem
	}
	
	@Transactional
	public Collection<Post> getPosts() throws SQLException{ // Método que busca uma lista (coleção) de postagens do blog armazenadas
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Post.class);
		return crit.list(); // Retorna lista (coleção) de todas as postagens do blog
	}

	@Transactional
	public Collection<Post> getPosts10() throws SQLException{ // Método que busca uma lista (coleção) das 10 últimas postagens (ordem decrescente) feitas no blog
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Post.class);
		crit.addOrder(Order.desc("idPost"));
		crit.setMaxResults(10);
		return crit.list(); // Criteria com ordem decrescente e maximo de resultados 10
	}
	
	@Transactional
	public Collection<Post> getPostsUsuario(Usuario usuario) throws SQLException{ // Método que busca uma lista de postagens referentes ao usuário passado como parâmetro
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Post.class);
		crit.add(Restrictions.eq("usuario", usuario)); // Criteria com restrição de apenas postagens do usuário passado como parâmetro
		return crit.list();
	}
	
	@Transactional
	public Post getPostId(Long id) throws Exception{ // Método que busca uma postagem de acordo com o identificador da postagem passado via parâmetro
		Session session = sessionFactory.getCurrentSession();
		return session.get(Post.class, id); // Retorna objeto post
	}

}
