package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LessonBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.dao.custom.InstructorDAO;
import lk.ijse.dao.custom.LessonDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dao.custom.impl.LessonDAOImpl;

import java.util.List;

public class LessonBOImpl implements LessonBO {

   private final LessonDAO lessonDAO = (LessonDAO) DAOFactory.getDAO(DAOFactory.DAOType.LESSON);
   private final InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getDAO(DAOFactory.DAOType.INSTRUCTOR);
   private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);
   private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public String generateLessonId() {
        return lessonDAO.generateNewId();
    }

    @Override
    public List<String> getAllInstructorIds() throws Exception {
        return instructorDAO.getAllIds();
    }

    @Override
    public List<String> getAllStudentIds() throws Exception {
        return studentDAO.getAllIds();
    }

    @Override
    public List<String> getAllCourseIds() throws Exception {
        return courseDAO.getAllIds();
    }
}
