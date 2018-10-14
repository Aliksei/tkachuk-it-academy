package by.itacademy.dao;

import by.itacademy.utils.SqlQueryManager;
import by.itacademy.connector.PostgresConnector;
import by.itacademy.enteties.Client;
import by.itacademy.exceptions.DaoLayerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {

    private static final String GET_CLIENT_BY_ID = "getClient.sql";
    private static final String CREATE_CLIENT = "createClient.sql";
    private static final String DELETE_CLIENT = "deleteClient.sql";
    private static final String UPDATE_CLIENT = "updateClient.sql";

    @Override
    public synchronized Integer createClient(Client client) {
        Integer id = null;
        Connection connection = PostgresConnector.getDefaultConnection();

        String insert = SqlQueryManager.getSqlQuery(CREATE_CLIENT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert, 1)) {
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getSecondName());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            return id;
        }catch (SQLException e){
            throw new DaoLayerException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public synchronized Optional<Client> getClientById(Integer id) {
        Client client = null;

        Connection connection = PostgresConnector.getDefaultConnection();
        String query = SqlQueryManager.getSqlQuery(GET_CLIENT_BY_ID);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                client = Client.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("first_name"))
                        .secondName(resultSet.getString("second_name"))
                        .age(resultSet.getInt("age"))
                        .build();
            }
            return Optional.ofNullable(client);
        }catch (SQLException e){
            throw new DaoLayerException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public synchronized boolean deleteClient(Integer id) {
        boolean result;
        Connection connection = PostgresConnector.getDefaultConnection();
        String deleteQuery = SqlQueryManager.getSqlQuery(DELETE_CLIENT);

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoLayerException(e.getMessage(), e.getCause());
        }
        return result;
    }

    @Override
    public synchronized boolean updateClient(Client client) {
        boolean result;
        Connection connection = PostgresConnector.getDefaultConnection();
        String updateQuery = SqlQueryManager.getSqlQuery(UPDATE_CLIENT);

        try(PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getSecondName());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setInt(4, client.getId());

            result = preparedStatement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoLayerException(e.getMessage(), e.getCause());
        }
        return result;
    }
}
