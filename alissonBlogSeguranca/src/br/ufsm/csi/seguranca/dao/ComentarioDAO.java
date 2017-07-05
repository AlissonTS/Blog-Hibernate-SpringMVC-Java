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
import br.ufsm.csi.seguranca.model.Comentario;;

@Repository
public class ComentarioDAO {

	@Autowired
	private SessionFactory sessionFactory; // Singleton

	@Transactional
	public void inserirComentario(Comentario comentario)throws Exception{ // Método que executa transação de inserir um comentário em uma postagem
		Session session = sessionFactory.getCurrentSession();
		session.save(comentario); // Save o comentário
	}

	@Transactional
	public Collection<Comentario> getComentariosPost(Post post) throws SQLException{ // Método que retorna uma lista (coleção) de comentários de acordo com a postagem passada por parâmetro
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Comentario.class); // Instancia criteria
		crit.add(Restrictions.eq("post", post)); // Adiciona como restrição para a criteria a busca de comentários apenas do post passado como parâmetro
		return crit.list(); // Retorna uma lista de acordo com a criteria com restrição de buscar apenas comentários daquela postagem
	}

}
