package javaCalc;

public interface IControler {
	/**
	 * Ajoute l'element present dans l'accumulateur à la stack
	 */
	public void addToStack();
	
	/**
	 * Réalise une somme
	 */
	public void add();
	
	/**
	 * Réalise une différence
	 */
	public void substract();
	
	/**
	 * Réalise une multiplication
	 */
	public void multiply();
	
	/**
	 * Réalise une division
	 */
	public void divide();
	
	/**
	 * Change le signe du dernier element de la pile si l'accumulateur est vide, sinon change le signe de l'accumulateur
	 */
	public void negative();
	
	/**
	 * Supprime le dernier caractère de l'accumulateur
	 */
	public void backspace();
	
	/**
	 * Ajoute un chiffre à la fin de l'accumulateur
	 * @param character chiffre à ajouter
	 */
	public void inputNumber(String character);
	
	/**
	 * Ajoute une virgule à l'accumulateur <br>
	 * L'ajout de virgule est bloquée si une virgule est déjà présente dans l'accumulateur
	 */
	public void inputDot();
	
	/**
	 * Vide la valeur présente dans l'accumulateur
	 */
	public void clear();
	
	/**
	 * Réinitialise la calculatrice
	 */
	public void reset();
	
	/**
	 * Supprime la valeur au dessus de la stack
	 */
	public void drop();
	
	/**
	 * Echange les deux valeurs au dessus de la pile
	 */
	public void swap();
	
	/**
	 * Inverse la valeur au dessus de la stack si l'accumulateur est vide, sinon inverse la valeur de l'accumulateur et l'insère dans la stack
	 */
	public void invert();
	
	/**
	 * Réalise l'opération "puissance"
	 */
	public void power();
}
