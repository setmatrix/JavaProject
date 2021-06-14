package application;

public class Student /*implements Comparable<Student>*/{
	int id;
	String login;
	String email;
	String type;
	public Student(int id,String login, String email, String type)
	{
		this.id = id;
		this.login=login;
		this.email=email;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id= id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	/*@Override
	public int compareTo(Student o) {
		int result = this.rocznik - o.rocznik;
		if(result == 0)
		{
			result = this.srednia - o.srednia;
			if(result == 0)
			{
				return this.nazwisko.compareTo(o.nazwisko);
			}
			return result;
		}
		return result;
	}*/
	@Override
	public String toString() {
		return "Id: " + id + ",Login: " + login + ",Email=" + email + ", Type: " + type +"\n";
	}
}