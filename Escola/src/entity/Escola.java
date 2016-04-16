package entity;

import java.sql.Date;

public class Escola {

	private long id;
	private String nome;
	private int qtdProfessores;
	private int qtdAlunos;
	private Date inicioAulas;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getQtdProfessores() {
		return qtdProfessores;
	}
	public void setQtdProfessores(int qtdProfessores) {
		this.qtdProfessores = qtdProfessores;
	}
	
	public int getQtdAlunos() {
		return qtdAlunos;
	}
	public void setQtdAlunos(int qtdAlunos) {
		this.qtdAlunos = qtdAlunos;
	}
	
	public Date getInicioAulas() {
		return inicioAulas;
	}
	public void setInicioAulas(java.util.Date date) {
		this.inicioAulas = (Date) date;
	}
}
