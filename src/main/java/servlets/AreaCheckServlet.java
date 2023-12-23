package servlets;

import exceptions.InvalidParameterException;
import model.Hit;
import model.Point;
import model.PointHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AreaCheckServlet extends HttpServlet {
    public static final String HITS_DATA_ATTRIBUTE = "hitsData";
    private final PointHandler pointHandler = new PointHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Hit currentHit = pointHandler.getHitInfo(buildPoint(req));
            HttpSession session = req.getSession();
            saveHitInSession(session, currentHit);
            fillResponse(resp, session);
        } catch (InvalidParameterException e) {
            resp.sendError(403, e.getMessage());
        }
    }

    private Point buildPoint(HttpServletRequest req) {
        double xVal;
        double yVal;
        double rVal;
        long timezone;

        try {
            xVal = Double.parseDouble(req.getParameter("xVal"));
            yVal = Double.parseDouble(req.getParameter("yVal"));
            rVal = Double.parseDouble(req.getParameter("rVal"));
            timezone = Long.parseLong(req.getParameter("timezone"));
        } catch (NumberFormatException exception) {
            throw new InvalidParameterException("Некорректный формат параметров!");
        }

        return new Point(xVal, yVal, rVal, timezone);
    }

    private void saveHitInSession(HttpSession session, Hit hit) {
        ArrayList<Hit> allHits = getHits(session);

        try {
            allHits.add(hit);
        } catch (NullPointerException e) {
            allHits = new ArrayList<Hit>();
            allHits.add(hit);
        }

        session.setAttribute(HITS_DATA_ATTRIBUTE, allHits);
    }

    private void fillResponse(HttpServletResponse resp, HttpSession session) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.print(getTableHtml(session));
    }

    private String getTableHtml(HttpSession session) {
        StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append("<table>")
                .append("<tbody>")
                .append("<tr>")
                .append("<th>X</th>")
                .append("<th>Y</th>")
                .append("<th>R</th>")
                .append("<th>Время попытки</th>")
                .append("<th>Длительность</th>")
                .append("<th>Попадание</th>")
                .append("</tr>");

        getHits(session).forEach((hit) -> {
            tableBuilder.append("<tr>")
                    .append("<td>").append(hit.getXVal()).append("</td>")
                    .append("<td>").append(hit.getYVal()).append("</td>")
                    .append("<td>").append(hit.getRVal()).append("</td>")
                    .append("<td>").append(hit.getCurrentTime()).append("</td>")
                    .append("<td>").append(hit.getExecutionTime()).append("</td>")
                    .append("<td>").append(hit.isHit()).append("</td>")
                    .append("</tr>");
        });

        tableBuilder.append("</tbody>");
        tableBuilder.append("</table>");

        return tableBuilder.toString();
    }

    private ArrayList<Hit> getHits(HttpSession session) {
        return (ArrayList<Hit>) session.getAttribute(HITS_DATA_ATTRIBUTE);
    }
}
