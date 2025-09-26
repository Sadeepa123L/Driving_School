package lk.ijse.bo.custom.impl;

import lk.ijse.Dto.UserDto;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);

    @Override
    public List<UserDto> getAllUsers() {
        List<User> list = userDAO.getAllUsers();
        List<UserDto> userDto = new ArrayList<>();
        for (User user : list) {
            UserDto dto = new UserDto(
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getRole()
            );
            userDto.add(dto);
        }
        return userDto;
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        return userDAO.update(new User(
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getPassword(),
                userDto.getRole()
        ));
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }
}
