package org.skillissue.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class Data {

  private List<Sudoku> data;

  public Data(List<Sudoku> solvedSudokuList){
    this.data = solvedSudokuList;
  }

  public List<Sudoku> getSudokuList(){
    return data;
  }

  @Override
  public String toString() {
    return getSudokuList().toString();
  }
}
