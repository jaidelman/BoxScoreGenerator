import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.io.*;

public class BoxScore{

  private static String homeTrim, awayTrim, homeName, awayName; //Team names
  private static int invalidLineup = 0; //Boolean for invalid Lineup

  public static void main(String[] args){

    String filename;
    Scanner input = new Scanner(System.in);
    ArrayList<Player> awayPlayers, homePlayers;

    System.out.println("Please enter the away team name (Eg: Red Sox)");
    awayName = input.nextLine();

    System.out.println("Please enter the home team name");
    homeName = input.nextLine();

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

    writeLine();
    writeBoxScore(awayPlayers, homePlayers);

    if(invalidLineup == 0){
      writePitchers(awayPlayers, homePlayers);
      System.out.println("Saved box score as " + homeTrim + awayTrim + ".txt");
    }
    else{
      System.out.println("Program Terminated");
    }


  }

  public static ArrayList<Player> loadPlayerList(String filename){

    String line;
    String array[];
    int count = 0;
    ArrayList<Player> playerList = new ArrayList<Player>();

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

      while((line = input.readLine()) != null){

        Player cur = new Player();
        array = line.split(",");

        if(array.length > 34 && count > 1){
          cur.setPlayerName(array[3]);
          cur.setUsername(array[4]);
          cur.setAvg(Double.valueOf(array[23]));
          cur.setEra(Double.valueOf(array[35]));

          //Adds possible positions
          cur.addPosition(array[0]);
          cur.addPosition(array[1]);
          cur.addPosition(array[2]);
          cur.addPosition("DH"); //Anyone can DH

          playerList.add(cur);
        }

        count++;
      }
      input.close();
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

  public static void writeBoxScore(ArrayList<Player> away, ArrayList<Player> home){

    String toWrite = "";
    String name, position;
    Scanner input = new Scanner(System.in);
    int found, validPosition;
    DecimalFormat df = new DecimalFormat("#.000");

    try{
      BufferedWriter w = new BufferedWriter(new FileWriter(homeTrim + awayTrim + ".txt", true));

      toWrite += "##BOX\n\\#|" + awayName + "|Pos|AB|R|H|RBI|BB|SO|BA|\\#|" + homeName + "|Pos|AB|R|H|RBI|BB|SO|BA|\n";
      toWrite += ":--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--\n";

      for(int i = 0; i<9; i++){

        //Away players
        found = 0;
        System.out.print("Please enter " + awayName + " player " + (i+1) + ": ");
        name = input.nextLine();

        for(Player player : away){
          if(name.equals(player.getPlayerName())){

            System.out.print("Please enter position: ");
            position = input.nextLine();

            //Checks if player can play that position
            validPosition = 0;
            for(String positions : player.getPositions()){

              if(position.equals(positions)){
                validPosition = 1;
              }

            }

            if(validPosition == 0){
              System.out.println("ERROR: " + name + " cannot play that position!");
              invalidLineup = 1;
              return;
            }

            toWrite += "**" + (i+1) + "**" + "|";
            toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
            toWrite += position  + "|";
            toWrite +=  "0|0|0|0|0|0|";
            toWrite += df.format(player.getAvg())  + "|";
            found = 1;

          }
        }
        if(found == 0){
          System.out.println("PLAYER NOT FOUND");
          toWrite += "**" + (i+1) + "**" + "|";
          toWrite += "EMPTY|?|0|0|0|0|0|0|.000|";
        }

        //Home Players
        found = 0;
        System.out.print("Please enter " + homeName + " player " + (i+1) + ": ");
        name = input.nextLine();

        for(Player player : home){
          if(name.equals(player.getPlayerName())){

            System.out.print("Please enter position: ");
            position = input.nextLine();

            //Checks if player can play that position
            validPosition = 0;
            for(String positions : player.getPositions()){

              if(position.equals(positions)){
                validPosition = 1;
              }

            }

            if(validPosition == 0){
              System.out.println("ERROR: " + name + " cannot play that position!");
              invalidLineup = 1;
              return;
            }
            
            toWrite += "**" + (i+1) + "**" + "|";
            toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
            toWrite += position  + "|";
            toWrite +=  "0|0|0|0|0|0|";
            toWrite += df.format(player.getAvg())  + "|";
            found = 1;

          }

        }
        if(found == 0){
          System.out.println("PLAYER NOT FOUND");
          toWrite += "**" + (i+1) + "**" + "|";
          toWrite += "EMPTY|?|0|0|0|0|0|0|.000|";
        }

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

  public static void writeLine(){

    String toWrite = "";

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

  public static void writePitchers(ArrayList<Player> away, ArrayList<Player> home){

    String toWrite = "";
    String name;
    Scanner input = new Scanner(System.in);
    int found;

    try{
      BufferedWriter w = new BufferedWriter(new FileWriter(homeTrim + awayTrim + ".txt", true));

      toWrite += "\n##PITCHERS\n" + awayName + "|IP|H|ER|BB|SO|ERA/6|" + homeName + "|IP|H|ER|BB|SO|ERA/6|\n";
      toWrite += ":--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--\n";

      //Away pitcher
      found = 0;
      System.out.print("Please enter " + awayName + " pitcher: ");
      name = input.nextLine();

      for(Player player : away){
        if(name.equals(player.getPlayerName())){

          toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
          toWrite +=  "0.0|0|0|0|0|";
          toWrite += String.format("%.2f", player.getEra())  + "|";
          found = 1;

        }
      }
      if(found == 0){
        System.out.println("PLAYER NOT FOUND");
        toWrite += "EMPTY|0.0|0|0|0|0|0.00|";
      }

      //Home pitcher
      found = 0;
      System.out.print("Please enter " + homeName + " pitcher: ");
      name = input.nextLine();

      for(Player player : home){
        if(name.equals(player.getPlayerName())){

          toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
          toWrite +=  "0.0|0|0|0|0|";
          toWrite += String.format("%.2f", player.getEra())  + "|";
          found = 1;

        }
      }
      if(found == 0){
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
}
