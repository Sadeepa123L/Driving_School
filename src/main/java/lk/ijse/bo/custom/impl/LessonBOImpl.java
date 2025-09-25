package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.LessonDto;
import lk.ijse.bo.custom.LessonBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.dao.custom.InstructorDAO;
import lk.ijse.dao.custom.LessonDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dao.custom.impl.LessonDAOImpl;
import lk.ijse.entity.Course;
import lk.ijse.entity.Instructor;
import lk.ijse.entity.Lesson;
import lk.ijse.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean saveLesson(LessonDto lessondto) throws Exception {
        Optional<Instructor> instructor = instructorDAO.findById(lessondto.getInstructorId());
        Optional<Student> student = studentDAO.findById(lessondto.getStudentId());
        Optional<Course> course = courseDAO.findById(lessondto.getCourseId());
        Lesson lesson = new Lesson(
               lessondto.getLessonId(),
                student.get(),
                course.get(),
                instructor.get(),
                lessondto.getLessonDate(),
                lessondto.getDuration()
        );
        return lessonDAO.save(lesson);
    }

    @Override
    public boolean updateLesson(LessonDto lessonDto) throws Exception {
        Optional<Instructor> instructor = instructorDAO.findById(lessonDto.getInstructorId());
        Optional<Student> student = studentDAO.findById(lessonDto.getStudentId());
        Optional<Course> course = courseDAO.findById(lessonDto.getCourseId());

        Lesson lesson = new Lesson(
                lessonDto.getLessonId(),
                student.get(),
                course.get(),
                instructor.get(),
                lessonDto.getLessonDate(),
                lessonDto.getDuration()
        );
        return lessonDAO.update(lesson);
    }

    @Override
    public boolean deleteLesson(String lessonId) throws Exception {

        Optional<Lesson> lesson = lessonDAO.findById(lessonId);
        if (lesson.isEmpty()) {
            throw new Exception("Lesson not Found");
        }
        return lessonDAO.delete(lessonId);
    }

    @Override
    public List<LessonDto> getAllLessons() throws Exception {
        List<Lesson> list = lessonDAO.getAll();
        List<LessonDto> listDto = new ArrayList<>();
        for(Lesson lesson : list) {
            LessonDto lessonDto = new LessonDto(
                    lesson.getLessonId(),
                    lesson.getStudent().getStudentId(),
                    lesson.getCourse().getProgramId(),
                    lesson.getInstructor().getInstructorId(),
                    lesson.getLessonDate(),
                    lesson.getDuration()
            );
            listDto.add(lessonDto);
        }
        return listDto;
    }
}
