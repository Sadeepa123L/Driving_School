package lk.ijse.bo.custom;

import lk.ijse.Dto.InstructorDto;
import lk.ijse.Dto.StudentDto;
import lk.ijse.bo.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface StudentBO extends SuperBO {
    String generateStudentId();
    boolean saveStudent(StudentDto studentDto) throws Exception;
    boolean updateStudent(StudentDto studentDto) throws Exception;
    List<StudentDto> getAllStudent() throws Exception;
    boolean deleteStudent(String id) throws Exception;
    List<String> getAllStudentIds() throws Exception;
}
