package com.my.contactbook.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "slot_db")
@Getter
@Setter
@ToString
public class SlotEntity {

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

    @OneToMany(mappedBy = "lessonSlot")
    private List<LessonEntity> lessons;

}
