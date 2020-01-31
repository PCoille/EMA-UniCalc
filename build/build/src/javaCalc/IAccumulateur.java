package javaCalc;

/**
 * Interface du modèle de la calculatrice
 * @author coill
 */
public interface IAccumulateur {
	/**
	 * Met le nombre présent dans l'accumulateur au dessus de la pile
	 */
	public void push();
	
	/**
	 * Supprime le dernier élément de la pile
	 */
	public void drop();
	
	/**
	 * Interchange les deux éléments au dessus de la pile
	 */
	public void swap();
	
	/**
	 * Réalise la somme des deux nombres au dessus de la pile si l'accumulateur est vide <br>
	 * Sinon, réalise la somme du nombre dans l'accumulateur avec celui au dessus de la pile <br>
	 * Le resultat est ajouté en haut de la pile
	 */
	public void add();
	
	/**
	 * Réalise la différence des deux nombres au dessus de la pile si l'accumulateur est vide ([2e plus haut de la pile] - [plus haut de la pile]) <br>
	 * Sinon, réalise la différence du nombre dans l'accumulateur avec celui au dessus de la pile ([pile] - [accumulateur]) <br>
	 * Le resultat est ajouté en haut de la pile
	 */
	public void substract();
	
	/**
	 * Réalise le produit des deux nombres au dessus de la pile si l'accumulateur est vide <br>
	 * Sinon, réalise le produit du nombre dans l'accumulateur avec celui au dessus de la pile <br>
	 * Le resultat est ajouté en haut de la pile
	 */
	public void multiply();
	
	/**
	 * Réalise la division des deux nombres au dessus de la pile si l'accumulateur est vide ([2e plus haut de la pile] / [plus haut de la pile]) <br>
	 * Sinon, réalise la division du nombre dans l'accumulateur avec celui au dessus de la pile ([pile] / [accumulateur]) <br>
	 * Le resultat est ajouté en haut de la pile
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
	 * Ajoute un caractère à l'accumulateur
	 * @param character : caractère ajouté
	 */
	public void accumuler(String character);
	
	/**
	 * Vide l'accumulateur et la stack, réinitialise le status
	 */
	public void reset();
	
	/**
	 * Vide l'accumulateur
	 */
	public void clear();
	
	/**
	 * Inverse le dernier element de la pile si l'accumulateur est vide, sinon inverse l'accumulateur
	 */
	public void invert();
	
	/**
	 * Réalise la puissance des deux nombres au dessus de la pile si l'accumulateur est vide ([2e plus haut de la pile] ^ [plus haut de la pile]) <br>
	 * Sinon, réalise la puissance du nombre dans l'accumulateur avec celui au dessus de la pile ([pile] ^ [accumulateur]) <br>
	 * Le resultat est ajouté en haut de la pile
	 */
	public void power();
}
