package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.Course;

public interface CourseDAO extends SuperDAO {
    String generateProgramId();
    void saveProgram(Course course) throws Exception;
}
