package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.InstructorDto;
import lk.ijse.bo.custom.InstructorBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.InstructorDAO;
import lk.ijse.entity.Instructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstructorBOImpl implements InstructorBO {

InstructorDAO instructorDAO= (InstructorDAO) DAOFactory.getDAO(DAOFactory.DAOType.INSTRUCTOR);

    @Override
    public String generateInstructorId() {
        return instructorDAO.generateNewId();
    }

    @Override
    public boolean saveInstructor(InstructorDto instructorDto) throws Exception {
        Instructor instructor = new Instructor(
                instructorDto.getInstructorId(),
                instructorDto.getName(),
                instructorDto.getSpecialization(),
                instructorDto.getAvailability()
        );
       return instructorDAO.save(instructor);
    }

    @Override
    public boolean updateInstructor(InstructorDto instructorDto) throws Exception {
        return instructorDAO.update(new Instructor(
                instructorDto.getInstructorId(),
                instructorDto.getName(),
                instructorDto.getSpecialization(),
                instructorDto.getAvailability()
        ));
    }

    @Override
    public List<InstructorDto> getAllInstructor() throws Exception {
        List<Instructor> list= instructorDAO.getAll();
        List<InstructorDto> listDto= new ArrayList<>();
        for(Instructor instructor:list){
            InstructorDto instructorDto = new InstructorDto(
                    instructor.getInstructorId(),
                    instructor.getName(),
                    instructor.getSpecialization(),
                    instructor.getAvailability()
            );
            listDto.add(instructorDto);
        }
        return listDto;
    }

    @Override
    public boolean deleteInstructor(String id) throws Exception {
        Optional<Instructor> instructors = instructorDAO.findById(id);
        if (instructors.isEmpty()) {
            throw new Exception("Instructor not Found");
        }
        return instructorDAO.delete(id);
    }

    @Override
    public List<String> getAllInstructorIds() throws Exception {
       return instructorDAO.getAllIds();
    }

}
