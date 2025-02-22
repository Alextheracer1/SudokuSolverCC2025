package org.skillissue.Sudoku;

import java.util.ArrayList;

public class Sudokus
{

  private ArrayList<Sudoku> sudokuList;

  public Sudokus(ArrayList<Sudoku> sudokuList){
    this.sudokuList = sudokuList;
  }

  public ArrayList<Sudoku> getSudokuList() {
    return sudokuList;
  }
}
