package com.my.contactbook.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class SlotDTO {
    private long slotId;
    private String slotName;

    private String slotFrom;
    private String slotTo;

}
