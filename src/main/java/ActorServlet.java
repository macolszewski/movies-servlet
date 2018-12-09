import com.google.gson.Gson;
import model.entity.Actor;
import model.repository.DBActorRepository;

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
    private DBActorRepository dbActorRepository;
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
            for (Actor actor: dbActorRepository.getAll()) {
                if (actor.getId().equals(id))
                    responseJson = gson.toJson(actor);
            }
        } else {
            responseJson = gson.toJson(dbActorRepository.getAll());
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
            dbActorRepository.update(id, actor);
        } else {
            dbActorRepository.add(actor);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathVariable(req);
        dbActorRepository.delete(id);
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
            return path[path.length - 1];
        }
    }
}
