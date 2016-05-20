package edu.escola.db;

import java.util.List;

import edu.escola.entidade.Escola;

public interface EscolaDAO {
	
	public void adicionar (Escola e) throws EscolaDAOException;
	public List<Escola> pesquisar(String nome) throws EscolaDAOException;

}
