package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Contract;
import model.DAO2;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Controller5  {

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker datePick;

    @FXML
    private TextField Id;

    private  Contract contract;
    @FXML
    private Controller mainController;
    @FXML
    private void initialize() {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }

        };
        datePick.setConverter(converter);
        datePick.setPromptText("yyyy-MM-dd");
    }


    public void setPerson(Contract contract) {
        this.contract = contract;

        datePick.setValue(contract.getDate().toLocalDate());
        lastNameField.setText(String.valueOf(contract.getSum()));
        Id.setText(contract.getId());
    }




    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private boolean isInputValid() {
        String errorMessage = "";


        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "?????????????????? ???????? ?????????? ??????????????????!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("???????? ?????????? ???? ??????????????????!");
            alert.setHeaderText("?????????????????? ?????? ???????? ??????????!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    @FXML
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }
    public void handleOk(javafx.event.ActionEvent event) {
        Controller controller = new Controller();
        if (isInputValid()) {
            contract.setDate(Date.valueOf(datePick.getValue()));
            contract.setSum(Float.parseFloat(lastNameField.getText()));


            try {
                DAO2 dao2 = new DAO2();
                dao2.updateContract(contract);
                mainController.updateTable2();
                controller.DialogInfo("???????????? ?????????????? ?????????????? ????????????????! ");




            }catch (Exception e){
                controller.DialogError("???? ?????????????? ???????????????? ???????????? ??????????????!");
                e.printStackTrace();
            }

        }

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


}
