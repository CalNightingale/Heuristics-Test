import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.Duration;
import java.time.Instant;

public class TwoApprox extends Heuristic{
  File file;
  
  public TwoApprox(File file){
    this.file = file;
  }
  
  private int[] updateKeys(int[] key, ArrayList<Integer> citiesToCheck, int currentCity) throws FileNotFoundException{
    int[] cityList = new int[citiesToCheck.size()];
    for(int i = 0; i < citiesToCheck.size(); i++) cityList[i] = citiesToCheck.get(i);
    int[] adjacent = getAdjacentCities(currentCity, cityList, file);
    for(int i = 0; i < adjacent.length; i++){
      int weight = (int)Math.round(getDist(getCoords(file, currentCity), getCoords(file, adjacent[i])));
      if(weight < key[adjacent[i]]){
        key[adjacent[i]] = weight;
      }
    }
    return key;
  }
  
  public int[] genKey() throws FileNotFoundException{ //generates a Minimum Spanning Tree using prim's algorithm
    ArrayList<Integer> usedCities = new ArrayList<Integer>();
    ArrayList<Integer> availableCities = new ArrayList<Integer>();
    for(int i = 0; i < numCitiesToGen; i++){ //all cities are available at the beginning
      availableCities.add(i);
    }
    int[] key = new int[numCitiesToGen];
    Arrays.fill(key, Integer.MAX_VALUE); //assign each city the largest value possible, so that it gets updated later
    key[0] = 0; //ensure start city is selected first
    int currentIndex = -1; //ensures error will be caused if var is not updated
    while(usedCities.size() < key.length){
      currentIndex = getMinIndex(key);
      usedCities.add(availableCities.remove(currentIndex));
      key = updateKeys(key, availableCities, currentIndex);
    }
    return key;
  }
  
  private int getLowestAdjWeight(int currentCity, int[] key, ArrayList<Integer> availableCities) throws FileNotFoundException{
    if(availableCities.contains(0) && currentCity == 0) availableCities.remove(currentCity);
    int numCities = availableCities.size();
    if (numCities == 1) return availableCities.get(0); //if there's only 1 city left, return it 
    int[] availCitArray = new int[numCities];
    for(int i = 0; i < availCitArray.length; i++) availCitArray[i] = availableCities.get(i);
    int[] adjacent = getAdjacentCities(currentCity, availCitArray, file);
    int lowestWeight = Integer.MAX_VALUE; //forces update
    int lowestWeightIndex = -1;
    for(int i: adjacent){
      if(key[i] < lowestWeight){
        lowestWeight = key[i];
        lowestWeightIndex = i;
      }
    }
    return lowestWeightIndex;
  }
  
  public int[] run() throws FileNotFoundException{
    Instant start = Instant.now();
    ArrayList<Integer> path = new ArrayList<Integer>();
    path.add(0); //start point is start city
    ArrayList<Integer> availableCities = new ArrayList<Integer>();
    for(int i = 0; i < numCitiesToGen; i++) availableCities.add(i); //gen list of all available cities
    availableCities.remove(0); //remove start city
    int[] key = genKey();
    int lowestAdjWeight = -1;
    for(int i = 1; i < numCitiesToGen; i++){ //adds the lowest weighted city  to path AND removes it from list of available cities
      lowestAdjWeight = getLowestAdjWeight(i, key, availableCities);
      path.add(lowestAdjWeight);
      availableCities.remove(availableCities.indexOf(lowestAdjWeight));
    }
    Duration runtime = Duration.between(start, Instant.now());
    System.out.println("Spanning Tree algorithm complete. Order: " + path.toString() +
                       ". Path length: " + getPathLength(file, path) +
                       ". Runtime: " + (runtime.getNano() * nanoConvFactor) + " seconds");
    int[] pathArray = new int[path.size()];
    for(int i = 0; i < path.size(); i++) pathArray[i] = path.get(i);
    return pathArray;
  }
}