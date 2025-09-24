package lk.ijse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private int userId;
    private String userName;
    private String password;
    private String role;

    public  UserDto(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
