import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class BoxScore{

  public static void main(String[] args){

    String filename, home, away;
    Scanner input = new Scanner(System.in);
    ArrayList<Player> awayPlayers, homePlayers;

    System.out.println("Please enter the away team name (Eg: Red Sox)");
    away = input.nextLine();

    System.out.println("Please enter the home team name");
    home = input.nextLine();

    //Loads away roster
    System.out.println("Please enter the file name to load away roster");
    filename = input.nextLine();
    awayPlayers = loadPlayerList(filename);

    //Loads home roster
    System.out.println("Please enter the file name to load home roster");
    filename = input.nextLine();
    homePlayers = loadPlayerList(filename);

    writeBoxScore(awayPlayers, homePlayers, home, away);

  }

  public static ArrayList<Player> loadPlayerList(String filename){

    String line;
    String array[];
    int count = 0;
    ArrayList<Player> playerList = new ArrayList<Player>();

    try(BufferedReader input = new BufferedReader(new FileReader(filename))){

      while((line = input.readLine()) != null){

        Player cur = new Player();
        array = line.split(",");

        if(array.length > 34 && count > 1){
          cur.setPlayerName(array[3]);
          cur.setUsername(array[4]);
          cur.setAvg(Double.valueOf(array[23]));
          cur.setEra(Double.valueOf(array[35]));
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

  public static void writeBoxScore(ArrayList<Player> away, ArrayList<Player> home, String homeName, String awayName){

    String toWrite = "";
    String name, position;
    Scanner input = new Scanner(System.in);
    int found;

    try{
      BufferedWriter w = new BufferedWriter(new FileWriter("boxScore.txt"));

      toWrite += "\\#|" + awayName + "|Pos|AB|R|H|RBI|BB|SO|BA|\\#|" + homeName + "|Pos|AB|R|H|RBI|BB|SO|BA|\n";
      toWrite += ":--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--|:--\n";

      for(int i = 0; i<9; i++){

        //Away players
        found = 0;
        System.out.print("Please enter away player " + (i+1) + ": ");
        name = input.nextLine();

        for(Player player : away){
          if(name.equals(player.getPlayerName())){

            System.out.print("Please enter position: ");
            position = input.nextLine();

            toWrite += "**" + (i+1) + "**" + "|";
            toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
            toWrite += position  + "|";
            toWrite +=  "0|0|0|0|0|0|";
            toWrite += player.getAvg()  + "|";
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
        System.out.print("Please enter home player " + (i+1) + ": ");
        name = input.nextLine();

        for(Player player : home){
          if(name.equals(player.getPlayerName())){

            System.out.print("Please enter position: ");
            position = input.nextLine();

            toWrite += "**" + (i+1) + "**" + "|";
            toWrite += "[" + name + "](" + player.getUsername() + ")"  + "|";
            toWrite += position  + "|";
            toWrite +=  "0|0|0|0|0|0|";
            toWrite += player.getAvg()  + "|";
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

}
