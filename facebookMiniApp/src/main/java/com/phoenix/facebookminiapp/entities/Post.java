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
@Table(name = "posts")
public class Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "post_user_id_fk"
            )
    )
    private User users;

    @OneToMany(
            mappedBy = "posts",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "posts",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private final List<Like> likes = new ArrayList<>();

    public Post(String body, User users) {
        this.body = body;
        this.users = users;
    }

    @Override
    public int compareTo(Post o) {
        return (int) (id - o.getId());
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", users=" + users.getId() +
                ", comments=" + comments +
                '}';
    }
}