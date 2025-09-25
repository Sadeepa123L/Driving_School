package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Instructor;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {


    @Override
    public List<Student> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Student> students = session.createQuery("from Student").list();
        session.close();
        return students;
    }

    @Override
    public String getLastId() throws Exception {
        return "";
    }

    @Override
    public boolean save(Student student) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(student);
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
    public boolean update(Student student) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.merge(student);
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Student student = (Student) session.get(Student.class, id);
            if (student != null) {
                session.remove(student);
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
            Query<String> query = session.createQuery("SELECT i.studentId FROM Student i", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Student> findById(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNewId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String lastId = (String) session.createQuery(
                        "SELECT i.studentId FROM Student i ORDER BY i.studentId DESC"
                )
                .setMaxResults(1)
                .uniqueResult();

        transaction.commit();
        session.close();

        // check if lastId exists and is valid
        if (lastId != null && lastId.startsWith("S")) {
            try {
                int newId = Integer.parseInt(lastId.replace("S", "")) + 1;
                return String.format("S%03d", newId);
            } catch (NumberFormatException e) {
                // if somehow wrong id exists in DB (like P002), fallback
                return "S001";
            }
        } else {
            // no record yet
            return "S001";
        }
    }
}
