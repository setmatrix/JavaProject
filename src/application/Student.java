package application;

public class Student /*implements Comparable<Student>*/{

	String login,haslo;

	public Student(String login, String haslo)
	{
		this.login=login;
		this.haslo=haslo;
	}

	
	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getHaslo() {
		return haslo;
	}


	public void setHaslo(String haslo) {
		this.haslo = haslo;
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
		return "login=" + login + ", haslo=" + haslo + "\n";
	}
	
}
