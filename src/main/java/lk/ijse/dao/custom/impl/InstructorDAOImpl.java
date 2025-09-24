package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.InstructorDAO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {

    @Override
    public List<Instructor> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Instructor> instructors = session.createQuery("from Instructor").list();
        session.close();
        return instructors;
    }

    @Override
    public String getLastId() throws Exception {
        return "";
    }

    @Override
    public boolean save(Instructor instructor) throws Exception {
//        instructor.setInstructorId(generateNewId());
//        if(instructor.getInstructorId() == null || instructor.getInstructorId().isEmpty()){
//            throw new RuntimeException("Instructor ID is required!");
//        }
//
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        session.persist(instructor);
//
//        transaction.commit();
//        session.close();
//        return true;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(instructor);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Instructor instructor) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(instructor);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = (Instructor) session.get(Instructor.class, id);
            if (instructor != null) {
                session.remove(instructor);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.instructorId FROM Instructor i", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Instructor> findById(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
        } finally {
            session.close();
        }
    }

    /*@Override
    public String generateNewId() {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            String lastId = (String) session.createQuery("SELECT c.instructorId FROM Instructor c ORDER BY c.instructorId DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            transaction.commit();
            session.close();

            if (lastId != null) {
                int newId = Integer.parseInt(lastId.replace("I", "")) + 1;
                return String.format("I%03d", newId);
            } else {
                return "I001";
            }
    }*/
    @Override
    public String generateNewId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // get last instructor id
        String lastId = (String) session.createQuery(
                        "SELECT i.instructorId FROM Instructor i ORDER BY i.instructorId DESC"
                )
                .setMaxResults(1)
                .uniqueResult();

        transaction.commit();
        session.close();

        // check if lastId exists and is valid
        if (lastId != null && lastId.startsWith("I")) {
            try {
                int newId = Integer.parseInt(lastId.replace("I", "")) + 1;
                return String.format("I%03d", newId);
            } catch (NumberFormatException e) {
                // if somehow wrong id exists in DB (like P002), fallback
                return "I001";
            }
        } else {
            // no record yet
            return "I001";
        }
    }

}
