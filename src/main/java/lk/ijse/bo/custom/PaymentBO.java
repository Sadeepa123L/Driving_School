package lk.ijse.bo.custom;

import lk.ijse.Dto.LessonDto;
import lk.ijse.Dto.PaymentDto;
import lk.ijse.bo.SuperBO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    List<String> getAllStudentIds() throws Exception;
    List<String> getAllCourseIds() throws Exception;
    boolean saveLesson(PaymentDto paymentDto) throws Exception;
    String generateNewId() throws Exception;
    List<PaymentDto> getAllPayments() throws Exception;
}
