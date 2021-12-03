package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO2 {
    private Connection connection;

    public DAO2(){

        connection = new ConnectionFactory().getConnection();
    }

    public void insertContract(Contract contract){
        String sql = "insert into contract (date, sum , idclient ) values (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1,contract.getDate());
            statement.setFloat(2, contract.getSum());
            statement.setInt(3, contract.getIdClient());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void updateContract(Contract contract){

        String sql = "update contract set date=?, sum=? where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setDate(1,contract.getDate());
            statement.setFloat(2, contract.getSum());
            statement.setInt(3, Integer.parseInt(contract.getId()));


            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void deleteContract(Integer idContract){

        String sql = "delete from contract where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,idContract);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Contract> searchContract1(int id,Date date){

        List<Contract> contracts = new ArrayList<>();

        //String sql = "select * from contract where date = '%" + 'date' + "%'";
        String sql= String.format("select * from contract where idclient = '%s' AND date = '%s'", id, date);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Contract contract = new Contract();

                contract.setId(String.valueOf(resultSet.getInt("id")));
                contract.setDate(Date.valueOf(resultSet.getString("date")));
                contract.setSum(Float.parseFloat(resultSet.getString("sum")));



                contracts.add(contract);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  contracts;
    }


    public ObservableList<Contract> selectContract(){

        ObservableList<Contract> contracts = FXCollections.observableArrayList();

        String sql = "select * from contract ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Contract contract = new Contract();

                contract.setId(String.valueOf(resultSet.getInt("id")));
                contract.setDate(resultSet.getDate("date"));
                contract.setSum(Float.parseFloat(resultSet.getString("sum")));



                contracts.add(contract);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  contracts;
    }
    public List<Contract> searchSelectContract1(int id){

        List<Contract> contracts = new ArrayList<>();


        String sql= String.format("select * from contract where idclient = %s",id);
        //String sql = "select * from contract where idclient =   ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Contract contract = new Contract();

                contract.setId(String.valueOf(resultSet.getInt("id")));
                contract.setDate(Date.valueOf(resultSet.getString("date")));
                contract.setSum(Float.parseFloat(resultSet.getString("sum")));



                contracts.add(contract);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  contracts;
    }
}
