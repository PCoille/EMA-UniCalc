package javaCalc;


import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Stage mettant en place l'interface graphique de la calculatrice <br>
 * Vue du MVC
 * @author coill
 */
public class CalcGUI extends Stage implements IView{
	/**
	 * Controleur associé à cette vue
	 */
	private Controler controler;
	/**
	 * Liste de TextFields contenant les valeurs étant au dessus de la stack
	 */
	private ArrayList<TextField> stackList = new ArrayList<TextField>(5);
	/**
	 * Taille de la liste de TextFields contenant les valeurs étant au dessus de la stack
	 */
	private int stackListSize = 5;
	/**
	 * Ecran sur lequel s'affiche le nombre en train d'être inséré
	 */
	private TextField screenField;
	/**
	 * Contient la majorité des éléments graphique
	 */
	private GridPane root;
	/**
	 * Label associé au status de la calculatrice
	 */
	private Label statusLabel;
	
	/**
	 * Constructeur par défaut (à ne pas utiliser)
	 */
	public CalcGUI() {
		super();
		this.controler = null;
		
		//Paramètres de la fenêtre 
		this.setHeight(420);
		this.setWidth(280);
		
		this.setMinHeight(460);
		this.setMinWidth(280);
		
		this.getIcons().add(new Image("./img/unicalc-icon.png"));
		this.setTitle("UniCalc");
		
		// Création des TextFields représentant les éléments au dessus de la stack
		for (int i = 0; i < stackListSize; i++) {
			TextField textField = new TextField();
			textField.setDisable(true);
			// Changement de la couleur des éléments au dessus de la stack pour plus de lisibilité
			textField.setStyle("-fx-control-inner-background: #feffa8;-fx-opacity: 1;");
			stackList.add(i, textField);
		}
		
		//Ecran sur lequel s'affiche le nombre en train d'être inséré
		screenField = new TextField();
		screenField.setDisable(true);
		double screenFieldHeight = 40;
		screenField.setPrefHeight(screenFieldHeight);
		screenField.setStyle( "-fx-background-image: url(\"./img/unicorn-bg.png\");"
							+ "-fx-background-repeat: no-repeat;"
							+ "-fx-background-position: right center;"
							+ "-fx-background-size: " + screenFieldHeight + ";"
							+ "-fx-opacity: 1;");
		screenField.setFont(new Font(20));
		
		//Label représentant le status de la calculatrice
		statusLabel = new Label();
		statusLabel.setWrapText(true);
		
		
		//root contenant tous les composants de l'interface graphique
		root = new GridPane();
		this.setScene(new Scene(root));
		root.requestFocus();
		
		//Génération de l'interface graphique et des évenements relatifs aux touches du clavier
		initGUI();
		setKeyEvents();
	}
	
	/**
	 * Constructeur principal
	 * @param controler	Le controleur associé à cette vue
	 */
	public CalcGUI(Controler controler) {
		super();
		this.controler = controler;
		
		//Paramètres de la fenêtre 
		this.setHeight(460);
		this.setWidth(280);
		
		this.setMinHeight(460);
		this.setMinWidth(280);
		
		this.getIcons().add(new Image("./img/unicalc-icon.png"));
		this.setTitle("UniCalc");
		
		// Création des TextFields représentant les éléments au dessus de la stack
		for (int i = 0; i < stackListSize; i++) {
			TextField textField = new TextField();
			textField.setDisable(true);
			// Changement de la couleur des éléments au dessus de la stack pour plus de lisibilité
			textField.setStyle("-fx-control-inner-background: #feffa8;-fx-opacity: 1;");
			stackList.add(i, textField);
		}
		
		//Ecran sur lequel s'affiche le resultat ou le nombre en train d'être inséré
		screenField = new TextField();
		screenField.setDisable(true);
		double screenFieldHeight = 40;
		screenField.setPrefHeight(screenFieldHeight);
		screenField.setStyle( "-fx-background-image: url(\"./img/unicorn-bg.png\");"
							+ "-fx-background-repeat: no-repeat;"
							+ "-fx-background-position: right center;"
							+ "-fx-background-size: " + screenFieldHeight + ";"
							+ "-fx-opacity: 1;");
		screenField.setFont(new Font(20));
		
		//Label représentant le status de la calculatrice
		statusLabel = new Label();
		statusLabel.setWrapText(true);
		
		
		//root contenant tous les composants de l'interface graphique
		root = new GridPane();
		this.setScene(new Scene(root));
		root.requestFocus();
		
		//Génération de l'interface graphique et des évenements relatifs aux touches du clavier
		initGUI();
		setKeyEvents();
	}
	
