package application;

public class Orders /*implements Comparable<Student>*/{
	String GAME_NAME;
	String PRODUCER;
	String PUBLISHER;
	String RELEASED;
	String PLATFORM;
	
	public Orders(String GAME_NAME, String PRODUCER, String PUBLISHER, String RELEASED, String PLATFORM)
	{
		this.GAME_NAME = GAME_NAME;
		this.PRODUCER = PRODUCER;
		this.PUBLISHER = PUBLISHER;
		this.RELEASED = RELEASED;
		this.PLATFORM = PLATFORM;
	}
	public String getGAME_NAME() { return GAME_NAME; }
	public void setGAME_NAME(String gAME_NAME) {
		this.GAME_NAME = gAME_NAME;
	}
	public String getPLATFORM() { return PLATFORM; }
	public void setPLATFORM(String PLATFORM) {
		this.PLATFORM = PLATFORM;
	}
	public String getPRODUCER() {
		return PRODUCER;
	}
	public void setPRODUCER(String pRODUCER) {
		this.PRODUCER = pRODUCER;
	}
	public String getPUBLISHER() {
		return PUBLISHER;
	}
	public void setPUBLISHER(String pUBLISHER) {
		this.PUBLISHER = pUBLISHER;
	}
	public String getRELEASED() {
		return RELEASED;
	}
	public void setRELEASED(String rELEASED) {
		this.RELEASED = rELEASED;
	}
	@Override
	public String toString() {
		return "GAME NAME: " + GAME_NAME + " Producer: "+ PRODUCER + " Publisher: " + PUBLISHER + " Game release: "+ RELEASED+ "PLatform: " + PLATFORM +"\n";
	}
}