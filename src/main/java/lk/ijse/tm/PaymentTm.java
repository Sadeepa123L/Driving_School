package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTm {
    private String paymentId;
    private String studentId;
    private String programId;
    private double amount;
    private String paymentDate;
    private String status;
}
