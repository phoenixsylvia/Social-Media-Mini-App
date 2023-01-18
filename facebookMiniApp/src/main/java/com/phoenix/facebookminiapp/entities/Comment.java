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
@Table(name = "comments")
public class Comment implements Comparable<Comment> {

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
                    name = "comment_user_id_fk"
            )
    )
    private User users;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "comment_post_id_fk"
            )
    )
    private Post posts;

    @OneToMany(
            mappedBy = "comments",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private final List<Like> likes = new ArrayList<>();

    @Override
    public int compareTo(Comment o) {
        return id.compareTo(o.getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", users=" + users.getId() +
                ", posts=" + posts.getId() +
                '}';
    }
}