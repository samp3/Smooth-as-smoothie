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
    
    private int getCount() throws SQLException{

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Count(*) FROM Raakaaine");
        
        ResultSet rs = stmt.executeQuery();
        
        int r = rs.getInt("Count(*)");
        System.out.println(r);
        
        rs.close();
        stmt.close();
        connection.close();
        
        return r;
    }

    public void insert(String nimi) throws SQLException {
        
        int id = this.getCount();
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Raakaaine (id, nimi) VALUES (?, ?)");
        stmt.setInt(1, id);
        stmt.setString(2, nimi);
        
        stmt.execute();
        
        stmt.close();
        connection.close();
        
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
        // ei toteutettu
    }

}