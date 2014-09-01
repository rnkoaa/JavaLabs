package com.richard;

/**
 * Created by rnkoaa on 8/31/14.
 */
public class User {
    private final String id;
    private final String userName;
    private final Person person;
    private final String password;

    public User(String id, String userName, String password, Person person) {
        this.id = id;
        this.userName = userName;
        this.person = person;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Person getPerson() {
        return person;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!person.equals(user.person)) return false;
        if (!userName.equals(user.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + person.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", person=" + person +
                '}';
    }
}
