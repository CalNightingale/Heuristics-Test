import java.io.PrintWriter;
import static java.lang.Math.toIntExact;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.Duration;
import java.util.Scanner;

public class HeuristicTest extends Heuristic{
  static int numCitiesToGen = 7;
  
  public static void genCities(int numCities, File file) throws FileNotFoundException{
    //create writer to record randomly generated cities
    PrintWriter writer = new PrintWriter(file);
    int x = 0;
    int y = 0;
    for(int i = 0; i < numCities; i++){
//coordinate values generated randomly between 0 and 10^6, as specified on page 5 of the heuristics analysis article
      x = toIntExact(Math.round(Math.random()*Math.pow(10, 6)));
      y = toIntExact(Math.round(Math.random()*Math.pow(10, 6)));
      writer.println(x + " " + y);//values separated by spaces to make parsing more efficient
      //System.out.println(x + "," + y);
    }
    writer.close();
  }
  
  public static void drawPath(int[] path, File file) throws FileNotFoundException{
    int[] coordHolder = new int[2];
    double[] x = new double[path.length]; double[] y = new double[path.length];
    for(int i = 0; i < path.length; i++){ //assign the coordinates of all cities on path to x and y arrays 
      coordHolder = getCoords(file, path[i]);
      x[i] = coordHolder[0]; y[i] = coordHolder[1];
    }
    StdDraw.setCanvasSize(800, 800);
    StdDraw.setScale(0, Math.pow(10, 6) + 1000); //sets scale to include all possible city locations
    StdDraw.setPenRadius(0.05);
    StdDraw.polygon(x, y);
  }
  
  public static void main(String[] args) throws FileNotFoundException{
    Duration runTime;
    File file = new File("C:/Users/calni/Documents/Programs/cities.txt");
    BruteForce bruteForce = new BruteForce(file);
    Greedy greedy = new Greedy(file);
    TwoApprox twoApprox = new TwoApprox(file);
    genCities(numCitiesToGen, file);
    System.out.println("DEBUG: run initialized");
    greedy.run();
    //bruteForce.run();
    drawPath(twoApprox.run(), file);
    //twoApprox.drawPath(file);
    System.out.println("DEBUG: run completed");
  }
}