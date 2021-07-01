package application;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataTest {

    @Test
    public void testConnection() throws SQLException {
        Connection data =  Data.getConnection();

        Assert.assertNotEquals(null, data);
    }
}