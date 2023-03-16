package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_db", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
@ToString
public class UserEntity extends BaseEntity
{

    @Id
    @Column(name = "user_code")
    @GeneratedValue(generator = "user-code-generator")
    @GenericGenerator(name = "user-code-generator", strategy = "com.my.contactbook.util.CodeGenerator")
    private String userCode;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id", length = 50)
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_code"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "fname", length = 50)
    private String firstName;

    @Column(name = "lname", length = 50)
    private String lastName;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "userId")
    private List<MarkEntity> userMarks;

    @ManyToMany(mappedBy = "teachers")
    private List<SubjectEntity> teacherSubjects;

    @ManyToOne
    @JoinColumn(name = "student_class")
    private ClassEntity studentClass;

    @OneToOne(mappedBy = "formTeacher")
    private ClassEntity formTeacherClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private EStatus status;

    public enum EStatus {
        ENABLE,
        DISABLE
    }
}
