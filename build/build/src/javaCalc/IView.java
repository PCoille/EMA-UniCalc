package javaCalc;

import java.util.ArrayList;

/**
 * Interface de la vue de la calculatrice
 * @author coill
 */
public interface IView {
	/**
	 * Fait apparaître l'interface graphique
	 */
	public void show();
	
	/**
	 * Modifie l'affichage des éléments les plus "hauts" de la stack
	 * @param list liste des éléments à afficher
	 */
	public void changeStack(ArrayList<String> list);
	
	/**
	 * Modifie l'affichage de l'accumulateur
	 * @param acc texte à afficher sur l'accumulateur
	 */
	public void changeAcc(String acc);
	
	/**
	 * Adaptation au changement de status de la calculatrice
	 * Affiche le texte correspondant et affiche en rouge en cas d'erreur
	 * @param status le status
	 */
	public void changeStatus(CalcStatus status);
}
