package lk.ijse.bo.custom;

import lk.ijse.Dto.LessonDto;
import lk.ijse.bo.SuperBO;

import java.util.List;

public interface LessonBO extends SuperBO {
    String generateLessonId();
    List<String> getAllInstructorIds() throws Exception;
    List<String> getAllStudentIds() throws Exception;
    List<String> getAllCourseIds() throws Exception;
    boolean saveLesson(LessonDto lessonDTO) throws Exception;
    boolean updateLesson(LessonDto lessonDTO) throws Exception;
    boolean deleteLesson(String lessonId) throws Exception;
    List<LessonDto> getAllLessons() throws Exception;
}