	/**
	 * Génération et organisation principale de l'interface graphique
	 */
	public void initGUI() {
		//TextFields contenant les éléments en haut de la stack
		for (int i = 0; i < stackListSize; i++) {
			root.add(stackList.get(stackListSize - i - 1), 0, i);
		}
		
		//Ecran représentant l'accumulateur
		root.add(screenField, 0, stackListSize);
		
		//Grille correspondant aux touches de la calculatrice
		GridPane gridPane = calcKeyGrid();
		root.add(gridPane, 0, stackListSize + 1);
		
		//Séparateur Horizontal (utilité purement graphique)
		Separator hSeparator = new Separator(Orientation.HORIZONTAL);
		hSeparator.setPadding(new Insets(6, 0, 0, 0));
		root.add(hSeparator, 0, stackListSize + 2);
		
		//Label indiquant le status de la calculatrice (particulièrment les erreurs)
		root.add(statusLabel, 0, stackListSize + 3);
		
		//Mise en place des contraintes sur les colonnes
		for (int col = 0 ; col < root.getColumnCount(); col++ ) {
			ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setHgrow(Priority.SOMETIMES);
			columnConstraints.setFillWidth(true);
            root.getColumnConstraints().add(columnConstraints);
        }
		
		//Mise en place des contraintes sur les lignes
		for (int row = 0 ; row < root.getRowCount(); row++ ) {
			RowConstraints rowConstraints = new RowConstraints();
			//S'il s'agit des TextFields représentant la stack :
			if (row < stackListSize) {
				rowConstraints.setFillHeight(true);
			}
			//S'il s'agit du TextField affichant l'accumulateur :
			else if (row == stackListSize) {
				rowConstraints.setFillHeight(true);
			}
			//S'il s'agit de la grille :
			else if (row == stackListSize + 1) {
				//On autorise l'augmentation verticale de la taille de la grille
				rowConstraints.setVgrow(Priority.SOMETIMES);
				rowConstraints.setFillHeight(true);
			}
			else {
				rowConstraints.setFillHeight(true);
			}
			
            root.getRowConstraints().add(rowConstraints);
        }
	}
	
