package javaCalc;

import java.beans.PropertyChangeSupport;

/**
 * Classe mettant en place toutes les opérations de stockage et opérations mathématiques <br>
 * Modèle du MVC
 * @author coill
 */
public class CalcAcc implements IAccumulateur{
	/**
	 * Stack représentant la mémoire de la calculatrice
	 */
	private CalcStack stack;
	/**
	 * Support permettant d'envoyer des évenement sur les changement de propriété
	 */
	private PropertyChangeSupport pcs;
	/**
	 * Controleur associé à ce Modèle
	 */
	private Controler controler;
	/**
	 * Attribut contenant le texte en cours d'édition
	 */
	private String acc;
	/**
	 * Status actuel de la calculatrice
	 */
	private CalcStatus status;
	
	/**
	 * Constructeur par défaut (à ne pas utiliser)
	 */
	public CalcAcc() {
		stack = new CalcStack();
		status = new CalcStatus();
		pcs = new PropertyChangeSupport(this);
		controler = null;
		acc = "";
	}
	
	/**
	 * Constructeur principal
	 * @param controler	Le controleur associé à ce modèle
	 */
	public CalcAcc(Controler controler) {
		stack = new CalcStack();
		status = new CalcStatus();
		this.controler = controler;
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(controler);
		acc = "";
	}
	
