package org.example.model;

import java.util.Objects;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Integer getId() {
        return id;
    }

    public Courier setId(Integer id) {
        this.id = id;
        return this;
    }

    private Integer id;

    public String getLogin() {
        return login;
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Courier setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return Objects.equals(login, courier.login) && Objects.equals(password, courier.password) && Objects.equals(firstName, courier.firstName) && Objects.equals(id, courier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstName, id);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                '}';
    }
}
