package application;

public class Orders /*implements Comparable<Student>*/{
	private String GAME_NAME;
	private String PRODUCER;
	private String PUBLISHER;
	private String RELEASED;
	private String PLATFORM;
	
	public Orders(String GAME_NAME, String PRODUCER, String PUBLISHER, String RELEASED, String PLATFORM)
	{
		this.GAME_NAME = GAME_NAME;
		this.PRODUCER = PRODUCER;
		this.PUBLISHER = PUBLISHER;
		this.RELEASED = RELEASED;
		this.PLATFORM = PLATFORM;
	}
	public String getGAME_NAME() { return GAME_NAME; }
	public void setGAME_NAME(String GAME_NAME) {
		this.GAME_NAME = GAME_NAME;
	}
	public String getPLATFORM() { return PLATFORM; }
	public void setPLATFORM(String PLATFORM) {
		this.PLATFORM = PLATFORM;
	}
	public String getPRODUCER() {
		return PRODUCER;
	}
	public void setPRODUCER(String PRODUCER) {
		this.PRODUCER = PRODUCER;
	}
	public String getPUBLISHER() {
		return PUBLISHER;
	}
	public void setPUBLISHER(String PUBLISHER) {
		this.PUBLISHER = PUBLISHER;
	}
	public String getRELEASED() {
		return RELEASED;
	}
	public void setRELEASED(String RELEASED) {
		this.RELEASED = RELEASED;
	}
	@Override
	public String toString() {
		return "GAME NAME: " + GAME_NAME + " Producer: "+ PRODUCER + " Publisher: " + PUBLISHER + " Game release: "+ RELEASED+ "PLatform: " + PLATFORM +"\n";
	}
}