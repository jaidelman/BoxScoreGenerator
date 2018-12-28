import java.util.ArrayList;
import java.text.DecimalFormat;

public class Player{

  private String name;
  private String username;
  private double era;
  private double avg;
  private ArrayList<String> positions;

  public Player(){
    name = "";
    username = "";
    era = 0.0;
    avg = 0.0;

    positions = new ArrayList<String>();
  }

  public void setPlayerName(String name){
    this.name = name;
  }

  public String getPlayerName(){
    return this.name;
  }

  public void setUsername(String name){
    this.username = name;
  }

  public String getUsername(){
    return this.username;
  }

  public void addPosition(String pos){
    positions.add(pos);
  }
  public ArrayList<String> getPositions(){
    return this.positions;
  }

  public void setEra(double era){
    this.era = era;
  }

  public double getEra(){
    return this.era;
  }

  public void setAvg(double avg){
    this.avg = avg;
  }

  public double getAvg(){
    return this.avg;
  }

  //Checks if a position is a position that player can play
  public boolean isValidPosition(String pos){

    for(String position : positions){

      if(pos.equals(position)){
        return true;
      }

    }

    return false;
  }

  //Sends the format for the player in the box score
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
