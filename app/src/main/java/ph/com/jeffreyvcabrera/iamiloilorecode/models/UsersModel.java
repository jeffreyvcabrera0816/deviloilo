package ph.com.jeffreyvcabrera.iamiloilorecode.models;

/**
 * Created by Jeffrey on 3/14/2017.
 */

public class UsersModel {
    Boolean Logged;
    Integer id, login_type;
    String firstname, lastname, email, birthdate, mobile, password;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setLogin_type(Integer login_type) {
        this.id = login_type;
    }

    public Integer getLogin_type() {
        return login_type;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLogged(Boolean logged) {
        this.Logged = logged;
    }

    public Boolean getLogged() {
        return Logged;
    }
}
