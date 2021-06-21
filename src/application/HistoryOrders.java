package application;

public class HistoryOrders /*implements Comparable<Student>*/{
	private int orderId;
	private String orderName;
	private String orderDate;
	private int isDelivered;

	public HistoryOrders(int orderId,String orderName, String orderDate,int isDelivered)
	{
		this.orderId = orderId;
		this.orderName = orderName;
		this.orderDate=orderDate;
		this.isDelivered = isDelivered;
	}
	public int isOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int isIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(int isDelivered) {
		this.isDelivered = isDelivered;
	}

	@Override
	public String toString() {
		return "Order ID: " + orderId + "Oder name: "+ orderName + "Order Date: " + orderDate + "is delivered" + isDelivered + "\n";
	}
}