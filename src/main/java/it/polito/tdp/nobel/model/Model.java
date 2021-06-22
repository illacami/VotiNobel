package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaSoluzioneMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.partenza = new LinkedList<Esame>();
		this.partenza = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale = new LinkedHashSet<Esame>();
		
		soluzioneMigliore = new HashSet<Esame>();
		mediaSoluzioneMigliore = 0;
		
		this.cerca2(parziale, numeroCrediti, 0);
		
		
		return soluzioneMigliore;
	}
	
	private void cerca2(Set<Esame> parziale, int numCrediti, int livello) {
		
		// caso terminale 
		// 1) ho occupato tutti i crediti
		// 2) soluzione migliore
		if(sommaCrediti(parziale) > numCrediti) {
			return;
		}
		
		if(sommaCrediti(parziale) == numCrediti) {
			if(calcolaMedia(parziale) > this.mediaSoluzioneMigliore) {
				soluzioneMigliore = new HashSet<Esame>(parziale);
				mediaSoluzioneMigliore = calcolaMedia(parziale);
			}
			return;
		}
		
		if(livello == partenza.size())
			return;
	
		parziale.add(partenza.get(livello));
		cerca2(parziale, numCrediti, livello+1);
		
		parziale.remove(partenza.get(livello));
		cerca2(parziale, numCrediti, livello+1);
	}
	
	private void cerca1() {
		
	}

	public double calcolaMedia(Set<Esame> esami) {
		
		double somma = 0.0;
		for(Esame e : esami)
			somma += e.getVoto();
		
		return somma/esami.size();
		
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		
		int crediti = 0;
		for(Esame e : esami)
			crediti += e.getCrediti();
		
		return crediti;
	}

}
