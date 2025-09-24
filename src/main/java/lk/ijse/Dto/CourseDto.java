package lk.ijse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDto {
    private String programId;
    private String programName;
    private int duration;
    private double fee;
//    private List<Payment> payments;
}
