package org.richard.data.domain;

/**
 * Created by rnkoaa on 6/4/15.
 */

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_name", nullable = true)
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }


    protected User() {
    }

   /* public Set<Role> getRoles() {
        return roles;
    }*/

    @Override
    public String toString() {
        return "Customer{" + "username='" + username + '\'' + ", lastName='" + lastName + '\'' + ", firstName='" + firstName + '\'' + ", id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!firstName.equals(user.firstName)) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (!lastName.equals(user.lastName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public Long getId() {

        return id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

   /* public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }*/
}
