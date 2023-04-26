package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post_db")
@Getter
@Setter
@ToString
public class PostEntity extends BaseEntity{

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @Column(name = "post_name", length = 100)
    private String postName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "post_img_url")
    private String imgUrl;

}
