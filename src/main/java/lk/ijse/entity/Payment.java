package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment_table")
public class Payment {
    @Id
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private String status; // COMPLETED, PENDING, FAILEDD



    @ManyToOne
    @JoinColumn(name = "programId")
    private Course program;
}
