/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Smoothie;

public class SmoothieDao implements Dao<Smoothie, Integer> {

    private Database database;

    public SmoothieDao(Database database) {
        this.database = database;
    }

    @Override
    public Smoothie findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Smoothie o = new Smoothie(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Smoothie> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos");

        ResultSet rs = stmt.executeQuery();
        List<Smoothie> smoothiet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            smoothiet.add(new Smoothie(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return smoothiet;
    }
    
    public int findID(String nimi) throws SQLException{

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos WHERE nimi = ?");
        stmt.setString(1, nimi);
        ResultSet rs = stmt.executeQuery();
        
        int r = 0;
        
        if(rs.next()){
            r = rs.getInt("id");
        } else {
            r = -1;
        }
        
        rs.close();
        stmt.close();
        connection.close();
        
        return r;
    }
    
    private int getID() throws SQLException{

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos");
        
        ResultSet rs = stmt.executeQuery();
        
        int i_id = 0;
        
        while(rs.next()){
            int c_id = rs.getInt("id");
            if(c_id != i_id){
                break;
            }
            i_id++;
        }
        
        
        rs.close();
        stmt.close();
        connection.close();
        
        return i_id;
    }

    public void insert(String nimi) throws SQLException {
        
        Connection connection = database.getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT Count(*) FROM Annos WHERE nimi = ?");
        stm.setString(1, nimi);
        
        stm.execute();
        
        ResultSet rs = stm.executeQuery();
        
        if(rs.getInt("Count(*)") > 0){
            stm.close();
            rs.close();
            connection.close();
        } else {
            rs.close();
            stm.close();
            int id = this.getID();

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Annos (id, nimi) VALUES (?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, nimi);

            stmt.execute();

            stmt.close();
            connection.close();
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Annos WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.execute();
        
        stmt.close();
        connection.close();
    }

}
