import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import java.io.PrintWriter;

public class Greedy extends Heuristic{
  File file;
  
  public Greedy(File file) throws FileNotFoundException{
    this.file = file;
  }
  
  public int[] run() throws FileNotFoundException{
    ArrayList<Integer> usedCities = new ArrayList<Integer>();
    Duration runtime;
    int currentCity = 0; //start from first city
    usedCities.add(currentCity); //include start city
    double shortestDist;
    double totalDist = 0;
    int closestCity = -1; //if this value isn't updated, an error is caused
    Instant start = Instant.now(); //log start time of execution
    while(usedCities.size() < numCitiesToGen){
      shortestDist = Double.MAX_VALUE;
      for(int i = 0; i < numCitiesToGen; i++){ //loop through all cities
        if(!(usedCities.contains(i))){ //if the city in question hasn't been visited yet
          if(getDist(getCoords(file, currentCity), getCoords(file, i)) < shortestDist){ //if the current attempt is the shortest yet
            shortestDist = getDist(getCoords(file, currentCity), getCoords(file, i));
            closestCity = i;
          }
        }
      }
      usedCities.add(closestCity);
      totalDist += shortestDist;
      currentCity = closestCity;
      //System.out.println(usedCities.toString());
    }
    runtime = Duration.between(start, Instant.now());
    System.out.println("Greedy algorithm complete. Order: " + usedCities.toString() + 
                       ". Path length: " + totalDist + 
                       ". Runtime: " + (runtime.getNano() * nanoConvFactor) + " seconds");
    int[] pathArray = new int[usedCities.size()];
    for(int i = 0; i < usedCities.size(); i++) pathArray[i] = usedCities.get(i);
    return pathArray;
  }
}