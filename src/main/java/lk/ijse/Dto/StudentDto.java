package lk.ijse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    private String studentId;
    private String name;
    private String address;
    private String contact;
    private Date regDate;
}
