package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public List<Course> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Course> courses = session.createQuery("from Course").list();
        session.close();
        return courses;
    }

    @Override
    public String getLastId() throws Exception {
        return "";
    }

    @Override
    public boolean save(Course course) throws Exception {
        course.setProgramId(generateNewId());
        if (course.getProgramId() == null || course.getProgramId().isEmpty()) {
            throw new RuntimeException("Program ID is required!");
        }

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(course);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Course course) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(course);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Course course = (Course) session.get(Course.class, id);
            if (course != null) {
                session.remove(course);
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
            Query<String> query = session.createQuery("SELECT i.programId FROM Course i", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Course> findById(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Course course = session.get(Course.class, id);
            return Optional.ofNullable(course);
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNewId() {
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
}
