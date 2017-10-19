package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Annos (id integer PRIMARY KEY, nimi text);");
        lista.add("INSERT INTO Annos VALUES(0, 'Mega Smoothie');");
        lista.add("INSERT INTO Annos VALUES(1, 'Super Smoothie');");
        lista.add("INSERT INTO Annos VALUES(2, 'Ultra Smoothie');");
        lista.add("CREATE TABLE RaakaAine (id integer PRIMARY KEY, nimi text);");
        lista.add("INSERT INTO RaakaAine VALUES(0, 'Maito');");
        lista.add("INSERT INTO RaakaAine VALUES(1, 'Vesi');");
        lista.add("INSERT INTO RaakaAine VALUES(2, 'Hiiva');");
        lista.add("CREATE TABLE AnnosRaakaAine (raakaAine_id INTEGER, annos_id INTEGER, jarjestys INTEGER, maara INTEGER, ohje TEXT, FOREIGN KEY (raakaAine_id) References RaakaAine(id), FOREIGN KEY (annos_id) REFERENCES Annos(id));");
        lista.add("INSERT INTO AnnosRaakaAine VALUES(0, 0, 1, 1, 'Kauniisti');");
        lista.add("INSERT INTO AnnosRaakaAine VALUES(0, 1, 1, 1, 'Rumasti');");
        lista.add("INSERT INTO AnnosRaakaAine VALUES(0, 2, 1, 1, 'Jotenkin');");
        lista.add("INSERT INTO AnnosRaakaAine VALUES(1, 0, 2, 1, 'Vaikka väkisin');");
        lista.add("INSERT INTO AnnosRaakaAine VALUES(2, 1, 2, 1, 'Tunget vaan sinne');");
        
        return lista;
    }
}
