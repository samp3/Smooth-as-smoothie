package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.SmoothieDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothie_database.db");
        database.init();
        
        SmoothieDao smoothieDao = new SmoothieDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothieDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/lisays", (req, res) -> {
            HashMap map = new HashMap<>();

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

            return new ModelAndView(map, "smoothie");
        }, new ThymeleafTemplateEngine());
                
        get("/lisays_raakaaine", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "lisays_raakaaine");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisays_raakaaine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            smoothieDao.insert(nimi);
            res.redirect("/lisays_raakaaine");
            return "";
        });
    }
}
