package lk.ijse.bo;

import lk.ijse.bo.custom.SignUpBO;
import lk.ijse.bo.custom.impl.*;

public class BOFactory {

    public enum BOType {
        SIGNUP,LOGIN,STUDENT,COURSE,INSTRUCTOR,LESSON,PAYMENT
    }
    public static SuperBO getBO(BOType type) {
         return switch (type) {
             case SIGNUP -> new SignUpBOImpl();
             case LOGIN -> new LoginBOImpl();
             case STUDENT -> new StudentBOImpl();
             case COURSE -> new CourseBOImpl();
             case INSTRUCTOR -> new InstructorBOImpl();
             case LESSON -> new LessonBOImpl();
             case PAYMENT -> new PaymentBOImpl();
            default -> null;
        };
    }
}
