package javaCalc;

import java.util.Stack;

/**
 * Stack modifiée pour les besoins spécifiques de la calculatrices
 * @author coill
 */
public class CalcStack extends Stack<Double> {
	private static final long serialVersionUID = 6820228063608240841L;

	/**
	 * Constructeur par défaut
	 */
	public CalcStack() {
		super();
	}
	
	/**
	 * Ajoute un element en haut de la pile
	 * @param number	element à ajouter
	 */
	public void push(double number) {
		this.push((Double) number);
	}
	
	/**
	 * Retire le dernier element de la pile sans stockage de valeur
	 */
	public void drop() {
		if (!empty())
			this.pop();
	}
	
	/**
	 * Retire tous les elements de la pile 
	 */
	public void clear() {
		while (!this.empty()) {
			this.drop();
			
		}
	}
	
	
}
