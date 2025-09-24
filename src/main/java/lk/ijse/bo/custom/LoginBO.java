package lk.ijse.bo.custom;

import lk.ijse.Dto.UserDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.exception.InvalidCredentialsException;

public interface LoginBO extends SuperBO {
    UserDto getUser(String userName) throws InvalidCredentialsException;

}
