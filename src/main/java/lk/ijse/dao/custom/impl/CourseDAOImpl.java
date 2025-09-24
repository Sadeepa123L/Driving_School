package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.entity.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public String generateProgramId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String lastId = (String) session.createQuery("SELECT c.programId FROM Course c ORDER BY c.programId DESC")
                .setMaxResults(1)
                .uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            int newId = Integer.parseInt(lastId.replace("P", "")) + 1;
            return String.format("P%03d", newId);
        } else {
            return "P001";
        }
    }

    @Override
    public void saveProgram(Course course) {
        course.setProgramId(generateProgramId());
//        System.out.println(course);
        if(course.getProgramId() == null || course.getProgramId().isEmpty()){
            throw new RuntimeException("Program ID is required!");
        }

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(course);

        transaction.commit();
        session.close();
    }
}
