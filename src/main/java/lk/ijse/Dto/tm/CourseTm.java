package lk.ijse.Dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseTm {
    private String programId;
    private String programName;
    private int duration;
    private double fee;
    private String instructorId;
}
