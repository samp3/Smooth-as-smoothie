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

public class RaakaaineDao implements Dao<Raakaaine, Integer> {

    private Database database;

    public RaakaaineDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Raakaaine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Raakaaine o = new Raakaaine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Raakaaine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine");

        ResultSet rs = stmt.executeQuery();
        List<Raakaaine> raakaaineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            raakaaineet.add(new Raakaaine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return raakaaineet;
    }
    
    private int getID() throws SQLException{

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine");
        
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
    public int findID(String nimi) throws SQLException{

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine WHERE nimi = ?");
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
    public void insert(String nimi) throws SQLException {
        
        Connection connection = database.getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT Count(*) FROM Raakaaine WHERE nimi = ?");
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
            
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Raakaaine (id, nimi) VALUES (?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, nimi);

            stmt.execute();

            stmt.close();
            connection.close();
        }
        
    }
    
    public void build(Smoothie smoothie) throws SQLException{

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raakaaine Left Join AnnosRaakaaine ON Raakaaine.id = AnnosRaakaaine.raakaAine_id WHERE AnnosRaakaaine.annos_id ="+smoothie.getId());

        ResultSet rs = stmt.executeQuery();
        List<Raakaaine> raakaaineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            int jarjestys = rs.getInt("jarjestys");
            int maara = rs.getInt("maara");
            String ohje = rs.getString("ohje");
            raakaaineet.add(new Raakaaine(id, nimi, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();
        
        Collections.sort(raakaaineet, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Raakaaine)o1).getJarjestys().compareTo(((Raakaaine)o2).getJarjestys());
            }
        });
        
        smoothie.setRaakaaineet(raakaaineet);
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Raakaaine WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.execute();
        
        stmt.close();
        connection.close();
    }

}