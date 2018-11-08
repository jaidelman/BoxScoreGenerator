

public class Player{

  private String name;
  private String username;
  private String position;
  private double era;
  private double avg;

  public Player(){
    name = "";
    username = "";
    position = "";
    era = 0.0;
    avg = 0.0;
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

  public void setPosition(String position){
    this.position = position;
  }

  public String getPosition(){
    return this.position;
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
