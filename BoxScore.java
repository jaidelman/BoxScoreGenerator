import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class BoxScore{

  private static String homeTrim, awayTrim, homeName, awayName; //Team names
  private static boolean invalidLineup = false; //Boolean for invalid Lineup
  private static Scanner input = new Scanner(System.in); //Global scanner

  public static void main(String[] args){

    String filename; //Filename
    ArrayList<Player> awayPlayers, homePlayers; //List of players on each team

    //Get away team name
    System.out.println("Please enter the away team name (Eg: Red Sox)");
    awayName = input.nextLine();

    //Get home team name
    System.out.println("Please enter the home team name");
    homeName = input.nextLine();

    //Deletes spaces for purpose of file name
    homeTrim = homeName.replace(" ", "");
    awayTrim = awayName.replace(" ", "");

    //Loads away roster
    System.out.println("Please enter the file name to load away roster");
    filename = input.nextLine();
    awayPlayers = loadPlayerList(filename);

    //Loads home roster
    System.out.println("Please enter the file name to load home roster");
    filename = input.nextLine();
    homePlayers = loadPlayerList(filename);

    writeLine(); //Writes the line (The top of the box score)
    writeBoxScore(awayPlayers, homePlayers); //Writes the position players into the box score

    //If both lineups are valid, contine
    if(invalidLineup == false){
      writePitchers(awayPlayers, homePlayers); //Writes pitchers to box score
      System.out.println("Saved box score as " + homeTrim + awayTrim + ".txt"); //Prints filename
      input.close(); //Close scanner
    }
    else{
      System.out.println("Program Terminated"); //If invalid lineup, close program
      input.close(); //Close scanner
    }


  }

  //Loads players from roster and puts them into list of players
  public static ArrayList<Player> loadPlayerList(String filename){

    String line; //To store line of file
    String array[]; //To store elements in line
    int count = 0; //To count which line we are on
    ArrayList<Player> playerList = new ArrayList<Player>(); //A list of all players in the file

    /*
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet("https://sheets.googleapis.com/v4/spreadsheets/1JxXUlVbF_c72OA_f9wdykNNkJ0k4Z49vsUl8J-qNzJA/values:batchGet");
    CloseableHttpResponse response1 = httpclient.execute(httpGet);

    try {
      System.out.println(response1.getStatusLine());
      HttpEntity entity1 = response1.getEntity();
      // do something useful with the response body
      // and ensure it is fully consumed
      EntityUtils.consume(entity1);
    } finally {
      response1.close();
    }
    */

    try(BufferedReader input = new BufferedReader(new FileReader(filename))){

      //While reading valid lines
      while((line = input.readLine()) != null){

        Player cur = new Player(); //Make new player
        array = line.split(","); //Split for CSV

        //Making sure that it is a player, and not the header
        if(array.length > 34 && count > 1){

          //Sets player attributes
          cur.setPlayerName(array[3]);
          cur.setUsername(array[4]);
          cur.setAvg(Double.valueOf(array[23]));
          cur.setEra(Double.valueOf(array[35]));

          //Adds possible positions
          cur.addPosition(array[0]);
          cur.addPosition(array[1]);
          cur.addPosition(array[2]);
          cur.addPosition("DH"); //Anyone can DH

          playerList.add(cur); //Adds player to list
        }

        count++; //Increment count
      }
      return playerList; //Return list
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

  //Writes player section of the box score
  public static void writeBoxScore(ArrayList<Player> away, ArrayList<Player> home){

    String toWrite = ""; //String to write to file
    String name; //Name of player

    try{
      BufferedWriter w = new BufferedWriter(new FileWriter(homeTrim + awayTrim + ".txt", true));

      //Writes the initial formatting
      toWrite += "##BOX\n\\#|" + awayName + "|Pos|AB|R|H|RBI|BB|SO|BA|\\#|" + homeName + "|Pos|AB|R|H|RBI|BB|SO|BA|\n";
      toWrite += ":--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--\n";

      //Loops through all 9 players in lineup to insert them. We must do away then home for proper reddit formatting
      for(int i = 0; i<9; i++){

        //Away players
        System.out.print("Please enter " + awayName + " player " + (i+1) + ": ");
        name = input.nextLine();
        toWrite += findPlayer(away, name, i);
        if(invalidLineup) return;

        //Home Players
        System.out.print("Please enter " + homeName + " player " + (i+1) + ": ");
        name = input.nextLine();
        toWrite += findPlayer(home, name, i);
        if(invalidLineup) return;

        toWrite += "\n";
      }


      //Write to file
      w.write(toWrite);
      w.close();
    }
    catch(IOException e){
      System.out.println("ERROR: IOException");
    }
  }

  //Writes the line in proper reddit formatting
  public static void writeLine(){

    String toWrite = ""; //String to write to file

    try{
      BufferedWriter w = new BufferedWriter(new FileWriter(homeTrim + awayTrim + ".txt"));

      toWrite += "#" + awayName + " 0 - 0 " + homeName + "\n\n##LINE\n";
      toWrite += "||1|2|3|4|5|6|R|H|\n:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--\n";
      toWrite += "|**" + awayName + "**|" + "**0**|-|-|-|-|-|**0**|**0**|\n";
      toWrite += "|**" + homeName + "**|" + "-|-|-|-|-|-|**0**|**0**|\n\n";
      toWrite += "##SCORING PLAYS\nInning|Team|Play|Score\n:--|:--|:--|:--\n\n";

      //Write to file
      w.write(toWrite);
      w.close();
    }
    catch(IOException e){
      System.out.println("ERROR: IOException");
    }
  }

  //Writes the pitchers to the box score. Pitchers have different stats and formatting than players
  public static void writePitchers(ArrayList<Player> away, ArrayList<Player> home){

    String toWrite = ""; //String to write to the file
    String name; //Name of pithcer
    boolean found; //If pitcher is found or not

    try{
      BufferedWriter w = new BufferedWriter(new FileWriter(homeTrim + awayTrim + ".txt", true));

      //Writes header
      toWrite += "\n##PITCHERS\n" + awayName + "|IP|H|ER|BB|SO|ERA/6|" + homeName + "|IP|H|ER|BB|SO|ERA/6|\n";
      toWrite += ":--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--\n";

      //Away pitcher
      found = false;
      System.out.print("Please enter " + awayName + " pitcher: ");
      name = input.nextLine();

      //Finds away pitcher and writes their line to the file
      for(Player player : away){
        if(name.equals(player.getPlayerName())){

          toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
          toWrite +=  "0.0|0|0|0|0|";
          toWrite += String.format("%.2f", player.getEra())  + "|";
          found = true;

        }
      }
      //If not found enter this format
      if(found == false){
        System.out.println("PLAYER NOT FOUND");
        toWrite += "EMPTY|0.0|0|0|0|0|0.00|";
      }

      //Home pitcher
      found = false;
      System.out.print("Please enter " + homeName + " pitcher: ");
      name = input.nextLine();

      //Finds home pitcher and writes their line to the file
      for(Player player : home){
        if(name.equals(player.getPlayerName())){

          toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
          toWrite +=  "0.0|0|0|0|0|";
          toWrite += String.format("%.2f", player.getEra())  + "|";
          found = true;

        }
      }
      //If not found enter this format
      if(found == false){
        System.out.println("PLAYER NOT FOUND");
        toWrite += "EMPTY|0.0|0|0|0|0|0.00|";
      }

      toWrite += "\n";

      //Write to file
      w.write(toWrite);
      w.close();
    }
    catch(IOException e){
      System.out.println("ERROR: IOException");
    }

  }

  //This function finds a position player and returns the string to write to the file in proper formatting
  public static String findPlayer(ArrayList<Player> players, String name, int i){

    String position; //Their position
    String toWrite = ""; //String to be written to file
    boolean found = false; //If player was found

    //Looks through all players
    for(Player player : players){

      //If they are found, get position
      if(name.equals(player.getPlayerName())){

        System.out.print("Please enter position: ");
        position = input.nextLine();

        //Checks if player can play that position
        if(!player.isValidPosition(position)){
          System.out.println("ERROR: " + name + " cannot play that position!");
          invalidLineup = true;
          return null;
        }

        //If valid, write player to file and set found to true
        toWrite += player.getString(i, position);
        found = true;

      }
    }

    //If not found, print not found
    if(found == false){
      System.out.println("PLAYER NOT FOUND");
      toWrite += "**" + (i+1) + "**" + "|";
      toWrite += "EMPTY|?|0|0|0|0|0|0|.000|";
    }

    return toWrite; //Return toWrite
  }

}
