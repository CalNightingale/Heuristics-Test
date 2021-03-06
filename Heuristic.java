import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Heuristic{
  
  public static int numCitiesToGen = 7;
  public static double nanoConvFactor = Math.pow(10, -8); //conversion factor for converting nanoseconds to seconds
  
  public double getDist(int[] a, int[] b){ //returns the distance between two cities (a and b)
    return Math.sqrt(Math.pow((a[0] - b[0]), 2) + Math.pow((a[1] - b[1]), 2));
  }
  
  public static int[] getCoords(File file, int line) throws FileNotFoundException{ //returns city location given line number in file
    Scanner reader = new Scanner(file);
    int[] coords = new int[2];//[0] is x and [1] is y
    for(int i = 0; i < line; i++){ //LINE PARAMETER IS ZERO-INDEXED
      if(reader.hasNextLine()) reader.nextLine();
      else return null;
    }
    coords[0] = reader.nextInt();
    coords[1] = reader.nextInt();
    reader.close();
    //System.out.println(coords[0] + ", " + coords[1]); //FOR DEBUGGING
    return coords;
  }
  
  public double getPathLength(File file, ArrayList<Integer> path)throws FileNotFoundException{
    double totalDist = 0;
    for(int i = 0; i < path.size() - 1; i++){
      totalDist += getDist(getCoords(file, path.get(i)), getCoords(file, path.get(i + 1)));
    }
    return totalDist;
  }
  
  public int[] getCityList(){
    int[] cities = new int[numCitiesToGen];
    for(int i = 0; i < cities.length; i++){ //generate list of cities in numerical order
      cities[i] = i;
    }
    return cities;
  }
  
  public int getMaxIndex(ArrayList<Double> list){
    double maxVal = Double.MIN_VALUE;
    int maxIndex = -1;
    for(int i = 0; i < list.size(); i++){
      if(list.get(i) > maxVal){
        maxVal = list.get(i);
        maxIndex = i;
      }
    }
    return maxIndex;
  }
  
  public int[] getAdjacentCities(int city, int[]map, File file) throws FileNotFoundException{ //returns the nearest 4 cities
    if(map.length < 5) return map; //prevents IndexOutOfBoundsExceptions; if <=4 cities are left, skip search
    ArrayList<Integer> validCities = new ArrayList<Integer>();
    ArrayList<Double> vCDist = new ArrayList<Double>();
    for(int i: map){
      validCities.add(i); //initialize arraylist
      vCDist.add(getDist(getCoords(file, city), getCoords(file, i)));
    }
    double distToCity = -1;
    int currentCity = -1;
    while(vCDist.size() > 4){
      int indexOfMaxDist = getMaxIndex(vCDist);
      vCDist.remove(indexOfMaxDist);
      validCities.remove(indexOfMaxDist);
    }
    int[] adjacent = {validCities.get(0), validCities.get(1), validCities.get(2), validCities.get(3)};
    //System.out.println(validCities.toString());
    return adjacent;
  }
  
  public int getMinIndex(int[] arr){
    int min = Integer.MAX_VALUE; //ensures var will be updated
    int minIndex = -1; //ensures error will be caused if var is not updated
    for(int i = 0; i < arr.length; i++){
      if(arr[i] < min){
        min = arr[i];
        minIndex = i;
      }
    }
    return minIndex;
  }
  
  private int getFarthestCity(int[] arr, int city, File file) throws FileNotFoundException{
    double maxDist = Double.MIN_VALUE; //ensures var will be updated
    int maxIndex = -1; //ensures error will be caused if var is not updated
    double currentDist = -1;
    for(int i: arr){
      currentDist = getDist(getCoords(file, arr[i]), getCoords(file, city));
      if(currentDist > maxDist){
        maxDist = currentDist;
        maxIndex = i;
      }
    }
    return maxIndex;
  }
  
  public ArrayList<Integer> genDefaultPath(){
    ArrayList<Integer> defaultPath = new ArrayList<Integer>();
    for(int i = 0; i < numCitiesToGen; i++) defaultPath.add(i);
    return defaultPath;
  }
}