import java.util.ArrayList;
import java.util.List;

public class GeneratorGeneration {
	private List<List<Cell>> currentGeneration = new ArrayList<>();
	private List<List<Cell>> nextGeneration = new ArrayList<>();
	private List<Rule> rules = new ArrayList<Rule>();

	public GeneratorGeneration(List<List<Cell>> currentGeneration) {
		this.currentGeneration = currentGeneration;
	}

	public void setCurrentGeneration(List<List<Cell>> matrixCells) {
		this.currentGeneration = matrixCells;
	}

	public int countNeighbors(int x, int y) {
		int neighbors = 0;
		int largura = this.currentGeneration.size();
		int altura = this.currentGeneration.get(0).size();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (x + i >= 0 && x + i < largura && y + j >= 0 && y + j < altura && (i != 0 || j != 0)) {
					if (this.currentGeneration.get(x + i).get(y + j).getIsAlive() == true) {
						neighbors++;

					}
				}
			}
		}

		return neighbors;
	}

	public Cell applyRules(int countNeighbors, Cell c) {

		Cell resultCell = c;
		for (Rule r : this.rules) {
			if (r.getNumberNeighbors() == countNeighbors && r.getCurrentStateOfCell() == c.getIsAlive()) {
				resultCell = r.evaluate(countNeighbors, c);
			}
		}

		return resultCell;
	}

	public List<List<Cell>> nextGeneration() {

		if (this.rules.size() < 1) {
			return this.currentGeneration;
		}
		for (List<Cell> l : this.currentGeneration) {
			List<Cell> newR = new ArrayList<Cell>();
			for (Cell c : l) {

				int x = this.currentGeneration.indexOf(l);
				int y = this.currentGeneration.get(x).indexOf(c);

				Cell resultCell = new Cell(false);

				int countNeighbors = this.countNeighbors(x, y);

				resultCell = applyRules(countNeighbors, c);

				newR.add(resultCell);

			}

			nextGeneration.add(newR);
		}
		return nextGeneration;
	}

	public void addRule(Rule rule) {
		this.rules.add(rule);
	}

	public List<List<Cell>> getCurrentGeneration() {
		return this.currentGeneration;
	}

}
