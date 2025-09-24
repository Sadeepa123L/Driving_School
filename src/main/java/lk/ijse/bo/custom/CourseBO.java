package lk.ijse.bo.custom;

import lk.ijse.Dto.CourseDto;
import lk.ijse.bo.SuperBO;

public interface CourseBO extends SuperBO {
    String generateProgramId();
    void saveProgram(CourseDto courseDto) throws Exception;
}
