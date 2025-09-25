package lk.ijse.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonTm {
    private String lessonId;
    private String studentId;
    private String courseId;
    private String instructorId;
    private String lessonDate;
    private int duration;
}
