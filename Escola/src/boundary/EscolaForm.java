package boundary;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.EscolaControl;
import entity.Escola;

public class EscolaForm implements ActionListener {
	private JFrame janela;
	private JTextField txtId = new JTextField(5);
	private JTextField txtNome = new JTextField(5);
	private JTextField txtQtdAlunos = new JTextField(5);
	private JTextField txtQtdProfessores = new JTextField(5);
	private JTextField txtInicioAulas = new JTextField(5);
	private JButton btnAdicionar = new JButton("Adicionar");
	private JButton btnPesquisar = new JButton("Pesquisar");
	private EscolaControl control = new EscolaControl();
	public EscolaForm() { 
		janela = new JFrame("Gestão de Escolas");
		JPanel panPrincipal = new JPanel( new GridLayout(6, 2) );
		panPrincipal.add( new JLabel("Id") );
		panPrincipal.add( txtId );
		panPrincipal.add( new JLabel("Nome") );
		panPrincipal.add( txtNome );
		panPrincipal.add( new JLabel("Qtd. Alunos") );
		panPrincipal.add( txtQtdAlunos ); 
		panPrincipal.add( new JLabel("Qtd. Professores") );
		panPrincipal.add( txtQtdProfessores );
		panPrincipal.add( new JLabel("Inicio Aulas") );
		panPrincipal.add( txtInicioAulas );
		panPrincipal.add( btnAdicionar );
		panPrincipal.add( btnPesquisar );
		
		btnAdicionar.addActionListener( this );
		btnPesquisar.addActionListener( this );
		
		janela.setContentPane( panPrincipal );
		janela.setSize( 400, 200 );
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
			control.adicionar( formToEscola() );
		} else if (e.getSource() == btnPesquisar) {
			List<Escola> escolas = 
				control.pesquisar( txtNome.getText() );
			escolaToForm( escolas.get(0) );
		}
	}
}