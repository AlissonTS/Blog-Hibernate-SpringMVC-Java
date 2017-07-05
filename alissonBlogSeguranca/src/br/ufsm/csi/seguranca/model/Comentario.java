package br.ufsm.csi.seguranca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COMENTARIO")
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "sequencia")
	@Column(name = "IDCOM")
	private Long idComentario;

	@Column(name = "TITULOCOMENTARIO")
	private String tituloComentario;
	
	@Column(name = "TEXTOCOMENTARIO")
	private String textoComentario;

	@ManyToOne
	@JoinColumn(name = "IDPOST")
	private Post post;

	public Long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}

	public String getTituloComentario() {
		return tituloComentario;
	}

	public void setTituloComentario(String tituloComentario) {
		this.tituloComentario = tituloComentario;
	}

	public String getTextoComentario() {
		return textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	

}