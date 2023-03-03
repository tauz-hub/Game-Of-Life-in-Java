package golProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DisplayCell {

	private List<List<Cell>> matrixCells;
	private int lengthCols;
	private int lengthRows;
	
	public DisplayCell(List<List<Cell>> matrixCells,int lengthRows, int lengthCols) {
		List<List<Cell>> newMatrixFull = new ArrayList<>();
		
		if(matrixCells.size() < lengthRows || matrixCells.get(0).size() < lengthCols) {

			for(int r = 0; r < lengthRows; r++) {
				List<Cell> line = new ArrayList<Cell>();
				for (int c = 0; c < lengthCols; c++) {
					
			        if (matrixCells.size() > r && matrixCells.get(r).size() > c && matrixCells.get(r).get(c) != null) {
			            line.add(matrixCells.get(r).get(c));
			        } else {
			            line.add(new Cell(false));
			        }
			        
			    }
				newMatrixFull.add(line);
			}
		}
	
		this.matrixCells = newMatrixFull.size() == 0 ? matrixCells : newMatrixFull;
		this.setLengthCols(lengthCols);
		this.setLengthRows(lengthRows);
	}
	
	public List<List<Cell>> getMatrix() {
		return this.matrixCells;
	}
	public void setMatrix(List<List<Cell>> matrixCells) {
		this.matrixCells = matrixCells;
	}
	
	public Cell getCell(int positionRow, int positionCol) {
		Cell cell = this.matrixCells.get(positionRow).get(positionCol);
		return cell;
	}
	
	public void printDisplay() {
		for (List<Cell> line : this.matrixCells) {
			String stringLine = "";
			for(Cell col : line) {
				if(col.getIsAlive()) {
					 stringLine += "1"; //🔴  || 0  || .
				} else {
					 stringLine += "0"; //🟨  ||  1 || X
				}
			}
			System.out.println(stringLine);
		}
	}

	public int getLengthCols() {
		return lengthCols;
	}
	
	public void setLengthCols(int lengthCols) {
		this.lengthCols = lengthCols;
	}

	public int getLengthRows() {
		return lengthRows;
	}

	public void setLengthRows(int lengthRows) {
		this.lengthRows = lengthRows;
	}
	

}
