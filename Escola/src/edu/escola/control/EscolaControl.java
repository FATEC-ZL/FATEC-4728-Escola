package edu.escola.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import edu.escola.db.EscolaDAO;
import edu.escola.db.EscolaDAOException;
import edu.escola.db.EscolaDAOImpl;
import edu.escola.entidade.Escola;

public class EscolaControl implements TableModel {
	
	private List<Escola> escolas = new ArrayList<Escola>();
	private List<TableModelListener> tableListeners = new ArrayList<TableModelListener>();
	

	public void adicionar(Escola e) throws EscolaDAOException {
		EscolaDAO eDao = new EscolaDAOImpl();
		eDao.adicionar( e );
		getEscolas().add( e );
		System.out.println( "Adicionar executado h· " + 
						getEscolas().size() + " escolas na lista " );
		
	}

	public List<Escola> pesquisar(String nome) throws EscolaDAOException {
		EscolaDAO eDao = new EscolaDAOImpl();
		List<Escola> encontradas = new ArrayList<Escola>();
		encontradas = eDao.pesquisar( nome );
		escolas = encontradas;
		System.out.println( "Pesquisar executado foram encontradas " +
								encontradas.size() + " escolas");
		return encontradas;
		
	}

	@Override
	public int getRowCount() {
		return getEscolas().size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		String [] nomes = {"Id", "Nome", "Alunos", "Professores", "Inicio Aulas"};
		return nomes[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?>[] classes = {Long.class, String.class, Integer.class, 
							Integer.class, Date.class};
		return classes[columnIndex];
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex > 0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Escola e = getEscolas().get( rowIndex );
		Object[] valores = {e.getId(), e.getNome(), e.getQtdAlunos(),
							e.getQtdProfessores(), e.getInicioAulas()};
		return valores[columnIndex];
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Escola e = getEscolas().get( rowIndex );
		switch (columnIndex) {
			case 0 : e.setId( (Long) aValue ); break;
			case 1 : e.setNome( (String) aValue ); break;
			case 2 : e.setQtdAlunos( (Integer) aValue ); break;
			case 3 : e.setQtdProfessores( (Integer) aValue ); break;
			case 4 : e.setInicioAulas( (Date) aValue ); break;
		}
		TableModelEvent event = new TableModelEvent(this);
		for (TableModelListener t : tableListeners) {
			t.tableChanged( event );
		}
	}
	public void setEscolas(List<Escola> escolas) {
		this.escolas = escolas;
	}

	public List<Escola> getEscolas() {
		return escolas;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {	
		tableListeners.add( l );
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		tableListeners.remove(l);
	}

}
