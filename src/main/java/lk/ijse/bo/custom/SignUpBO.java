package lk.ijse.bo.custom;

import lk.ijse.Dto.UserDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.exception.UserAlreadyExistsException;

public interface SignUpBO extends SuperBO {
    void signUp(UserDto userDTO) throws UserAlreadyExistsException;
}
