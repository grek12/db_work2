package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DAO1 {
    private Connection connection;

    public DAO1(){

        connection = new ConnectionFactory().getConnection();
    }

    public void insertClient(Client client){
        String sql = "insert into client (orgname,adress,director) values (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,client.getOrgname());
            statement.setString(2,client.getAdress());
            statement.setString(3,client.getDirector());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void updateClient(Client client){

        String sql = "update client set orgname=?, adress=?, director=? where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,client.getOrgname());
            statement.setString(2,client.getAdress());
            statement.setString(3,client.getDirector());
            statement.setInt(4, Integer.parseInt(client.getId()));

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void deleteClient(Integer idClient){

        String sql = "delete from client where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,idClient);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Client> searchClient1(String orgname){

        List<Client> clients = new ArrayList<>();

        String sql = "select * from client where orgname like '%" + orgname + "%'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(String.valueOf(resultSet.getInt("id")));
                client.setOrgname(resultSet.getString("orgname"));
                client.setAdress(resultSet.getString("adress"));
                client.setDirector(resultSet.getString("director"));


                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  clients;
    }
    public List<Client> searchClient2(String adress){

        List<Client> clients = new ArrayList<>();

        String sql = "select * from client where adress like '%" + adress + "%'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(String.valueOf(resultSet.getInt("id")));
                client.setOrgname(resultSet.getString("orgname"));
                client.setAdress(resultSet.getString("adress"));
                client.setDirector(resultSet.getString("director"));


                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  clients;
    }
    public List<Client> searchClient3(String director){

        List<Client> clients = new ArrayList<>();

        String sql = "select * from client where director like '%" + director + "%'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(String.valueOf(resultSet.getInt("id")));
                client.setOrgname(resultSet.getString("orgname"));
                client.setAdress(resultSet.getString("adress"));
                client.setDirector(resultSet.getString("director"));


                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  clients;
    }

    public ObservableList<Client> selectClient(){

        ObservableList<Client> clients = FXCollections.observableArrayList();

        String sql = "select * from client ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(String.valueOf(resultSet.getInt("id")));
                client.setOrgname(resultSet.getString("orgname"));
                client.setAdress(resultSet.getString("adress"));
                client.setDirector(resultSet.getString("director"));


                clients.add(client);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  clients;
    }

}
