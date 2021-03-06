import com.google.gson.Gson;
import model.ActorService;
import model.entity.Actor;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "actor", urlPatterns = "/actor/*")
public class ActorServlet extends HttpServlet {
    @Inject
    private ActorService actorService;
    private Gson gson;

    public ActorServlet() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter printWriter = resp.getWriter();
        String id = getPathVariable(req);
        String responseJson = null;
        if (id != null) {
            for (Actor actor : actorService.getAll()) {
                if (actor.getId().equals(id))
                    responseJson = gson.toJson(actor);
            }
        } else {
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            if (name != null & surname != null) {
                responseJson = gson.toJson(actorService.findByName(name, surname));
            } else if (name != null) {
                responseJson = gson.toJson(actorService.findByName(name));
            } else if (surname != null) {
                responseJson = gson.toJson(actorService.findBySurname(surname));
            } else {
                responseJson = gson.toJson(actorService.getAll());
            }
        }
        printWriter.print(responseJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String body = getBody(req);
        Actor actor = gson.fromJson(body, Actor.class);
        String id = getPathVariable(req);
        if (id != null) {
            actorService.update(id, actor);
        } else {
            actorService.add(actor);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathVariable(req);
        actorService.delete(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    protected String getBody(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    protected String getPathVariable(HttpServletRequest req) {
        if (req.getPathInfo() == null) {
            return null;
        } else {
            String[] path = req.getPathInfo().split("/");
            if (path.length > 0) {

                return path[path.length - 1];
            } else {
                return null;
            }
        }
    }
}
