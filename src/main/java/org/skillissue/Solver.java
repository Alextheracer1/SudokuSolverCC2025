package org.skillissue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.skillissue.Algorithm.Grid;
import org.skillissue.Sudoku.Data;
import org.skillissue.Sudoku.Sudoku;
import org.skillissue.Sudoku.SudokuHash;

public class Solver implements Runnable{


  @Override
  public void run() {
    try {
      SSLBypass.disableSSLVerification();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    Gson gson = new Gson();
    String apiGetSudokus = "https://193.170.119.74/hashsudokus";
    String apiValidate = "https://193.170.119.74/validate-sudokus-hash";
    String jwtToken = "67b71073d3b60cde98e8dba2___eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InNraWxsaXNzdWVAZmgtc2FsemJ1cmcuYWMuYXQiLCJpYXQiOjE3NDAwNTA2MzUsImV4cCI6MTc0MDQ4MjYzNX0.CY334eFnuFoRmynXT6w9LI670TPJ6ne_eiQyMdTqWF0";

    List<Sudoku> sudokuList;

    while (true) {
      try {

        URL urlGet = new URL(apiGetSudokus);
        HttpsURLConnection getConnection = (HttpsURLConnection) urlGet.openConnection();
        getConnection.setRequestMethod("GET");
        getConnection.setRequestProperty("Cookie", "auth=" + jwtToken);

        Type personListType = new TypeToken<List<Sudoku>>() {}.getType();

        sudokuList = gson.fromJson(
            new InputStreamReader(getConnection.getInputStream()),
            personListType);

        getConnection.disconnect();

        if (sudokuList == null || sudokuList.isEmpty()) {
          System.out.println("No Sudoku puzzles found in the JSON file.");
          return;
        }

        for (Sudoku sudoku : sudokuList) {
          Grid grid = Grid.of(sudoku.getSolved());
          org.skillissue.Algorithm.Solver solver = new org.skillissue.Algorithm.Solver();
          solver.solve(grid);

          int[][] solvedGrid = new int[9][9];
          for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
              solvedGrid[i][j] = grid.getCell(i, j).getValue();
            }
          }
          sudoku.setSolved(solvedGrid);
        }

        Data data = new Data(sudokuList);

        String json = gson.toJson(data);

        String hashJson = DigestUtils.sha1Hex(json);

        SudokuHash sudokuHash = new SudokuHash(hashJson);

        String sudokuHashJson = gson.toJson(sudokuHash);

        //System.out.println("Generated JSON:");
        //System.out.println(sudokuHashJson);


        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient()) {
          // Define the API endpoint
          HttpPost httpPost = new HttpPost(apiValidate);

          // Set the request body
          StringEntity entity = new StringEntity(sudokuHashJson);
          httpPost.setEntity(entity);

          // Set headers
          httpPost.setHeader("Content-Type", "application/json");
          httpPost.setHeader("Cookie", "auth=" + jwtToken);

          // Execute the request
          try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // Get the response code
            //int statusCode = response.getStatusLine().getStatusCode();
            //System.out.println("Response Code: " + statusCode);

          }
        } catch (Exception e) {
        }

      } catch (Exception e) {
      }
    }
  }
}