	/**
	 * Extrait le dernier element de la pile avec gestion des erreurs
	 * @return	Dernier element de la pile
	 */
	private double pop() {
		CalcStack oldStack = (CalcStack) stack.clone();
		
		if(!stack.empty()) {
			double value = stack.pop();
			pcs.firePropertyChange("stack", oldStack, stack);
			return value;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Vide l'accumulateur et renvoie la valeur
	 * @return	valeur de l'accumulateur
	 */
	private double popAccu() {
		if(!acc.isEmpty()) {
			String oldAcc = acc;
			clear();
			return (double) Double.parseDouble(oldAcc);
			
		}
		else {
			return 0;
		}
	}
	
	@Override
	public void push() {
		CalcStack oldStack = (CalcStack) stack.clone();
		stack.push(popAccu());
		clear();
		
		System.out.println(oldStack == stack);
		
		pcs.firePropertyChange("stack", oldStack, stack);
		resetStatus();
	}
	
	/**
	 * Insère un nouveau nombre dans la stack
	 * @param number Le nombre inséré
	 */
	private void push(double number) {
		CalcStack oldStack = (CalcStack) stack.clone();
		
		stack.push(number);
		
		pcs.firePropertyChange("stack", oldStack, stack);
	}
	
	@Override
	public void drop() {
		CalcStack oldStack = (CalcStack) stack.clone();
		stack.drop();
		pcs.firePropertyChange("stack", oldStack, stack);
		resetStatus();
	}
	
	@Override
	public void add() {
		double a = 0;
		double b = 0;
		boolean isError = false;
		
		if (acc.isEmpty()) {
			if (!stack.empty())
				b = pop();
			else
				isError = true;
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		else {
			b = popAccu();
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		
		if (!isError) {
			push(a+b);
			resetStatus();
		}
		else {
			push(b);
			setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
		}
			
		
	}
	
	@Override
	public void substract() {
		double a = 0;
		double b = 0;
		boolean isError = false;
		
		if (acc.isEmpty()) {
			if (!stack.empty())
				b = pop();
			else
				isError = true;
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		else {
			b = popAccu();
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		
		if (!isError) {
			push(a-b);
			resetStatus();
		}
		else {
			push(b);
			setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
		}
	}
	
	@Override
	public void multiply() {
		double a = 0;
		double b = 0;
		boolean isError = false;
		
		if (acc.isEmpty()) {
			if (!stack.empty())
				b = pop();
			else
				isError = true;
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		else {
			b = popAccu();
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		
		if (!isError) {
			push(a*b);
			resetStatus();
		}
		else {
			push(b);
			setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
		}
	}
	
	@Override
	public void divide() {
		double a = 0;
		double b = 0;
		boolean isError = false;
		
		if (acc.isEmpty()) {
			if (!stack.empty())
				b = pop();
			else
				isError = true;
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		else {
			b = popAccu();
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		
		if(!isError) {
			if (b != 0) {
				push(a/b);
				resetStatus();
			}
			else {
				push(a);
				push(b);
				setStatus("Division par 0",true);	
			}
		}
		else {
			push(b);
			setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
		}
		
		clear();
	}
	
	@Override
	public void swap() {
		double a = 0;
		double b = 0;
		boolean isError = false;
		
		if (!stack.empty())
			b = pop();
		else
			isError = true;
		
		if (!stack.empty())
			a = pop();
		else
			isError = true;
		
		
		if(!isError) {
			push(b);
			push(a);
			resetStatus();
		}
		else {
			push(b);
			setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
		}
		
	}
	
	@Override
	public void negative() {
		if(acc.isEmpty()) {
			if(!stack.empty()) {
				push(-pop());
				resetStatus();
			}
			else {
				setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
			}
		}
		
		else {
			String oldAcc = acc;
			
			//Si le premier signe est un '-', le retire, sinon ajoute un '-' au début de l'accumulateur
			if (acc.charAt(0) == '-') {
				acc = acc.substring(1, acc.length());
			}
			else {
				acc = "-" + acc;
			}
			
			resetStatus();
			pcs.firePropertyChange("accumulator", oldAcc, acc);
		}
	}
	
	/**
	 * Actualise le status de la calculatrice
	 * @param msg		Le message indiquant le status de la calculatrice
	 * @param isError	Indique s'il s'agit d'une erreur
	 */
	private void setStatus(String msg, boolean isError) {
		CalcStatus oldStatus = new CalcStatus(status);
		status.setStatus(msg, isError);
		pcs.firePropertyChange("status", oldStatus, status);
	}
	
	/**
	 * Remplace le status actuel avec un status vide
	 */
	private void resetStatus() {
		CalcStatus oldStatus = new CalcStatus(status);
		status.reset();
		pcs.firePropertyChange("status", oldStatus, status);
	}
	
	@Override
	public void backspace() {
		if (!acc.isEmpty()) {
			String oldAcc = acc;
			acc = acc.substring(0, acc.length()-1);
			pcs.firePropertyChange("accumulator", oldAcc, acc);
		}
	}
	
	@Override
	public void accumuler(String character) {
		pcs.firePropertyChange("accumulator", acc, acc += character);
	}
	
	@Override
	public void reset() {
		clear();
		clearStack();
		setStatus("", false);
	}
	
	@Override
	public void clear() {
		String oldAcc = acc;
		acc = "";
		pcs.firePropertyChange("accumulator", oldAcc, acc);
	}
	
	/**
	 * Vide entièrement la stack
	 */
	private void clearStack() {
		CalcStack oldStack = (CalcStack) stack.clone();
		stack.clear();
		pcs.firePropertyChange("stack", oldStack, stack);
	}

	@Override
	public void invert() {
		//TODO
		if(acc.isEmpty()) {
			if(!stack.empty()) {
				double value =pop();
				if (value != 0) {
					push(1/value);
					resetStatus();
				}
				else {
					push(value);
					setStatus("Division par 0",true);
				}	
			}
			else {
				setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
			}
		}
		
		else {
			double value = popAccu();
			
			if (value != 0) {
				push(1/value);
				resetStatus();
			}
			else {
				push(value);
				setStatus("Division par 0",true);
			}
		}
	}

	@Override
	public void power() {
		// TODO Auto-generated method stub
		double a = 0;
		double b = 0;
		boolean isError = false;
		
		if (acc.isEmpty()) {
			if (!stack.empty())
				b = pop();
			else
				isError = true;
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		else {
			b = popAccu();
			
			if (!stack.empty())
				a = pop();
			else
				isError = true;
		}
		
		if (!isError) {
			push(Math.pow(a, b));
			resetStatus();
		}
		else {
			push(b);
			setStatus("Le nombre d'éléments nécessaires pour l'opération est insuffisant", true);
		}
	}
}
