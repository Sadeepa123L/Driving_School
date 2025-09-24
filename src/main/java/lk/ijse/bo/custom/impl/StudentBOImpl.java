package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.StudentDto;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dao.custom.impl.StudentDAOImpl;
import lk.ijse.entity.Instructor;
import lk.ijse.entity.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO =(StudentDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public String generateStudentId() {
        return "";
    }

    @Override
    public boolean saveStudent(StudentDto studentDto) throws Exception {
        Student student = new Student(
                studentDto.getStudentId(),
                studentDto.getName(),
                studentDto.getAddress(),
                studentDto.getContact(),
                studentDto.getRegDate()
        );
        return studentDAO.save(student);
    }

    @Override
    public boolean updateStudent(StudentDto studentDto) throws Exception {
        return false;
    }

    @Override
    public List<StudentDto> getAllStudent() throws Exception {
        return List.of();
    }

    @Override
    public boolean deleteStudent(String id) throws Exception {
        Optional<Student> students = studentDAO.findById(id);
        if (students.isEmpty()) {
            throw new Exception("Instructor not Found");
        }
        return studentDAO.delete(id);
    }

    @Override
    public List<String> getAllStudentIds() throws Exception {
        return List.of();
    }
}
