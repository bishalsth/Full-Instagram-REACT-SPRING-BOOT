package com.insta.instagram.modal;

import com.insta.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="posts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String caption;
    private String image;
    private String location;
    private LocalDate createdAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "email",column = @Column(name = "user_email"))
    })
    private UserDto user;

    @OneToMany
    private List<Comment> comments = new ArrayList<Comment>();

    @Embedded
    @ElementCollection
    @JoinTable(name = "likedByUsers",joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserDto> likedByUsers = new HashSet<>();

}
