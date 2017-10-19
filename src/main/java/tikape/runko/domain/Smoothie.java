package tikape.runko.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.RaakaaineDao;

public class Smoothie {

    private Integer id;
    private String nimi;
    private List<Raakaaine> raakaaineet;
    
    public Smoothie(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.raakaaineet = new ArrayList<>();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public List<Raakaaine> getRaakaaineet(){
        return raakaaineet;
    }
    
    public void setRaakaaineet(List<Raakaaine> raakaineet){
        this.raakaaineet = raakaineet;
    }
    
    public void buildRaakaaineet(RaakaaineDao rd) throws SQLException{
        rd.build(this);
    }
}
