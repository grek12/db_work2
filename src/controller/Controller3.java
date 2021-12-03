package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.DAO1;



public class Controller3  {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField Id;


    @FXML
    private TextField streetField;
    private Client client;
    @FXML
    private Controller mainController;

    @FXML
    private void initialize() {
    }

    @FXML
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public void setPerson(Client client) {
        this.client = client;

        firstNameField.setText(client.getOrgname());
        lastNameField.setText(client.getAdress());
        streetField.setText(client.getDirector());
        Id.setText(client.getId());
    }




    @FXML
    private void handleCancel(javafx.event.ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Заполните поле названия организации!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Заполните поле адреса организации!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Заполните поле руководителя организации!!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Поля ввода не заполнены!");
            alert.setHeaderText("Заполните все поля ввода!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void handleOk(javafx.event.ActionEvent event) {
        Controller controller = new Controller();
        if (isInputValid()) {
            client.setOrgname(firstNameField.getText());
            client.setAdress(lastNameField.getText());
            client.setDirector(streetField.getText());

            try {
                DAO1 dao = new DAO1();
                dao.updateClient(client);
                mainController.updateTable();
                controller.DialogInfo("Данные клиента успешно изменены! ");




            }catch (Exception e){
                controller.DialogError("Не удалось изменить данные клиента!");
                e.printStackTrace();
            }

        }

        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


}