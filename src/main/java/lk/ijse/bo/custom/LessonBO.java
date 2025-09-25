package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.util.List;

public interface LessonBO extends SuperBO {
    String generateLessonId();
    List<String> getAllInstructorIds() throws Exception;
    List<String> getAllStudentIds() throws Exception;
    List<String> getAllCourseIds() throws Exception;
}
