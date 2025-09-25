package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    public enum DAOType{
        USER,STUDENT,COURSE,INSTRUCTOR,LESSON
    }

    public static SuperDAO getDAO(DAOType daoType){
        return switch (daoType) {
            case USER -> new UserDAOImpl();
            case STUDENT -> new StudentDAOImpl();
            case COURSE -> new CourseDAOImpl();
            case INSTRUCTOR -> new InstructorDAOImpl();
            case LESSON -> new LessonDAOImpl();
            default -> null;
        };
    }
}
