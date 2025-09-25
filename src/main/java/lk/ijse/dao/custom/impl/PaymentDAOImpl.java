package lk.ijse.dao.custom.impl;

import lk.ijse.configuration.FactoryConfiguration;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Lesson;
import lk.ijse.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public String generateNewId() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String lastId = (String) session.createQuery("SELECT c.paymentId FROM Payment c ORDER BY c.paymentId DESC")
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
    public List<Payment> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Payment> payments = session.createQuery("from Payment ").list();
        session.close();
        return payments;
    }

    @Override
    public boolean savePayment(Payment payment) throws Exception {
        payment.setPaymentId(generateNewId());
        if (payment.getPaymentId() == null || payment.getPaymentId() == null) {
            throw new RuntimeException("Payment ID is required!");
        }

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(payment);

        transaction.commit();
        session.close();
        return true;
    }
}
