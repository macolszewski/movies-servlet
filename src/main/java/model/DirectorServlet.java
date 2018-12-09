package model;

import com.google.gson.Gson;
import model.entity.Actor;
import model.entity.Director;
import model.repository.DBDirectorRepository;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "director", urlPatterns = "/director/*")
public class DirectorServlet extends HttpServlet {
    @Inject
    private DBDirectorRepository dbDirectorRepository;
    private Gson gson;
    public DirectorServlet() {
        gson = new Gson();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter printWriter = resp.getWriter();
        String id = getPathVariable(req);
        String responseJson = null;
        if (id != null) {
            for (Director director: dbDirectorRepository.getAll()) {
                if (director.getId().equals(id))
                    responseJson = gson.toJson(director);
            }
        } else {
            responseJson = gson.toJson(dbDirectorRepository.getAll());
        }
        printWriter.print(responseJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String body = getBody(req);
        Director director = gson.fromJson(body, Director.class);
        String id = getPathVariable(req);
        if (id != null) {
            dbDirectorRepository.update(id, director);
        } else {
            dbDirectorRepository.add(director);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathVariable(req);
        dbDirectorRepository.delete(id);
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
            if (path.length>0) {

                return path[path.length - 1];
            } else {
                return null;
            }
        }
    }
}
