import java.util.ArrayList;
import java.text.DecimalFormat;

public class Player{

  private String name; //Player name
  private String username; //Player reddit username
  private double era; //Player ERA
  private double avg; //Player Average
  private ArrayList<String> positions; //List of positions they can play

  //Default constructor sets everything to default
  public Player(){
    name = "";
    username = "";
    era = 0.0;
    avg = 0.0;

    positions = new ArrayList<String>();
  }

  //Sets player name
  public void setPlayerName(String name){
    this.name = name;
  }

  //Gets player name
  public String getPlayerName(){
    return this.name;
  }

  //Sets username
  public void setUsername(String name){
    this.username = name;
  }

  //Gets username
  public String getUsername(){
    return this.username;
  }

  //Adds position to valid positions
  public void addPosition(String pos){
    positions.add(pos);
  }

  //Gets list of eligible positions
  public ArrayList<String> getPositions(){
    return this.positions;
  }

  //Sets ERA
  public void setEra(double era){
    this.era = era;
  }

  //Gets ERA
  public double getEra(){
    return this.era;
  }

  //Sets average
  public void setAvg(double avg){
    this.avg = avg;
  }

  //Gets average
  public double getAvg(){
    return this.avg;
  }

  //Checks if a position is a position that player can play
  public boolean isValidPosition(String pos){

    //Check all valid positions
    for(String position : positions){

      //If equal true
      if(pos.equals(position)){
        return true;
      }

    }

    return false; //If no positions are equal, false
  }

  //Gets the String for what needs to be written to the file for that player
  public String getString(int i, String position){

    String toReturn = "";
    DecimalFormat df = new DecimalFormat("#.000");

    toReturn += "**" + (i+1) + "**" + "|";
    toReturn += "[" + this.name + "](" + this.username + ")"  + "|";
    toReturn += position  + "|";
    toReturn +=  "0|0|0|0|0|0|";
    toReturn += df.format(this.avg)  + "|";

    return toReturn;
  }

}
