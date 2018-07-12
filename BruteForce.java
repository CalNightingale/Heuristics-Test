import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.Duration;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BruteForce extends Heuristic{
  File file;
  File debugFile = new File("C:/Users/calni/Documents/Programs/debug.txt");
  PrintWriter writer = new PrintWriter(debugFile);
  double shortestDist = Double.MAX_VALUE; //ensures this will be replaced by at least one permutation
  ArrayList<Integer> shortestPath;
  int prevCoord = 0;
  
  public BruteForce(File file) throws FileNotFoundException{
    this.file = file;
  }
  public Duration run() throws FileNotFoundException{
    Instant start = Instant.now(); //log start time of execution
    ArrayList<Integer> shortestPath = calcPerms(genDefaultPath()); //begin recursive function
    Duration runtime = Duration.between(start, Instant.now());
    System.out.println("BruteForce algorithm complete. Order: " + shortestPath.toString() + 
                       ". Path length: " + getPathLength(file, shortestPath) + 
                       ". Runtime: " + (runtime.getNano() * nanoConvFactor) + " seconds");
    return runtime;
  }
  
  public ArrayList<Integer> calcPerms(ArrayList<Integer> original) throws FileNotFoundException{
    //System.out.println("default path: " + original.toString());
    return getPermutation(new ArrayList<Integer>(), original);
  }
  
  private ArrayList<Integer> getPermutation(ArrayList<Integer> permSoFar, ArrayList<Integer> listToUse) throws FileNotFoundException{
    double currentPermLength = 0;
    if(listToUse.size() == 0){ //if the entire list has been used in a permutation, and there is no data to add
      currentPermLength = getPathLength(file, permSoFar);
      //System.out.println("perm finished. Perm: " + permSoFar.toString() + ". Length: " + currentPermLength);
      if(currentPermLength < shortestDist){ //if new permutation is shorter than the best so far
        shortestDist = currentPermLength;
        shortestPath = permSoFar;
      }
    } else { //permutation is not finished, continue building it by recalling this function (recursive)
      for (int i = 0; i < listToUse.size(); i++){
        //switch the next element from the original list to the permutation
        ArrayList<Integer> nextPerm = (ArrayList<Integer>)permSoFar.clone();
        ArrayList<Integer> nextList = (ArrayList<Integer>)listToUse.clone();
        nextPerm.add(listToUse.get(i));
        nextList.remove(i);
        getPermutation(nextPerm, nextList);
      }
    }
    return shortestPath;
  }
}