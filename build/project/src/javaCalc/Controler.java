package javaCalc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Controleur du MVC
 * @author coill
 */
public class Controler extends Application implements PropertyChangeListener {
	/**
	 * Model associé au controleur
	 */
	private IAccumulateur model;
	/**
	 * View associée au controleur
	 */
	private IView view;
	/**
	 * Variable contenant le texte en cours d'édition (utilisé pour {@link #inputDot()})
	 */
	private String acc;

	/**
	 * Constructeur de Controler
	 */
	public Controler() {
		model = new CalcAcc(this);
		view = new CalcGUI(this);
		acc = "";
	}
	
	/**
	 * Cette méthode est appellée à chaque changement de propriété
	 * @param evt décrit la source de l'évenement et ce qui a changé
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "accumulator":
				accumulatorChange(evt);
				break;
				
			case "stack":
				stackChange(evt);
				break;
				
			case "status":
				statusChange(evt);
				break;
				
			default:
				System.out.println(evt.getPropertyName());
				System.out.println(evt.getNewValue());
				break;
		}
	}
	
	/**
	 * Réaction à l'évenement : changement d'accumulateur
	 * @param evt	l'évenement
	 */
	private void accumulatorChange(PropertyChangeEvent evt) {
		System.out.println("Accumulator: " + evt.getNewValue());
		String newAcc = evt.getNewValue().toString();
		view.changeAcc(newAcc);
		acc = newAcc;
	}
	
	/**
	 * Réaction à l'évenement : changement de stack
	 * @param evt	l'évenement
	 */
	private void stackChange(PropertyChangeEvent evt) {
		System.out.println("Stack: " + evt.getNewValue());
		CalcStack stack = (CalcStack) evt.getNewValue();
		view.changeStack(topStackElements(stack, 5));
	}
	
	/**
	 * Réaction à l'évenement : changement de status
	 * @param evt	l'évenement
	 */
	private void statusChange(PropertyChangeEvent evt) {
		System.out.println("CalcStatus: " + evt.getNewValue());
		view.changeStatus((CalcStatus) evt.getNewValue());
	}
	
	/**
	 * Renvoie la liste des éléments au sommet de la stack
	 * @param stack			La stack de la calculatrice
	 * @param iterations	Le nombre d'éléments souhaités
	 * @return				La liste des éléments soouhaités
	 */
	private ArrayList<String> topStackElements(CalcStack stack, int iterations) {
		ArrayList<String> list = new ArrayList<String>();
		stack = (CalcStack) stack.clone();
		
		for (int i = 0; i < iterations; i++) {
			if(!stack.empty()) {
				list.add(stack.pop().toString());
			}
			else {
				list.add("");
			}
		}
		
		return list;
	}
	
	/**
	 * Ajoute l'element present dans l'accumulateur à la stack
	 */
	public void addToStack() {
		model.push();
	}
	
	/**
	 * Réalise une somme
	 */
	public void add() {
		model.add();	
	}
	
	/**
	 * Réalise une différence
	 */
	public void substract() {
		model.substract();
	}
	
	/**
	 * Réalise une multiplication
	 */
	public void multiply() {
		model.multiply();
	}
	
	/**
	 * Réalise une division
	 */
	public void divide() {
		model.divide();
	}
	
	/**
	 * Change le signe du dernier element de la pile si l'accumulateur est vide, sinon change le signe de l'accumulateur
	 */
	public void negative() {
		model.negative();
	}
	
	/**
	 * Supprime le dernier caractère de l'accumulateur
	 */
	public void backspace() {
		model.backspace();
	}
	
	/**
	 * Ajoute un chiffre à la fin de l'accumulateur
	 * @param character chiffre à ajouter
	 */
	public void inputNumber(String character) {
		model.accumuler(character);
	}
	
	/**
	 * Ajoute une virgule à l'accumulateur <br>
	 * L'ajout de virgule est bloquée si une virgule est déjà présente dans l'accumulateur
	 */
	public void inputDot() {
		if(!acc.contains(".")) {
			model.accumuler(".");
		}
	}
	
	/**
	 * Fait apparaître l'interface graphique au lancement du programme principal
	 */
	@Override
	public void start(Stage arg0){
		view.show();
	}
	
	/**
	 * Lance le programme principal
	 * @param args	Arguments du programme
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Vide la valeur présente dans l'accumulateur
	 */
	public void clear() {
		model.clear();
	}

	/**
	 * Réinitialise la calculatrice
	 */
	public void reset() {
		model.reset();
	}

	/**
	 * Supprime la valeur au dessus de la stack
	 */
	public void drop() {
		model.drop();
	}

	/**
	 * Echange les deux valeurs au dessus de la pile
	 */
	public void swap() {
		model.swap();
	}
	
	/**
	 * Inverse la valeur au dessus de la stack si l'accumulateur est vide, sinon inverse la valeur de l'accumulateur et l'insère dans la stack
	 */
	public void invert() {
		model.invert();
	}
	
	/**
	 * Réalise l'opération "puissance"
	 */
	public void power() {
		model.power();
	}
}
