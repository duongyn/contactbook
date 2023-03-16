package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class UserEditDTO {

    private String userCode;

    private String editField;

    private String editValue;

    private String userFName;

    private String userLName;

    private String userAddress;

    private String gender;

    private String dob;
}
