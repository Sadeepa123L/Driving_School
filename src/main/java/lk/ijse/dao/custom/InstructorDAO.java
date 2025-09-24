package lk.ijse.dao.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Instructor;

public interface InstructorDAO extends SuperDAO {
    String generateInstructorId();
    void saveInstructor(Instructor instructor) throws Exception;
}
