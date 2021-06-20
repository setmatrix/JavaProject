package application;

public class Orders /*implements Comparable<Student>*/{
	String GAME_NAME;
	String PRODUCER;
	String PUBLISHER;
	String RELEASED;
	String SNAMEPLATFORM;
	
	public Orders(String GAME_NAME, String PRODUCER, String PUBLISHER, String RELEASED, String SNAMEPLATFORM)
	{
		this.GAME_NAME = GAME_NAME;
		this.PRODUCER = PRODUCER;
		this.PUBLISHER = PUBLISHER;
		this.RELEASED = RELEASED;
		this.SNAMEPLATFORM = SNAMEPLATFORM;
	}
	public String getGAME_NAME() { return GAME_NAME; }
	public void setGAME_NAME(String gAME_NAME) {
		GAME_NAME = gAME_NAME;
	}
	public String getSNAMEPLATFORM() { return SNAMEPLATFORM; }
	public void setSNAMEPLATFORM(String sNAMEPLATFORM) {
		SNAMEPLATFORM = sNAMEPLATFORM;
	}
	public String getPRODUCER() {
		return PRODUCER;
	}
	public void setPRODUCER(String pRODUCER) {
		PRODUCER = pRODUCER;
	}
	public String getPUBLISHER() {
		return PUBLISHER;
	}
	public void setPUBLISHER(String pUBLISHER) {
		PUBLISHER = pUBLISHER;
	}
	public String getRELEASED() {
		return RELEASED;
	}
	public void setRELEASED(String rELEASED) {
		RELEASED = rELEASED;
	}
	@Override
	public String toString() {
		return "GAME NAME: " + GAME_NAME + " Producer: "+ PRODUCER + " Publisher: " + PUBLISHER + " Game release: "+ RELEASED+ "PLatform: " + SNAMEPLATFORM +"\n";
	}
}