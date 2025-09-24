package lk.ijse.bo;

import lk.ijse.bo.custom.SignUpBO;
import lk.ijse.bo.custom.impl.LoginBOImpl;
import lk.ijse.bo.custom.impl.SignUpBOImpl;

public class BOFactory {

    public enum BOType {
        SIGNUP,LOGIN
    }
    public static SuperBO getBO(BOType type) {
         return switch (type) {
            case SIGNUP -> new SignUpBOImpl();
             case LOGIN -> new LoginBOImpl();
            default -> null;
        };
    }
}
