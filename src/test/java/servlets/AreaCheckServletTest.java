package servlets;

import model.Hit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AreaCheckServletTest {
    private final AreaCheckServlet servlet = new AreaCheckServlet();
    private final HttpSession session = mock(HttpSession.class);

    @BeforeEach
    public void setUp() throws ServletException {
        ServletConfig config = mock(ServletConfig.class);
        HashMap<String, Object> sessionStore = new HashMap<>();

        doAnswer((i) -> {
            String key = i.getArgument(0);
            Object value = i.getArgument(1);
            sessionStore.put(key, value);
            return null;
        }).when(session).setAttribute(any(), any());

        when(session.getAttribute("hitsData")).thenAnswer((i) -> {
            String key = i.getArgument(0);
            return sessionStore.get(key);
        });

        servlet.init(config);
    }

//    @Test
    public void doGetWithCorrectRequest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        PrintWriter writer = new PrintWriter(outputStreamWriter);

        when(response.getWriter()).thenReturn(writer);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("xVal")).thenReturn("1");
        when(request.getParameter("yVal")).thenReturn("2");
        when(request.getParameter("rVal")).thenReturn("3");
        when(request.getParameter("timezone")).thenReturn("60");

        servlet.doGet(request, response);
        outputStreamWriter.flush();

        verify(response).getWriter();
        verify(request).getSession();
        verify(session, times(2)).getAttribute("hitsData");
        verify(session).setAttribute(eq("hitsData"), any());
        verify(request).getParameter("xVal");
        verify(request).getParameter("yVal");
        verify(request).getParameter("rVal");
        verify(request).getParameter("timezone");

        ArrayList<Hit> hits = (ArrayList<Hit>) session.getAttribute("hitsData");
        Hit hit = hits.get(0);
        String table = "<table><tbody><tr><th>X</th><th>Y</th><th>R</th><th>Время попытки</th><th>Длительность</th><th>Попадание</th></tr><tr>" +
                "<td>" + hit.getXVal() + "</td>" +
                "<td>" + hit.getYVal() + "</td>" +
                "<td>" + hit.getRVal() + "</td>" +
                "<td>" + hit.getCurrentTime() + "</td>" +
                "<td>" + hit.getExecutionTime() + "</td>" +
                "<td>" + hit.isHit() + "</td>" +
                "</tr></tbody></table>";

        assertEquals(outputStream.toString(), table);
        assertEquals(hits.size(), 1);
    }

 //   @Test
    public void doGetWithCorrectRequestWithDataInSession() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        PrintWriter writer = new PrintWriter(outputStreamWriter);

        ArrayList<Hit> hits = new ArrayList<Hit>();
        hits.add(Hit.builder()
                .xVal(1)
                .yVal(2)
                .rVal(3)
                .currentTime(LocalDateTime.of(2022, 1, 3, 23, 12, 3))
                .executionTime(4)
                .isHit(true).build());
        session.setAttribute("hitsData", hits);

        when(response.getWriter()).thenReturn(writer);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("xVal")).thenReturn("1");
        when(request.getParameter("yVal")).thenReturn("2");
        when(request.getParameter("rVal")).thenReturn("3");
        when(request.getParameter("timezone")).thenReturn("60");

        servlet.doGet(request, response);
        outputStreamWriter.flush();

        verify(response).getWriter();
        verify(request).getSession();
        verify(session, times(2)).getAttribute("hitsData");
        verify(session, times(2)).setAttribute(eq("hitsData"), any());
        verify(request).getParameter("xVal");
        verify(request).getParameter("yVal");
        verify(request).getParameter("rVal");
        verify(request).getParameter("timezone");

        Hit hit = hits.get(1);
        String table = "<table><tbody><tr><th>X</th><th>Y</th><th>R</th><th>Время попытки</th><th>Длительность</th><th>Попадание</th></tr>" +
                "<tr><td>1.0</td><td>2.0</td><td>3.0</td><td>2022-01-03T23:12:03</td><td>4</td><td>true</td></tr><tr>" +
                "<td>" + hit.getXVal() + "</td>" +
                "<td>" + hit.getYVal() + "</td>" +
                "<td>" + hit.getRVal() + "</td>" +
                "<td>" + hit.getCurrentTime() + "</td>" +
                "<td>" + hit.getExecutionTime() + "</td>" +
                "<td>" + hit.isHit() + "</td>" +
                "</tr></tbody></table>";

        assertEquals(outputStream.toString(), table);
        assertEquals(hits.size(), 2);
    }

//    @Test
    public void doGetWithIncorrectParamsForm() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("xVal")).thenReturn("error");

        servlet.doGet(request, response);

        verify(request).getParameter("xVal");
        verify(response).sendError(403, "Некорректный формат параметров!");
    }

  //  @Test
    public void doGetWithInvalidYParamInterval() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("xVal")).thenReturn("1");
        when(request.getParameter("yVal")).thenReturn("7");
        when(request.getParameter("rVal")).thenReturn("3");
        when(request.getParameter("timezone")).thenReturn("60");

        servlet.doGet(request, response);

        verify(request).getParameter("xVal");
        verify(request).getParameter("yVal");
        verify(request).getParameter("rVal");
        verify(request).getParameter("timezone");
        verify(response).sendError(403, "Значение Y не попадает в нужный интервал!");
    }

 //   @Test
    public void doGetWithInvalidRParamInterval() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("xVal")).thenReturn("1");
        when(request.getParameter("yVal")).thenReturn("3");
        when(request.getParameter("rVal")).thenReturn("-3");
        when(request.getParameter("timezone")).thenReturn("60");

        servlet.doGet(request, response);

        verify(request).getParameter("xVal");
        verify(request).getParameter("yVal");
        verify(request).getParameter("rVal");
        verify(request).getParameter("timezone");
        verify(response).sendError(403, "Значение R не может быть неположительным!");
    }
}