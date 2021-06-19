package application;

public class HistoryOrders /*implements Comparable<Student>*/{
	String Order_name;
	String Order_date;
	int is_delivered;

	public HistoryOrders(String Order_name, String Order_date,	int is_delivered)
	{
		this.Order_name = Order_name;
		this.Order_date=Order_date;
		this.is_delivered = is_delivered;
	}
	
	public String getOrder_name() {
		return Order_name;
	}

	public void setOrder_name(String order_name) {
		Order_name = order_name;
	}

	public String getOrder_date() {
		return Order_date;
	}

	public void setOrder_date(String order_date) {
		Order_date = order_date;
	}

	public int isIs_delivered() {
		return is_delivered;
	}

	public void setIs_delivered(int is_delivered) {
		this.is_delivered = is_delivered;
	}

	@Override
	public String toString() {
		return "Oder name: "+ Order_name + "Order Date: " + Order_date + "is delivered" + is_delivered + "\n";
	}
}