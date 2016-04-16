package control;

import java.util.ArrayList;
import java.util.List;
import entity.Escola;

public class EscolaControl {
	
	private List<Escola> escolas = new ArrayList<Escola>();

	public void adicionar(Escola e) {
		escolas.add( e );
		System.out.println( "Adicionar executado há " + 
						escolas.size() + " escolas na lista " );
		
	}

	public List<Escola> pesquisar(String nome) {
		List<Escola> encontradas = new ArrayList<Escola>();
		for (Escola e : escolas) {
			if (e.getNome().contains( nome )) { 
				encontradas.add ( e );
			}
		}
		System.out.println( "Pesquisar executado foram encontradas " +
								encontradas.size() + " escolas");
		return encontradas;
		
	}
}
