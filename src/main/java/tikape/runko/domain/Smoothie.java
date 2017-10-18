package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Smoothie {

    private Integer id;
    private String nimi;
    
    public Smoothie(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
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

}
