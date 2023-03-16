package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role_db", uniqueConstraints = {@UniqueConstraint(columnNames = "role_name")})
@Getter
@Setter
@ToString
public class RoleEntity extends BaseEntity{
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "role_name", length = 50)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    @Column(name = "role_prefix")
    private String rolePrefix;

}
