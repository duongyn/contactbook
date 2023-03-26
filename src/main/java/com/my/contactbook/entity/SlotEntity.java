package com.my.contactbook.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "slot_db")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SlotEntity extends BaseEntity{

    public SlotEntity(String slotName, LocalTime fromTime, LocalTime toTime){
        this.slotName = slotName;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Id
    @Column(name = "slot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long slotId;

    @Column(name = "slot_name")
    private String slotName;

    @Column(name = "from_time", columnDefinition = "TIME")
    private LocalTime fromTime;

    @Column(name = "to_time", columnDefinition = "TIME")
    private LocalTime toTime;

    @OneToMany(mappedBy = "scheduleSlot")
    private List<ScheduleEntity> schedules;

}
