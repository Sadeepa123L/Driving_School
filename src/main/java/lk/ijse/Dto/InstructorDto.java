package lk.ijse.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class InstructorDto {
    private String instructorId;
    private String name;
    private String specialization;
    private String availability;
}
