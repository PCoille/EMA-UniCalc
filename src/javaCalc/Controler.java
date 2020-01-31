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
public class Controler extends Application implements PropertyChangeListener, IControler {
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
	
	@Override
	public void addToStack() {
		model.push();
	}
	
	@Override
	public void add() {
		model.add();	
	}
	
	@Override
	public void substract() {
		model.substract();
	}
	
	@Override
	public void multiply() {
		model.multiply();
	}
	
	@Override
	public void divide() {
		model.divide();
	}
	
	@Override
	public void negative() {
		model.negative();
	}
	
	@Override
	public void backspace() {
		model.backspace();
	}
	
	@Override
	public void inputNumber(String character) {
		model.accumuler(character);
	}
	
	@Override
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

	@Override
	public void clear() {
		model.clear();
	}

	@Override
	public void reset() {
		model.reset();
	}

	@Override
	public void drop() {
		model.drop();
	}

	@Override
	public void swap() {
		model.swap();
	}
	
	@Override
	public void invert() {
		model.invert();
	}
	
	@Override
	public void power() {
		model.power();
	}
}
