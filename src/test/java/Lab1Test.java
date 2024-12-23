
import Util.ConnectionUtil;
import Util.FileUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Lab1Test {


    @Test
    public void problem1Test(){

        try {
            String sql = FileUtil.parseSQLFile("src/main/lab1.sql");
            if(sql.isEmpty()){
                Assert.fail("No SQL statement in lab1.sql.");
            }
            Connection connection = ConnectionUtil.getConnection();
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException e) {
            Assert.fail("There was an issue running your sql statement in lab1.sql: "+e.getMessage());
        }
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "select lastname from Person;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeQuery();
        } catch (SQLException e) {
            Assert.fail("Could not select the lastname column from the Person table.");
        }
    }
    /**
     * The @Before annotation runs before every test so that way we create the tables required prior to running the test
     */
    @Before
    public void beforeTest(){
        try {

            Connection connection = ConnectionUtil.getConnection();

            //Write SQL logic here
            String sql1 = "CREATE TABLE Person (id SERIAL PRIMARY KEY, firstname varchar(100));";
            String sql2 = "INSERT INTO Person (firstname) VALUES ('Kevin');";
            String sql3 = "INSERT INTO Person (firstname) VALUES ('Brian');";
            String sql4 = "INSERT INTO Person (firstname) VALUES ('Charles');";

            PreparedStatement ps = connection.prepareStatement(sql1 + sql2 + sql3 + sql4);

            ps.executeUpdate();

        } catch (SQLException e) {
            Assert.fail("Issue with lab setup: "+e.getMessage());
        }
    }

    /**
     * The @After annotation runs after every test so that way we drop the tables to avoid conflicts in future tests
     */
    @After
    public void cleanup(){

        try {

            Connection connection = ConnectionUtil.getConnection();
            String sql = "DROP TABLE Person;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();

        } catch (SQLException e) {
        }
    }

}
