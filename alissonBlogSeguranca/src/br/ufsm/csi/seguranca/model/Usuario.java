package br.ufsm.csi.seguranca.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "sequencia")
	@Column(name = "IDUSUARIO")
	private Long idUsuario;

	@Column(name = "NOMEUSUARIO", length = 50)
	private String nomeUsuario;

	@Column(name = "LOGINUSUARIO", unique = true)
	private String loginUsuario;

	@Column(name = "SENHAUSUARIO")
	private String senhaUsuario;

	@Column(name = "TIPOUSUARIO")
	private int tipoUsuario;
	
	@Column(name = "ATIVO")
	private boolean ativo;

	@Column(name = "TENTATIVAS")
	private int tentativas; 
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Collection<Post> postsUsuario;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Collection<Post> getPostsUsuario() {
		return postsUsuario;
	}

	public void setPostsUsuario(Collection<Post> postsUsuario) {
		this.postsUsuario = postsUsuario;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getTentativas() {
		return tentativas;
	}

	public void setTentativas(int tentativas) {
		this.tentativas = tentativas;
	}
	
}
