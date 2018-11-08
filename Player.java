

public class Player{

  private String name;
  private String username;
  private double era;
  private double avg;

  public Player(){
    name = "";
    username = "";
    era = 0.0;
    avg = 0.0;
  }

  public void setPlayerName(String name){
    this.name = name;
  }

  public String getPlayerName(){
    return this.name;
  }

  public void setUserName(String name){
    this.username = name;
  }

  public String getUserName(){
    return this.username;
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
