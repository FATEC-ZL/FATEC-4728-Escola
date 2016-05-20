package edu.escola.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.escola.entidade.Escola;
public class EscolaDAOImpl implements EscolaDAO {
	
	private Connection con = JDBCUtil.getConnection();

	@Override
	public void adicionar(Escola e) throws EscolaDAOException {
		String sql = "INSERT INTO escolas "
			+ "(id, nome, alunos, professores, inicioAulas) "
			+ "VALUES (?, ?, ?, ?, ?)";
	try {
			PreparedStatement pst = con.prepareStatement( sql );
			pst.setLong(1, e.getId());
			pst.setString(2, e.getNome());
			pst.setInt(3,  e.getQtdAlunos());
			pst.setInt(4, e.getQtdProfessores());
			long numero = e.getInicioAulas().getTime();
			java.sql.Date data = new java.sql.Date( numero );
			pst.setDate(5, data );
			// pst.setDate(5, 
			// 	new java.sql.Date(e.getInicioAulas().getTime() ) );
			pst.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new EscolaDAOException( e1 );
		}
	}

	@Override
	public List<Escola> pesquisar(String nome) throws EscolaDAOException {
		List<Escola> lista = new ArrayList<Escola>();
		String sql = "SELECT * FROM escolas WHERE nome like ?";
		try {
				PreparedStatement pst = con.prepareStatement( sql );
				pst.setString(1, "%" + nome + "%");
				ResultSet rs = pst.executeQuery();
				while( rs.next() ) { 
					Escola e = new Escola();
					e.setId( rs.getInt("id") );
					e.setNome( rs.getString("nome") );
					e.setQtdAlunos( rs.getInt("alunos") );
					e.setQtdProfessores( rs.getInt("professores") );
					e.setInicioAulas( rs.getDate("inicioAulas") );
					lista.add( e );
				}
		} catch( SQLException e ) { 
			e.printStackTrace();
			throw new EscolaDAOException( e );
		}
		return lista;
	} 

}