package lk.ijse.Dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTm {
    private int userId;
    private String userName;
    private String password;
    private String role;
}
