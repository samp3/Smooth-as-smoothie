package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import tikape.runko.domain.Raakaaine;
import tikape.runko.domain.Smoothie;

public class AnnosRaakaaineDao implements Dao<Raakaaine, Integer> {

    private Database database;

    public AnnosRaakaaineDao(Database database) {
        this.database = database;
    }

    public void insert(int r_id, int a_id, int j, int m, String o) throws SQLException {
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO AnnosRaakaAine (raakaAine_id, annos_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, r_id);
        stmt.setInt(2, a_id);
        stmt.setInt(3, j);
        stmt.setInt(4, m);
        stmt.setString(5, o);
        
        stmt.execute();
        
        stmt.close();
        connection.close();
        
    }

    public void deleteR(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM AnnosRaakaAine WHERE raakaAine_id = ?");
        stmt.setInt(1, key);
        
        stmt.execute();
        
        stmt.close();
        connection.close();
    }

    public void deleteS(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");
        stmt.setInt(1, key);
        
        stmt.execute();
        
        stmt.close();
        connection.close();
    }

    @Override
    public Raakaaine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Raakaaine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}