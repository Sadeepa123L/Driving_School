package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.CourseDto;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.entity.Course;

public class CourseBOImpl implements CourseBO {

    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public String generateProgramId() {
        return courseDAO.generateProgramId();
    }

    @Override
    public void saveProgram(CourseDto courseDto) throws Exception {
        Course course = new Course(
                courseDto.getProgramId(),    // assign ID before save
                courseDto.getProgramName(),
                courseDto.getDuration(),
                courseDto.getFee()
        );
        courseDAO.saveProgram(course);
    }
}