	/**
	 * Mise en place de la grille correspondant aux touches de la calculatrice
	 * @return	la grille
	 */
	private GridPane calcKeyGrid() {
		//Génération des boutons de la calculatrice
		Button b1 = numberButton("1");
		Button b2 = numberButton("2");
		Button b3 = numberButton("3");
		Button b4 = numberButton("4");
		Button b5 = numberButton("5");
		Button b6 = numberButton("6");
		Button b7 = numberButton("7");
		Button b8 = numberButton("8");
		Button b9 = numberButton("9");
		Button b0 = numberButton("0");
		
		Button bNeg = negButton();
		Button bPlus = plusButton();
		Button bMinus = minusButton();
		Button bMult = multButton();
		Button bDiv = divButton();
		Button bComma = dotButton();
		Button bStack = stackButton();
		
		Button bBackspace = backspaceButton();
		Button bClear = clearButton();
		Button bReset = resetButton();
		Button bDrop = dropButton();
		Button bSwap = swapButton();
		
		Button bPow = powerButton();
		Button bInv = invertButton();
		
		
		//Disposition grille
		
		GridPane gridPane = new GridPane();
		
		gridPane.add(bClear, 0, 0);
		gridPane.add(bReset, 1, 0);
		gridPane.add(bDrop, 2, 0);
		gridPane.add(bSwap, 3, 0);
		
		gridPane.add(b7, 0, 1);
		gridPane.add(b8, 1, 1);
		gridPane.add(b9, 2, 1);
		gridPane.add(b4, 0, 2);
		gridPane.add(b5, 1, 2);
		gridPane.add(b6, 2, 2);
		gridPane.add(b1, 0, 3);
		gridPane.add(b2, 1, 3);
		gridPane.add(b3, 2, 3);
		gridPane.add(b0, 0, 4);
		gridPane.add(bComma, 1, 4);
		gridPane.add(bNeg, 2, 4);
		
		gridPane.add(bPlus, 3, 1);
		gridPane.add(bMinus, 3, 2);
		gridPane.add(bMult, 3, 3);
		gridPane.add(bDiv, 3, 4);
		
		gridPane.add(bBackspace, 4, 0);
		gridPane.add(bPow, 4, 1);
		gridPane.add(bInv, 4, 2);
		gridPane.add(bStack, 4, 3,1,2);
		
		//Ajout d'un padding pour que les bouttons ne soient pas collé au bord
		gridPane.setPadding(new Insets(4,4,4,4));
		
		//Ajout des contraintes sur les colonnes (permet de faire grossir les boutons lors d'un changement de taille de la fenêtre)
		for (int col = 0 ; col < gridPane.getColumnCount(); col++ ) {
			ColumnConstraints columnConstraints = new ColumnConstraints();
			
			columnConstraints.setHgrow(Priority.SOMETIMES);
			columnConstraints.setFillWidth(true);

            gridPane.getColumnConstraints().add(columnConstraints);
        }
		
		//Ajout des contraintes sur les colonnes (permet de faire grossir les boutons lors d'un changement de taille de la fenêtre)
		for (int row = 0 ; row < gridPane.getRowCount(); row++ ) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.SOMETIMES);
			rowConstraints.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConstraints);
        }
		
		return gridPane;
	}
	
	/**
	 * Mise en place de la réponse aux évenements relatifs à l'appuis des touches de clavier
	 */
	private void setKeyEvents() {
		root.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				//System.out.println(ke.getCode());
				switch (ke.getCode()) {
					case DIGIT0:
						inputNumber("0");
						break;
					case NUMPAD0:
						inputNumber("0");
						break;
					case DIGIT1:
						inputNumber("1");
						break;
					case NUMPAD1:
						inputNumber("1");
						break;
					case DIGIT2:
						inputNumber("2");
						break;
					case NUMPAD2:
						inputNumber("2");
						break;
					case DIGIT3:
						inputNumber("3");
						break;
					case NUMPAD3:
						inputNumber("3");
						break;
					case DIGIT4:
						inputNumber("4");
						break;
					case NUMPAD4:
						inputNumber("4");
						break;
					case DIGIT5:
						inputNumber("5");
						break;
					case NUMPAD5:
						inputNumber("5");
						break;
					case DIGIT6:
						inputNumber("6");
						break;
					case NUMPAD6:
						inputNumber("6");
						break;
					case DIGIT7:
						inputNumber("7");
						break;
					case NUMPAD7:
						inputNumber("7");
						break;
					case DIGIT8:
						inputNumber("8");
						break;
					case NUMPAD8:
						inputNumber("8");
						break;
					case DIGIT9:
						inputNumber("9");
						break;
					case NUMPAD9:
						inputNumber("9");
						break;
					case PLUS:
						add();
						break;
					case ADD:
						add();
						break;
					case MINUS:
						substract();
						break;
					case SUBTRACT:
						substract();
						break;
					case MULTIPLY:
						multiply();
						break;
					case DIVIDE:
						divide();
					case BACK_SPACE:
						backspace();
						break;
					case ENTER:
						addToStack();
						break;
					case PERIOD:
						inputDot();
						break;
					case DECIMAL:
						inputDot();
						break;
					case COMMA:
						inputDot();
						break;
					default:
						break;
				}
			}
		});
	}
	
	/**
	 * Renvoie un bouton permettant d'ajouter un chiffre à l'accumulateur
	 * @param number	le nombre associé au bouton
	 * @return 			le bouton
	 */
	private Button numberButton(String number) {
		Button button = new Button(number);
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		//Associe l'utilisation des boutons numérotés avec l'écriture des nombres en question
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				inputNumber(button.getText());
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande l'ajout d'un chiffre dans l'accumulateur
	 * @param string	le string associé au chiffre
	 */
	private void inputNumber(String string) {
		controler.inputNumber(string);
	}
	
	/**
	 * Renvoie un bouton permettant de supprimer le dernier caractère de l'accumulateur
	 * @return le bouton
	 */
	private Button backspaceButton() {
		Button button = new Button("←");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		//Supprime le dernier caractère indiqué sur l'écran
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				backspace();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la suppression du dernier caractère
	 */
	private void backspace() {
		controler.backspace();
	}
	
	/**
	 * Renvoie un bouton permettant d'insérer un point
	 * @return le bouton
	 */
	private Button dotButton() {
		Button button = new Button(".");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				inputDot();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande l'insertion d'un point
	 */
	private void inputDot() {
		controler.inputDot();
	}
	
	/**
	 * Renvoie un bouton permettant de réaliser une somme
	 * @return le bouton
	 */
	private Button plusButton() {
		Button button = new Button("+");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				add();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réalisation d'une somme
	 */
	private void add() {
		controler.add();
	}
	
	/**
	 * Renvoie un bouton permettant de réaliser une différence
	 * @return le bouton
	 */
	private Button minusButton() {
		Button button = new Button("-");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				substract();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réalisation d'une différence
	 */
	private void substract() {
		controler.substract();
	}
	
	/**
	 * Renvoie un bouton permettant de réaliser une multiplication
	 * @return le bouton
	 */
	private Button multButton() {
		Button button = new Button("x");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				multiply();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réalisation d'une multiplication
	 */
	private void multiply() {
		controler.multiply();
	}
	
	/**
	 * Renvoie un bouton permettant de réaliser une division
	 * @return le bouton
	 */
	private Button divButton() {
		Button button = new Button("/");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				divide();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réalisation d'une division
	 */
	private void divide() {
		controler.divide();
	}
	
	/**
	 * Renvoie un bouton permettant d'ajouter le nombre présent dans l'accumulateur à la stack
	 * @return	le bouton
	 */
	private Button stackButton() {
		Button button = new Button("<>");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addToStack();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande l'ajout du nombre présent dans l'accumulateur à la stack
	 */
	private void addToStack() {
		controler.addToStack();
	}
	
	/**
	 * Renvoie un bouton permettant d'inverser le signe de l'accumulateur
	 * @return le bouton
	 */
	private Button negButton() {
		Button button = new Button("(-)");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				negative();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande l'inverser du signe de l'accumulateur
	 */
	private void negative() {
		controler.negative();
	}
	
	/**
	 * Renvoie un bouton permettant de supprimer le nombre présent dans l'accumulateur
	 * @return le bouton
	 */
	private Button clearButton() {
		Button button = new Button("CLEAR");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clear();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demade la suppression du nombre présent dans l'accumulateur
	 */
	private void clear() {
		controler.clear();
	}
	
	/**
	 * Renvoie un bouton permettant à la calculatrice de revenir dans son état initial
	 * @return le bouton
	 */
	private Button resetButton() {
		Button button = new Button("RESET");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				reset();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réinitialisation de la calculatrice
	 */
	private void reset() {
		controler.reset();
	}
	
	/**
	 * Renvoie un bouton permettant de supprimer l'élement le plus au dessus de la stack
	 * @return le bouton
	 */
	private Button dropButton() {
		Button button = new Button("DROP");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				drop();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la suppression de l'élement le plus au dessus de la stack
	 */
	private void drop() {
		controler.drop();
	}
	
	/**
	 * Renvoie un boutton peremettant l'échange des deux valeurs au dessus de la stack
	 * @return le bouton
	 */
	private Button swapButton() {
		Button button = new Button("SWAP");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				swap();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande l'échange des deux valeurs au dessus de la stack 
	 */
	private void swap() {
		controler.swap();
	}
	
	/**
	 * Renvoie un bouton permettant de réaliser une inversion
	 * @return le bouton
	 */
	private Button invertButton() {
		Button button = new Button("1/x");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				invert();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réalisation d'une inversion
	 */
	private void invert() {
		controler.invert();
	}
	
	/**
	 * Renvoie un bouton permettant de réaliser une opération "puissance"
	 * @return le bouton
	 */
	private Button powerButton() {
		Button button = new Button("^");
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleButton(button));
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				power();
				root.requestFocus();
			}
		});
		
		return button;
	}
	
	/**
	 * Demande la réalisation d'une opération "puissance"
	 */
	private void power() {
		controler.power();
	}
	
	/**
	 * Permet de changer la taille du texte des boutons quand ceux-ci grandissent
	 * (d'après https://stackoverflow.com/questions/37032041/scaling-a-buttons-text-automatically-with-the-button-in-javafx)
	 * @param button	le bouton sur lequel appliquer le changement
	 */
	private void scaleButton(Button button) {
        double w = button.getWidth();
        double h = button.getHeight();

        double bw = button.prefWidth(-1);
        double bh = button.prefHeight(-1);

        if (w == 0 || h == 0 || bw == 0 || bh == 0) return ;

        double hScale = w / bw ;
        double vScale = h / bw ;

        double scale = Math.min(hScale, vScale);

        button.lookup(".text").setScaleX(scale);
        button.lookup(".text").setScaleY(scale);
    }

	@Override
	public void changeAcc(String acc) {
		screenField.setText(acc);
	}
	
	
	@Override
	public void changeStack(ArrayList<String> list) {
		for (int i = 0; i < stackListSize; i++) {
			stackList.get(i).setText(list.get(i));
		}
	}
	
	
	@Override
	public void changeStatus(CalcStatus status) {
		statusLabel.setText(status.getMessage());
		if (status.isError()) {
			statusLabel.setTextFill(Color.RED);
		}
		else {
			statusLabel.setTextFill(Color.BLACK);
		}
	}
     
}