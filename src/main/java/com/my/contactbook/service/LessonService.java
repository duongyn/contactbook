package com.my.contactbook.service;

import com.my.contactbook.mapper.LessonMapper;
import com.my.contactbook.repository.SlotRepository;
import com.my.contactbook.repository.SubjectRepository;
import com.my.contactbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonService {
    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

//    @Autowired
//    private LessonRepository lessonRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private LessonMapper lessonMapper;

//    public LessonDTO createLesson(LessonDTO dto) {
//        LessonEntity lesson = lessonMapper.convertToEntity(dto);
//        UserEntity teacher = userRepository.findById(dto.getLessonTeacherCode())
//                .orElseThrow(() -> new RuntimeException("Not found teacher with code: " + dto.getLessonTeacherCode()));
//        lesson.setLessonTeacher(teacher);
//        SlotEntity slot = slotRepository.findById(dto.getLessonSlotId())
//                .orElseThrow(() -> new RuntimeException("Not found slot with id: " + dto.getLessonSlotId()));
//        lesson.setLessonSlot(slot);
//        SubjectEntity subject = subjectRepository.findBySubjectName(dto.getLessonSubjectName())
//                .orElseThrow(() -> new RuntimeException("Not found subject with name: " + dto.getLessonSubjectName()));
//        lesson.setLessonSubject(subject);
//        return lessonMapper.convertToDto(lessonRepository.save(lesson));
//    }
//
//    public List<LessonDTO> findBySubjectId(long subjectId) {
//        SubjectEntity subject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new RuntimeException("Not found subject with id: " + subjectId));
//        List<LessonEntity> entities = lessonRepository.findByLessonSubject(subject);
//        List<LessonDTO> dtos = new ArrayList<>();
//        if (entities.size() > 0) {
//            dtos = lessonMapper.toListDto(entities);
//        }
//        return dtos;
//    }
}
