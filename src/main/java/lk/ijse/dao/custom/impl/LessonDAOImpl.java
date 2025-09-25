package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.LessonDAO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class LessonDAOImpl implements LessonDAO {
    @Override
    public List<Lesson> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Lesson> lessons = session.createQuery("from Lesson ").list();
        session.close();
        return lessons;
    }

    @Override
    public String getLastId() throws Exception {
        return null;
    }

    @Override
    public boolean save(Lesson lesson) throws Exception {
        lesson.setLessonId(generateNewId());
        if (lesson.getLessonId() == null || lesson.getLessonDate() == null) {
            throw new RuntimeException("Program ID is required!");
        }

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(lesson);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Lesson lesson) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(lesson);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Lesson lesson = (Lesson) session.get(Lesson.class, id);
            if (lesson != null) {
                session.remove(lesson);
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
        return List.of();
    }

    @Override
    public Optional<Lesson> findById(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Lesson lesson = session.get(Lesson.class, id);
            return Optional.ofNullable(lesson);
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNewId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String lastId = (String) session.createQuery("SELECT c.lessonId FROM Lesson c ORDER BY c.lessonId DESC")
                .setMaxResults(1)
                .uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            int newId = Integer.parseInt(lastId.replace("L", "")) + 1;
            return String.format("L%03d", newId);
        } else {
            return "L001";
        }
    }
}
