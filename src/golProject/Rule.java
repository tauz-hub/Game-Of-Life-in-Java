package golProject;

public class Rule {

	int numberOfNeighborsToApplyRule;
	boolean resultGivesCellAfterPassingRule;
	boolean currentStateOfCell;
	
	public Rule(int numberOfNeighborsToApplyRule, boolean resultGivesCellAfterPassingRule, boolean currentStateOfCell) {
		this.numberOfNeighborsToApplyRule = numberOfNeighborsToApplyRule;
		this.resultGivesCellAfterPassingRule = resultGivesCellAfterPassingRule;
		this.currentStateOfCell = currentStateOfCell;
	}
	public int getNumberNeighbors() {
		return this.numberOfNeighborsToApplyRule;
	}
	public boolean getCurrentStateOfCell() {
		return this.currentStateOfCell;
	}
	public Cell evaluate (int numberOfNeighbors, Cell cell) {
		Cell newCell = new Cell(cell.getIsAlive());
		if(numberOfNeighborsToApplyRule == numberOfNeighbors && cell.getIsAlive() == currentStateOfCell) {
			newCell.setIsAlive(resultGivesCellAfterPassingRule);
		}
		return newCell;
	}

}
