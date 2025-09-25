package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.CourseDto;
import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.dao.custom.InstructorDAO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseBOImpl implements CourseBO {

    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getDAO(DAOFactory.DAOType.COURSE);
    private final InstructorDAO instructorDAO = (InstructorDAO) DAOFactory.getDAO(DAOFactory.DAOType.INSTRUCTOR);

    @Override
    public String generateProgramId() {
        return courseDAO.generateNewId();
    }

    @Override
    public boolean saveProgram(CourseDto courseDto) throws Exception {
        Optional<Instructor> instructor = instructorDAO.findById(courseDto.getInstructorId());
        Course course = new Course(
                courseDto.getProgramId(),    // assign ID before save
                courseDto.getProgramName(),
                courseDto.getDuration(),
                courseDto.getFee(),
                instructor.get()
        );
         return courseDAO.save(course);
    }

    @Override
    public List<CourseDto> getAllCourse() throws Exception {
        List<Course> list= courseDAO.getAll();
        List<CourseDto> listDto= new ArrayList<>();
        for(Course course:list){
            CourseDto courseDto = new CourseDto(
                   course.getProgramId(),
                    course.getProgramName(),
                    course.getDuration(),
                    course.getFee(),
                    course.getInstructor().getInstructorId()
            );
            listDto.add(courseDto);
        }
        return listDto;
    }

    @Override
    public boolean updateProgram(CourseDto courseDto) throws Exception {
        Optional<Instructor> instructor = instructorDAO.findById(courseDto.getInstructorId());
        return courseDAO.update(new Course(
               courseDto.getProgramId(),
                courseDto.getProgramName(),
                courseDto.getDuration(),
                courseDto.getFee(),
                instructor.get()
        ));
    }

    @Override
    public boolean deleteProgram(String id) throws Exception {
        Optional<Course> course = courseDAO.findById(id);
        if (course.isEmpty()) {
            throw new Exception("Instructor not Found");
        }
        return courseDAO.delete(id);
    }

    @Override
    public List<String> getAllInstructorIds() throws Exception {
        return instructorDAO.getAllIds();
    }

    @Override
    public List<String> getAllCourseIds() throws Exception {
        return courseDAO.getAllIds();
    }

}
