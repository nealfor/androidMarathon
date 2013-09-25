package marathon.training;

public class Race{
	private String raceName="";
	private String raceType="";
	private String racePace="";
	
	public String getName() {
		return(raceName);
	}
	public void setName(String name) {
		this.raceName=name;
	}
	public String getType() {
		return(raceType);
	}
	public void setType(String type) {
		this.raceType=type;
	}
	public String getPace() {
		return(racePace);
	}
	public void setPace(String pace) {
		this.racePace=pace;
	}
}//end Race class
