package vk.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private Date date;

    private String activateCode;
    private String email;
    private String password;



    private Date lastOnline;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String username,
                String firstName,
                String lastName,
                Date date,
                String activationCode,
                String email,
                String password,
                Set<Role> roles,
                Date lastOnline) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.activateCode = activationCode;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.lastOnline = lastOnline;
    }
    public Date getLastOnline() {return lastOnline;}

    public void setLastOnline(Date lastOnline) {this.lastOnline = lastOnline;}
    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}


    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {return roles;}

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
