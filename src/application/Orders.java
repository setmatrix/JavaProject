package application;

public class Orders /*implements Comparable<Student>*/{
	String GAME_NAME;
	String PRODUCER;
	String PUBLISHER;
	String RELEASED;
	
	public Orders(String GAME_NAME, String PRODUCER, String PUBLISHER, String RELEASED)
	{
		this.GAME_NAME = GAME_NAME;
		this.PRODUCER = PRODUCER;
		this.PUBLISHER = PUBLISHER;
		this.RELEASED = RELEASED;
	}
	
	

	public String getGAME_NAME() {
		return GAME_NAME;
	}



	public void setGAME_NAME(String gAME_NAME) {
		GAME_NAME = gAME_NAME;
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
		return "GAME NAME: " + GAME_NAME + " Producer: "+ PRODUCER + " Publisher: " + PUBLISHER + " Game release: "+ RELEASED+ "\n";
	}
}