package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.Payment;

import java.util.List;

public interface PaymentDAO extends SuperDAO {
    String generateNewId() throws Exception;
    List<Payment> getAll() throws Exception;
    boolean savePayment(Payment payment) throws Exception;
    boolean updatePayment(Payment payment) throws Exception;
}
