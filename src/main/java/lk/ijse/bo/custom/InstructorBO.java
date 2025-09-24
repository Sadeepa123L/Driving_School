package lk.ijse.bo.custom;

import lk.ijse.Dto.CourseDto;
import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface InstructorBO extends SuperBO {
    String generateInstructorId();
    boolean saveInstructor(InstructorDto instructorDto) throws Exception;
    boolean updateInstructor(InstructorDto instructorDto) throws Exception;
    List<InstructorDto> getAllInstructor() throws Exception;
    boolean deleteInstructor(String id) throws Exception;
    List<String> getAllInstructorIds() throws Exception;
}
