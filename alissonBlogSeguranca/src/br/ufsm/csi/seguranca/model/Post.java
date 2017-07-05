package br.ufsm.csi.seguranca.model;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "POST")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "sequencia")
	@Column(name = "IDPOST")
	private Long idPost;

	@Column(name = "TITULOPOST", length = 50)
	private String tituloPost;
	
	@Column(name = "TEXTOPOST")
	private String textoPost;
	
	@Column(name = "DATA")
	private Date dataPost;

	@ManyToOne
	@JoinColumn(name = "IDUSUARIO")
	private Usuario usuario;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Collection<Comentario> comentariosPost;

	public Long getIdPost() {
		return idPost;
	}

	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}

	public String getTextoPost() {
		return textoPost;
	}

	public void setTextoPost(String textoPost) {
		this.textoPost = textoPost;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Collection<Comentario> getComentariosPost() {
		return comentariosPost;
	}

	public void setComentariosPost(Collection<Comentario> comentariosPost) {
		this.comentariosPost = comentariosPost;
	}

	public String getTituloPost() {
		return tituloPost;
	}

	public void setTituloPost(String tituloPost) {
		this.tituloPost = tituloPost;
	}

	public Date getDataPost() {
		return dataPost;
	}

	public void setDataPost(Date dataPost) {
		this.dataPost = dataPost;
	}
	
	
	

}