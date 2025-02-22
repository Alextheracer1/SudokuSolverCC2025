package org.skillissue.Sudoku;

public class Sudoku {

  private int id;
  private int[][] template;

  public Sudoku(int id, int[][] solved) {
    this.id = id;
    this.template = solved;
  }

  public int getId() {
    return id;
  }

  public int[][] getSolved() {
    return template;
  }

  public void setSolved(int[][] solved) {
    this.template = solved;
  }
}
