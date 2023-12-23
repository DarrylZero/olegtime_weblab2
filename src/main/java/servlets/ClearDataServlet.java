package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static servlets.AreaCheckServlet.HITS_DATA_ATTRIBUTE;

public class ClearDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.getSession().removeAttribute(HITS_DATA_ATTRIBUTE);
    }
}
