package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CheckPasswordDTO {

    private String username;

    private String oldPassword;

    private String newPassword;

}
