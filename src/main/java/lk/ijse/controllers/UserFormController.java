package lk.ijse.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Dto.UserDto;
import lk.ijse.Dto.tm.InstructorTm;
import lk.ijse.Dto.tm.UserTm;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    public TableView<UserTm> UserDetailsTable;
    public TableColumn<UserTm, String> ColId;
    public TableColumn<UserTm,String> ColUserName;
    public TableColumn<UserTm, String> ColPassword;
    public TableColumn<UserTm, String > colRole;

    UserBO userBo = (UserBO) BOFactory.getBO(BOFactory.BOType.USER);

    public void btnUserUpdateOnAction(ActionEvent actionEvent) {
        Integer id = Integer.valueOf(ColId.getText());
        String userName = ColUserName.getText();
        String password = ColPassword.getText();
        String role = colRole.getText();

        UserDto userDto = new UserDto(
                id,
                userName,
                password,
                role
        );
        try {
            boolean isUpdated = userBo.updateUser(userDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User Updated Successfully!").show();
                LoadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Updated!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUserDeleteOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ColUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        ColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            LoadTableData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void LoadTableData() throws Exception {
        UserDetailsTable.setItems(FXCollections.observableList(
                userBo.getAllUsers().stream().map(userDto ->
                        new UserTm(
                                userDto.getUserId(),
                                userDto.getUserName(),
                                userDto.getPassword(),
                                userDto.getRole()
                        )).toList()
        ));
    }
}
