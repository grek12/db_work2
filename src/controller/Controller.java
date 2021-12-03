package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Client;
import model.Contract;
import model.DAO1;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import model.DAO2;

public class Controller implements Initializable {

        @FXML
        public TableView<Client> clientTable;

        @FXML
        private TableColumn<Client, Integer> idClient;

        @FXML
        private TableColumn<Client, String> orgname;

        @FXML
        private TableColumn<Client, String> adress;

        @FXML
        private TableColumn<Client, String> director;

        @FXML
        private TableView<Contract> contractTable;

        @FXML
        private TableColumn<Contract, String> date;

        @FXML
        private TableColumn<Contract, Float> sum;

        @FXML
        private TableColumn<Contract, String> idContract;

        @FXML
        private TextField nameSearch;

        @FXML
        private TextField adressSearch;

        @FXML
        private TextField directorSearch;

    @FXML
    private DatePicker datePick;
        @FXML
        private Button addClientBut;

        public DAO1 dao;
        public DAO2 dao2;

        public void initialize(URL url, ResourceBundle resourceBundle) {
            dao = new DAO1();
            clientTable.setItems(dao.selectClient());
            dao2 = new DAO2();
            contractTable.setItems(dao2.selectContract());
            idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
            orgname.setCellValueFactory(new PropertyValueFactory<>("orgname"));
            adress.setCellValueFactory(new PropertyValueFactory<>("adress"));
            director.setCellValueFactory(new PropertyValueFactory<>("director"));
            idContract.setCellValueFactory(new PropertyValueFactory<>("id"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            sum.setCellValueFactory(new PropertyValueFactory<>("sum"));
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



        @FXML
        private void addClient() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/addClient.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 410, 150);
            Stage stage = new Stage();
            stage.setTitle("Добавление нового клиента");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Controller2 controller = fxmlLoader.getController();
            controller.setMainController(this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

        }

        }
        @FXML
        private void searchName(){

            try{

                List<Client> result = dao.searchClient1(nameSearch.getText());

                clientTable.setItems(FXCollections.observableList(result));


            }catch (Exception e){
                DialogError("Ошибка!");
                e.printStackTrace();
            }
        }

        @FXML
        private void searchAdress(){

            try{

                List<Client> result = dao.searchClient2(adressSearch.getText());

                clientTable.setItems(FXCollections.observableList(result));


            }catch (Exception e){
                DialogError("Ошибка!");
                e.printStackTrace();
            }
        }

        @FXML
        private void searchDirector(){

            try{

                List<Client> result = dao.searchClient3(directorSearch.getText());

                clientTable.setItems(FXCollections.observableList(result));


            }catch (Exception e){
                DialogError("Ошибка!");
                e.printStackTrace();
            }
        }


    @FXML
    private void searchDate(){

        try{
            Client client = clientTable.getSelectionModel().getSelectedItem();
            List<Contract> result = dao2.searchContract1(Integer.parseInt(client.getId()),Date.valueOf(datePick.getValue()));

            contractTable.setItems(FXCollections.observableList(result));


        }catch (Exception e){
            DialogError("Ошибка!");
            e.printStackTrace();
        }
    }


        @FXML
        private void updateClient(Client client) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/updateClient.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 410, 180);
                Stage stage = new Stage();
                stage.setTitle("Добавление нового клиента");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                Controller3 controller = fxmlLoader.getController();
                controller.setMainController(this);
                controller.setPerson(client);
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

            }

        }

        @FXML
        public void clickItem() {
            clientTable.setOnMouseClicked(event -> {
                showContract();
            });
        }
        public void showContract() {
            Client client = clientTable.getSelectionModel().getSelectedItem();
            List<Contract> result = dao2.searchSelectContract1(Integer.parseInt(client.getId()));
            contractTable.setItems(FXCollections.observableList(result));

        }

        @FXML
        private void deleteClient() {
            Client client = clientTable.getSelectionModel().getSelectedItem();
            if (client == null) {
                DialogError("Удаление невозможно, список клиентов пуст!");
            } else {
                if (DialogConf("Подтверждаете удаление клиента?")) {

                    try{

                        dao.deleteClient(Integer.valueOf(client.getId()));
                        DialogInfo("Клиент успешно удален!");
                        updateTable();

                    }catch(Exception e){
                        DialogError("Ошибка при удалении");
                        e.printStackTrace();
                    }
                }
            }
            }

            @FXML
            public void updateTable(){
                clientTable.setItems(dao.selectClient());
            }

            @FXML
            public void updateTable2(){
            contractTable.setItems(dao2.selectContract());
            }

            public void DialogError(String error){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText(error);
                alert.showAndWait();
            }


        @FXML
        private void addContract( ) {
            Client client = clientTable.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/addContract.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 410, 180);
                Stage stage = new Stage();
                stage.setTitle("Добавление нового контракта");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                Controller4 controller4 = fxmlLoader.getController();
                controller4.setMainController(this);
                controller4.setPerson(client.getId());
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

            }

        }


        public void DialogInfo(String info){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setHeaderText(null);
            alert.setContentText(info);

            alert.showAndWait();
        }

        private boolean DialogConf(String conf){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText(conf);

            Optional<ButtonType> opcao = alert.showAndWait();

            if (opcao.get() == ButtonType.OK){
                return true;
            }
            return false;
        }


        public void updateClient(ActionEvent event) {
           Client client = clientTable.getSelectionModel().getSelectedItem();
            if (client != null) {
           updateClient(client);
            }else {
                DialogError("Клиент не выбран");
            }
            }
        @FXML
        private void updateContract(Contract contract){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/updateContract.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 410, 180);
                Stage stage = new Stage();
                stage.setTitle("Изменение данных контракта");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                Controller5 controller = fxmlLoader.getController();
                controller.setPerson(contract);
            } catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Не удалось создать новое окно.", e);

            }

        }
    public void updateContract(ActionEvent event) {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();
        if (contract != null) {
            updateContract(contract);
        }else {
            DialogError("Контракт не выбран");
        }
    }
    @FXML
    private void deleteContract() {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();
        if (contract == null) {
            DialogError("Удаление невозможно, выберите контракт!");
        } else {
            if (DialogConf("Подтверждаете удаление контракта?")) {

                try{

                    dao2.deleteContract(Integer.valueOf(contract.getId()));
                    DialogInfo("Контракт успешно удален!");
                    updateTable2();
                }catch(Exception e){
                    DialogError("Ошибка при удалении");
                    e.printStackTrace();
                }
            }
        }
    }
    }



