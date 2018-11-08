import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class BoxScore{

  public static void main(String[] args){

    String filename;
    Scanner input = new Scanner(System.in);
    ArrayList<Player> playerList1, playerList2;

    //Loads away roster
    System.out.println("Please enter the file name to load away roster");
    filename = input.nextLine();
    playerList1 = loadPlayerList(filename);

    //Loads home roster
    System.out.println("Please enter the file name to load home roster");
    filename = input.nextLine();
    playerList2 = loadPlayerList(filename);

  }

  public static ArrayList<Player> loadPlayerList(String filename){

    String line;
    String array[];
    ArrayList<Player> playerList = new ArrayList<Player>();

    try(BufferedReader input = new BufferedReader(new FileReader(filename))){

      while((line = input.readLine()) != null){

        Player cur = new Player();
        array = line.split(",");

        cur.setPlayerName(array[3]);
        cur.setUserName(array[4]);
        cur.setAvg(Double.valueOf(array[23]));
        cur.setEra(Double.valueOf(array[35]));

        playerList.add(cur);


        input.close();
      }
      return playerList;
    }
    catch(FileNotFoundException e){
      System.out.println("ERROR: FILE NOT FOUND");
      return null;
    }
    catch(IOException e){
      System.out.println("ERROR: IO STREAM INTERUPPTED");
      return null;
    }

  }

}
