import java.util.ArrayList;

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
}
