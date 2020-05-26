package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


enum Type {
    Spectator,
    Manager
}
@Entity
@Table(name = "User")
public class User extends HasID<String> implements Serializable {
   // @Id
   // @Column(name = "username")
    private String username;
   // @Column(name = "password")
    private String password;
    //@Column(name = "type")
    private Type type;
    //@Column(name = "nume")
    private String nume;
   // @Column(name = "prenume")
    private String prenume;
    //@Column(name = "email")
    private String email;
    public User() {
    }

    public User(String username, String password, String type, String nume, String prenume, String email) {
        super.setId(username);
        this.username = username;
        this.password = password;
        this.type = Type.valueOf(type);
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public User(String username, String password) {
        super.setId(username);
        this.username = username;
        this.password = password;
    }

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "type")
    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

    @Column(name = "nume")
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Column(name = "prenume")
    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


