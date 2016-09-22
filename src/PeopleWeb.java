import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PeopleWeb {
    static ArrayList<Person> peopleList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Spark.init();
        File f = new File("people.csv");
        Scanner fileScanner = new Scanner(f);


        String line;
        while (fileScanner.hasNext()) {
            line = fileScanner.nextLine();
            while (line.startsWith("id,first_name")) {
                line = fileScanner.nextLine();
            }
            String[] columns = line.split(",");
            Person person = new Person(Integer.parseInt(columns[0]), columns[1], columns[2], columns[3], columns[4], columns[5]);
            peopleList.add(person);
        }
        Spark.get("/",
                ((request, response) -> {
                    String offset = request.queryParams("offset");
                    HashMap m = new HashMap();

                    Session session = request.session();
                    String firstName = session.attribute("?????");

                    m.put("names", peopleList);
                    return new ModelAndView(m, "people.html");
                }),
                new MustacheTemplateEngine()

        );
        Spark.get(
                "/person",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Integer id = peopleList.get(0); //TODO this is close!
                    Person p = new Person(id);
                    m.put("nameDetail", p);
                    return new ModelAndView(m, "people.html");
                }),
                new MustacheTemplateEngine()
        );


    }

    public static void addTestNames() {

    }

}

