package org.skillissue.Sudoku;

public class SudokuHash {

  private String data;

  public SudokuHash(String hash) {
    this.data = hash;
  }

  public String getHash() {
    return data;
  }

  @Override
  public String toString() {
    return data;
  }
}
