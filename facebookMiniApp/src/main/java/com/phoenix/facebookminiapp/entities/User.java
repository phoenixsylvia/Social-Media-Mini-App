package com.phoenix.facebookminiapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(
            name = "users",
            uniqueConstraints = {
                    @UniqueConstraint(
                            name = "user_username_unique",
                            columnNames = "username"
                    )
            }
    )
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name", nullable = false)
        private String firstName;

        @Column(name = "last_name", nullable = false)
        private String lastName;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "password", nullable = false)
        private String password;

        @OneToMany(
                mappedBy = "users",
                orphanRemoval = true,
                cascade = CascadeType.ALL
        )
        private final List<Post> posts = new ArrayList<>();

        public User(String firstName, String lastName, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", posts=" + posts +
                    '}';
        }
}
