package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Component
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 35, message = "Name should contain up to 35 characters")
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 122, message = "Age should be less than 122")
    private int age;

    @Column(name = "country")
    @NotEmpty(message = "Country should not be empty")
    private String country;

    @Column(name = "username")
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 1, max = 10, message = "Username should be in the range from 1 to 10 characters")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    public User() {
    }

    @ManyToMany
    @JoinTable (name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set <Role> roles;


    public User(String name, int age, String country, String username, String password, Set<Role> roles) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Set<? extends SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities =
                getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                        .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}