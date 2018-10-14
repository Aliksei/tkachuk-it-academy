package by.itacademy.dao;

import by.itacademy.enteties.Client;

import java.util.Optional;

public interface ClientDao {

    Integer createClient(final Client client);

    Optional<Client> getClientById(Integer id);

    boolean deleteClient(Integer id);

    boolean updateClient(Client client);
}
