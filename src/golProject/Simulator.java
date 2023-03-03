package golProject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Simulator {

	static String createRandomGrid(int rows, int cols) {
		Random random = new Random();
		String grid = new String("");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid += random.nextBoolean() ? "0" : "1";
			}
			if (i + 1 < rows) {
				grid += "#";
			}
		}
		;
		return grid;
	}

	public static void main(String[] args) throws IOException {
		

		int width = -1;
		int height = -1;
		int generations = -1;
		int sleepTime = -1;
		String pattern = "-1";

		if (args.length > 0) {

			for (String arg : args) {

				String[] parts = arg.split("=");
				String key = parts[0];
				String value = parts[1];

				switch (key) {
				case "w":
					width = Integer.parseInt(value);
					break;
				case "h":
					height = Integer.parseInt(value);
					break;
				case "g":
					generations = Integer.parseInt(value);
					break;
				case "s":
					sleepTime = Integer.parseInt(value);
					break;
				case "p":
					pattern = value;
					break;
				default:
					System.out.println("Parâmetro inválido: " + arg);
					return;
				}
			}
		}

		if (width < 0 || height < 0 || generations < 0 || sleepTime < 0 || pattern.equals("-1") || args.length == 0) {
			System.out.println("Complete todos os parâmetro! Ex.\n  w=10 h=10 g=500 s=1000 p=\"rnd\"");
			return;

		}
		if (width != 10 && width != 20 && width != 40 && width != 80) {
			System.out.println("Medida w inválida");
			return;
		}

		if (height != 10 && height != 20 && height != 40) {
			System.out.println("Medida h inválida");
			return;
		}

		if (!(sleepTime >= 250 && sleepTime <= 1000)) {
			System.out.println("Medida s inválida");
			return;
		}
		boolean infinite = false;
		if(generations == 0) {
			infinite  = true; 
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					System.exit(0);
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}
			});
		}

		int sizeRowsMatrix = width;
		int sizeColsMatrix = height;

		int numberOfGenerations = generations;
		String newGridInString = pattern.equals("rnd") ? createRandomGrid(width, height) : pattern;

		System.out.println(newGridInString);

		String[] splitString = newGridInString.split("#");

		List<List<Cell>> matrixBasedOfString = new ArrayList<>();

		for (String row : splitString) {

			List<Cell> lineBasedOfString = new ArrayList<>();
			String[] col = row.split("");
			for (String c : col) {
				if (c.equals("1")) {
					lineBasedOfString.add(new Cell(true));
				} else {
					lineBasedOfString.add(new Cell(false));
				}

			}
			matrixBasedOfString.add(lineBasedOfString);

		}

		// Essas são as 4 regras:

		// Para um espaço que é preenchido
		// cada célula com um ou nenum vizinho morre, como se fosse por solidão.
		Rule rule1 = new Rule(0, false, true);
		Rule rule2 = new Rule(1, false, true);

		// cada célula com quatro ou mais vizinhos morre, como se por superpopulação.
		Rule rule3 = new Rule(4, false, true);
		Rule rule4 = new Rule(5, false, true);
		Rule rule5 = new Rule(6, false, true);
		Rule rule6 = new Rule(7, false, true);
		Rule rule7 = new Rule(8, false, true);

		// Cada célula com dois ou três vizinhos sobrevive.
		Rule rule8 = new Rule(2, true, true);
		Rule rule9 = new Rule(3, true, true);

		// Para um espaço vazio ou não preenchido.
		// Cada célula com três vizinhos torna-se povoada
		Rule rule10 = new Rule(3, true, false);

		DisplayCell displaygame = new DisplayCell(matrixBasedOfString, sizeRowsMatrix, sizeColsMatrix);

		System.out.println("Geração inicial");
		displaygame.printDisplay();

		int count = 0;
		
		while(count < numberOfGenerations || infinite) {

			GeneratorGeneration gerador = new GeneratorGeneration(displaygame.getMatrix());
			gerador.addRule(rule1);
			gerador.addRule(rule2);
			gerador.addRule(rule3);
			gerador.addRule(rule4);
			gerador.addRule(rule5);
			gerador.addRule(rule6);
			gerador.addRule(rule7);
			gerador.addRule(rule8);
			gerador.addRule(rule9);
			gerador.addRule(rule10);
			List<List<Cell>> nextGeneration = gerador.nextGeneration();
			displaygame.setMatrix(nextGeneration);
			System.out.println("\nGeneration " + (count + 1) + "");
			displaygame.printDisplay();

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			
			count++;
		}
		
		System.out.println("Programa GOL finalizado");

	}

}
