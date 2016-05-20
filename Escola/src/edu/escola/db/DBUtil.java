package edu.escola.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/escola";
			Connection con = DriverManager.getConnection(url, "root", "root");
			System.out.println("Conectado no Banco de dados");
			Statement stmt = con.createStatement();
			// inserção
//			stmt.executeUpdate("INSERT INTO escolas VALUES "
//					+ "(0,'Fatec ZL',100,10,'2016-08-01')");
//			System.out.println("Escola inserida com sucesso");
			// seleção
			ResultSet rs = stmt.executeQuery("SELECT * FROM escolas");
			while (rs.next()) {
				long id = rs.getLong("id");
				String nome = rs.getString("nome");
				int qtdAluno = rs.getInt("alunos");
				int qtdProfessores = rs.getInt("professores");
				Date data = rs.getDate("inicioAulas");
				System.out.printf(" ID: %d  Nome: %s  "
						+ "Alunos %d Professores %d  "
						+ "Inicio Aulas %td/%tm/%ty \n", id, nome, qtdAluno,
						qtdProfessores, data, data, data);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
