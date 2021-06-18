package application;

public class Student /*implements Comparable<Student>*/{
	int id;
	String firstname;
	String lastname;
	String login;
	String email;
	String type;

	public Student(int id,String firstname, String lastname, String login, String email, String type)
	{
		this.id = id;
		this.firstname=firstname;
		this.lastname=lastname;
		this.login = login;
		this.email = email;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id= id;
	}
	public String getfirstname() {
		return firstname;
	}
	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getlastname() {
		return lastname;
	}
	public void setlastname(String lastname) {
		this.lastname = lastname;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FirstName: " + firstname + ", LastName: " + lastname + ", Type: " + type +"\n";
	}
	public String getEmail() {
		return email;
	//test
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
	}
	public void setEmail(String email) {
		this.email = email;
	}
}