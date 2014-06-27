package br.org.fieb.heijunka.model;

//It doesnt have validation. For instance, endTime before StartTime
//Better to use schedule/calendar
public class TimeSlot{
	//considering only hour, not minutes and seconds.
	private int startTime; //e.g.: 10 instead of 10:45:00
	private int endTime;  //       12 instead of 12:15:00
	
	public TimeSlot(int startTime, int endTime){
		setStartTime(startTime);
		setEndTime(endTime);
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	//Maybe improve this. There's better way to do this
	public int getTimeIntervalInHours(){
		return (endTime - startTime);
	}
	
	public int getTimeIntervalInMinutes(){
		return (endTime - startTime) * 60;
	}
}