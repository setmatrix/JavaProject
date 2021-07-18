package application;

public class User{
    int id;
    String login;
    String email;
    String type;
    public User(int id,String login, String email, String type)
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
    @Override
    public String toString() {
        return "Id: " + id + ",Login: " + login + ",Email=" + email + ", Type: " + type +"\n";
    }
}