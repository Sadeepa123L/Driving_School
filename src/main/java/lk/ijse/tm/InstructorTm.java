package lk.ijse.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstructorTm {
    private String instructorId;
    private String name;
    private String specialization;
    private String availability;
}
