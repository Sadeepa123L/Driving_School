package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.InstructorDAO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InstructorDAOImpl implements InstructorDAO {
    @Override
    public String generateInstructorId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String lastId = (String) session.createQuery("SELECT c.instructorId FROM Instructor c ORDER BY c.instructorId DESC")
                .setMaxResults(1)
                .uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            int newId = Integer.parseInt(lastId.replace("I", "")) + 1;
            return String.format("P%03d", newId);
        } else {
            return "I001";
        }
    }

    @Override
    public void saveInstructor(Instructor instructor) throws Exception {
        instructor.setInstructorId(generateInstructorId());
        if(instructor.getInstructorId() == null || instructor.getInstructorId().isEmpty()){
            throw new RuntimeException("Program ID is required!");
        }

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(instructor);

        transaction.commit();
        session.close();
    }
    }
