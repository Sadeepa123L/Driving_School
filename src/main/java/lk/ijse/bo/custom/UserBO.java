package lk.ijse.bo.custom;

import lk.ijse.Dto.UserDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.User;

import java.util.List;

public interface UserBO extends SuperBO {
    List<UserDto> getAllUsers();
    boolean updateUser(UserDto userDto);
    boolean deleteUser(String userId);
}
