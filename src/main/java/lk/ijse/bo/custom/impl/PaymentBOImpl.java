package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.LessonDto;
import lk.ijse.Dto.PaymentDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CourseDAO;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO =(PaymentDAO) DAOFactory.getDAO(DAOFactory.DAOType.PAYMENT);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getDAO(DAOFactory.DAOType.COURSE);

    @Override
    public List<String> getAllStudentIds() throws Exception {
        return studentDAO.getAllIds();
    }

    @Override
    public List<String> getAllCourseIds() throws Exception {
        return courseDAO.getAllIds();
    }

    @Override
    public boolean saveLesson(PaymentDto paymentDto) throws Exception {
        Optional<Student> student = studentDAO.findById(paymentDto.getStudentId());
        Optional<Course> course = courseDAO.findById(paymentDto.getProgramId());
        Payment payment = new Payment(
                         paymentDto.getPaymentId(),
                         student.get(),
                         paymentDto.getAmount(),
                         paymentDto.getPaymentDate(),
                         paymentDto.getStatus(),
                         course.get()
                );
        return paymentDAO.savePayment(payment);
    }

    @Override
    public String generateNewId() throws Exception {
        return paymentDAO.generateNewId();
    }

    @Override
    public List<PaymentDto> getAllPayments() throws Exception {
        List<Payment> list = paymentDAO.getAll();
        List<PaymentDto> listDto = new ArrayList<>();
        for(Payment payment : list) {
            PaymentDto paymentDto = new PaymentDto(
                   payment.getPaymentId(),
                    payment.getStudent().getStudentId(),
                    payment.getProgram().getProgramId(),
                    payment.getAmount(),
                    payment.getPaymentDate(),
                    payment.getStatus()
            );
            listDto.add(paymentDto);
        }
        return listDto;
    }
}
