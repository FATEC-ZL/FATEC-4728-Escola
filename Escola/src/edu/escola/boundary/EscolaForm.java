package edu.escola.boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import edu.escola.control.EscolaControl;
import edu.escola.db.EscolaDAOException;
import edu.escola.entidade.Escola;
public class EscolaForm implements ActionListener, ListSelectionListener, TableModelListener {
	private JFrame janela;
	private JTextField txtId = new JTextField(5);
	private JTextField txtNome = new JTextField(5);
	private JTextField txtQtdAlunos = new JTextField(5);
	private JTextField txtQtdProfessores = new JTextField(5);
	private JTextField txtInicioAulas = new JTextField(5);
	private JButton btnAdicionar = new JButton("Adicionar");
	private JButton btnPesquisar = new JButton("Pesquisar");
	private EscolaControl control = new EscolaControl();
	private JTable tabela;
	public EscolaForm() { 
		janela = new JFrame("Gestão de Escolas");
		
		JPanel panPrincipal = new JPanel( new BorderLayout() ); 
		JPanel panFormulario = new JPanel( new GridLayout(6, 2) );
		JScrollPane panTable = new JScrollPane();
		
		panFormulario.add( new JLabel("Id") );
		panFormulario.add( txtId );
		panFormulario.add( new JLabel("Nome") );
		panFormulario.add( txtNome );
		panFormulario.add( new JLabel("Qtd. Alunos") );
		panFormulario.add( txtQtdAlunos ); 
		panFormulario.add( new JLabel("Qtd. Professores") );
		panFormulario.add( txtQtdProfessores );
		panFormulario.add( new JLabel("Inicio Aulas") );
		panFormulario.add( txtInicioAulas );
		panFormulario.add( btnAdicionar );
		panFormulario.add( btnPesquisar );
		
//		JTable tabela = new JTable( new Object[][]{ 
//								{1, 2, 3}, 
//								{3, 4, 5}, 
//								{6, 7, 8} } , 
//				new String[]{"Coluna1", "Coluna2", "Coluna3"} );
		
		tabela = new JTable( control );
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		centerRenderer.setBackground( Color.YELLOW );
		tabela.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		
		tabela.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getSelectionModel().addListSelectionListener( this );
		tabela.getModel().addTableModelListener( this );
		
		panTable.getViewport().add( tabela );
		
		panPrincipal.add( panFormulario, BorderLayout.NORTH );
		panPrincipal.add(panTable, BorderLayout.CENTER);
		
		btnAdicionar.addActionListener( this );
		btnPesquisar.addActionListener( this );
		
		janela.setContentPane( panPrincipal );
		janela.setResizable(false);
		janela.setSize( 800, 600 );
		janela.setLocationRelativeTo(null);
		janela.setVisible( true );
		janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	public static void main(String[] args) {
		new EscolaForm();
	}
	
	public Escola formToEscola() { 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Escola escola = new Escola();
		escola.setId( Long.parseLong( txtId.getText() ) );
		escola.setNome( txtNome.getText() );
		escola.setQtdAlunos( 
				Integer.parseInt(txtQtdAlunos.getText() ) );
		escola.setQtdProfessores( 
				Integer.parseInt(txtQtdProfessores.getText() ) );
		try {
			escola.setInicioAulas( 
					sdf.parse(txtInicioAulas.getText() ) );
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return escola;
	}
	
	public void escolaToForm( Escola e ){ 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		txtId.setText( String.valueOf(e.getId()) );
		txtNome.setText( e.getNome() );
		txtQtdAlunos.setText( String.valueOf(e.getQtdAlunos()) );
		txtQtdProfessores.setText( String.valueOf(e.getQtdProfessores()) );
		txtInicioAulas.setText( sdf.format( e.getInicioAulas() ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		if  (e.getSource() == btnAdicionar) {
			try {
				control.adicionar( formToEscola() );
			} catch (EscolaDAOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,  
						"Erro ao gravar no banco de dados " + e1.getMessage());
			}
			tabela.invalidate();
			tabela.revalidate();
		} else if (e.getSource() == btnPesquisar) {
			List<Escola> escolas;
			try {
				escolas = control.pesquisar( txtNome.getText() );
				if ( escolas.size() > 0) {
					escolaToForm( escolas.get(0) );
				}
				tabela.invalidate();
				tabela.revalidate();
			} catch (EscolaDAOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,  
						"Erro ao pesquisar no banco de dados " + e1.getMessage());				
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if ( e.getValueIsAdjusting() ) {
			int indice = tabela.getSelectionModel().getAnchorSelectionIndex();
			System.out.println( "Foi selecionada a linha : " + indice );
			Escola escola = control.getEscolas().get( indice );
			escolaToForm( escola );
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int indice = e.getFirstRow();
		Escola escola = control.getEscolas().get( indice );
		escolaToForm( escola );		
	}
}
