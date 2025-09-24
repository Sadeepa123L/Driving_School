package lk.ijse.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private String paymentId;
    private String studentId;
    private String programId;
    private double amount;
    private String paymentDate;
    private String status;
}
