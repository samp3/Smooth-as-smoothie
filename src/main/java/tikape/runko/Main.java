package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaaineDao;
import tikape.runko.database.SmoothieDao;
import tikape.runko.domain.Smoothie;

public class Main {

    public static void main(String[] args) throws Exception {
        
        staticFileLocation("/public");
        
        Database database = new Database("jdbc:sqlite:smoothie_database.db");
        database.init();
        
        SmoothieDao smoothieDao = new SmoothieDao(database);
        RaakaaineDao raakaaineDao = new RaakaaineDao(database);
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/lisays", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());

            return new ModelAndView(map, "lisays");
        }, new ThymeleafTemplateEngine());

        Spark.post("/l_smoothie", (req, res) -> {
            String nimi = req.queryParams("nimi");
            smoothieDao.insert(nimi);
            res.redirect("/lisays");
            return "";
        });
        
        get("/smoothie/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothie", smoothieDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("raakaaineet", raakaaineDao.findAll());
            map.put("categories", raakaaineDao.findAll());
            map.put("template", "templates/index.vtl");
            
            ((Smoothie) map.get("smoothie")).buildRaakaaineet(raakaaineDao);
            
            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());
        
       
                
        get("/lisays_raakaaine", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "lisays_raakaaine");
        }, new ThymeleafTemplateEngine());

        Spark.post("/l_raakaaine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            raakaaineDao.insert(nimi);
            res.redirect("/lisays_raakaaine");
            return "";
        });
        
        get("/lisays_raakaaine/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            raakaaineDao.delete(Integer.parseInt(req.params("id")));
            map.put("raakaaineet", raakaaineDao.findAll());
            
            return new ModelAndView(map, "lisays_raakaaine");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/lisays/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            smoothieDao.delete(Integer.parseInt(req.params("id")));
            map.put("smoothiet", smoothieDao.findAll());

            return new ModelAndView(map, "lisays");
        });
    }
}
