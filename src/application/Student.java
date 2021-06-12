package application;

public class Student /*implements Comparable<Student>*/{

	int id;
	String login,e_mail;

	public Student(int Id,String login, String e_mail)
	{
		this.id = id;
		this.login=login;
		this.e_mail=e_mail;
	}

	public int getId() {
		return id;
	}


	public void setId(String login) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getE_mail() {
		return e_mail;
	}


	public void setE_mail(String haslo) {
		this.e_mail = e_mail;
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
		return "Id: " + id + ",Login: " + login + ",E_mail=" + e_mail + "\n";
	}
	
}
