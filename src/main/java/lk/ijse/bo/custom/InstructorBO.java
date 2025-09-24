package lk.ijse.bo.custom;

import lk.ijse.Dto.CourseDto;
import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.SuperBO;

public interface InstructorBO extends SuperBO {
    String generateInstructorId();
    void saveInstructor(InstructorDto instructorDto) throws Exception;
}
