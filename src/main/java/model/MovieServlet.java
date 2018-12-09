package model;

import com.google.gson.Gson;
import model.entity.Director;
import model.entity.Movie;
import model.repository.DBMovieRepository;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "movie", urlPatterns = "/movie/*")
public class MovieServlet extends HttpServlet {
    @Inject
    private DBMovieRepository dbMovieRepository;
    private Gson gson;
    public MovieServlet() {
        gson = new Gson();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter printWriter = resp.getWriter();
        String id = getPathVariable(req);
        String responseJson = null;
        if (id != null) {
            for (Movie movie: dbMovieRepository.getAll()) {
                if (movie.getId().equals(id))
                    responseJson = gson.toJson(movie);
            }
        } else {
            responseJson = gson.toJson(dbMovieRepository.getAll());
        }
        printWriter.print(responseJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String body = getBody(req);
        Movie movie = gson.fromJson(body, Movie.class);
        String id = getPathVariable(req);
        if (id != null) {
            dbMovieRepository.update(id, movie);
        } else {
            dbMovieRepository.add(movie);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = getPathVariable(req);
        dbMovieRepository.delete(id);
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
