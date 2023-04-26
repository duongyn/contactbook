package com.my.contactbook.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Getter
@Setter
@ToString
public class UserDTO {

    private String userCode;

    private String status;

    @NotBlank(message = "Please enter First Name")
    @Length(min = 1, max = 50, message = "The name length should be 1 - 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name should not contain numbers and special characters")
    private String firstName;

    @NotBlank(message = "Please enter Last Name")
    @Length(min = 1, max = 50, message = "The name length should be 1 - 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name  should not contain numbers and special characters")
    private String lastName;

    private String username;

    @NotBlank(message = "Date of birth can not be empty")
    private String dob;

    private String joinedDate;

    private String gender;

    private List<String> roleName;

    private String address;

    private String studentClass;

    private String phoneNumber;

}
