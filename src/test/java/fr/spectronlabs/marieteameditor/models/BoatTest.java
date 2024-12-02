package fr.spectronlabs.marieteameditor.models;

import fr.spectronlabs.marieteameditor.constants.Constants;
import fr.spectronlabs.marieteameditor.database.BoatQuery;
import fr.spectronlabs.marieteameditor.database.DatabaseConnection;
import fr.spectronlabs.marieteameditor.services.BoatService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class BoatTest {
    private final DatabaseConnection database = new DatabaseConnection(Constants.DATABASE_URL, Constants.DATABASE_USER, Constants.DATABASE_PASSWORD);
    private final Connection connection = database.getConnection();
    private final BoatService service = new BoatService(new BoatQuery(connection));


}