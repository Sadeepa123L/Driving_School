package lk.ijse.bo.custom;

import lk.ijse.Dto.CourseDto;
import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.SuperBO;

import java.util.List;

public interface CourseBO extends SuperBO {
    String generateProgramId();
    boolean saveProgram(CourseDto courseDto) throws Exception;
    List<CourseDto> getAllCourse() throws Exception;
      boolean updateProgram(CourseDto courseDto) throws Exception;
    boolean deleteProgram(String id) throws Exception;
    List<String> getAllInstructorIds() throws Exception;
    List<String> getAllCourseIds() throws Exception;
}
