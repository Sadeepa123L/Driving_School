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
        return false;
    }

    @Override
    public boolean update(Lesson lesson) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public List<String> getAllIds() throws Exception {
        return List.of();
    }

    @Override
    public Optional<Lesson> findById(String id) throws Exception {
        return Optional.empty();
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
