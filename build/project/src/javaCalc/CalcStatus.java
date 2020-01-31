package javaCalc;

/**
 * Classe représentant le status de la calculatrice
 * @author coill
 */
public class CalcStatus {
	/**
	 * Indique si la calculatrice vient de rencontrer une erreur
	 */
	private boolean isError;
	/**
	 * Message donnant des détails sur le status de la calculatrice
	 */
	private String message;
	
	/**
	 * Constructeur par défaut de CalcStatus
	 */
	public CalcStatus() {
		isError = false;
		message = "";
	}
	
	/**
	 * Constructeur de status dans le cas où seul un message est inséré, on considère alors qu'il n'y a pas d'erreur
	 * @param msg	le message indiquant le status
	 */
	public CalcStatus(String msg) {
		isError = false;
		message = msg;
	}
	
	/**
	 * Constructeur de status dans le cas où un message et le isError sont insérés
	 * @param msg		le message indiquant le status
	 * @param isError	indique s'il s'agit d'une erreur
	 */
	public CalcStatus(String msg, boolean isError) {
		this.isError = isError;
		if (isError) {
			message = "Erreur : " + msg;
		}
		else {
			message = msg;
		}
	}
	
	public CalcStatus(CalcStatus status) {
		this.isError = status.isError;
		this.message = status.message;
	}
	
	public void setStatus(String msg, boolean isError) {
		this.isError = isError;
		if (isError) {
			message = "Erreur : " + msg;
		}
		else {
			message = msg;
		}
	}

	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Remplace le status actuel avec un status vide
	 */
	public void reset() {
		isError = false;
		message = "";
	}
}
