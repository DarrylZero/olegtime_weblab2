package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ControllerServletTest {
    private final ControllerServlet servlet = new ControllerServlet();

    @BeforeEach
    public void setUp() throws ServletException {
        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(config.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(any())).thenReturn(dispatcher);
        when(context.getNamedDispatcher(any())).thenReturn(dispatcher);

        servlet.init(config);
    }

   // @Test
    public void doGetWithClearCommand() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("clear")).thenReturn("true");

        servlet.doGet(request, response);

        verify(servlet.getServletConfig()).getServletContext();
        verify(request).getParameter("clear");
        verify(servlet.getServletContext()).getNamedDispatcher("ClearDataServlet");
    }

 //   @Test
    public void doGetWithPointCommand() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("xVal")).thenReturn("string");
        when(request.getParameter("yVal")).thenReturn("string");
        when(request.getParameter("rVal")).thenReturn("string");
        when(request.getParameter("timezone")).thenReturn("string");

        servlet.doGet(request, response);

        verify(servlet.getServletConfig()).getServletContext();
        verify(request).getParameter("xVal");
        verify(request).getParameter("yVal");
        verify(request).getParameter("rVal");
        verify(request).getParameter("timezone");
        verify(servlet.getServletContext()).getNamedDispatcher("AreaCheckServlet");
    }

 //   @Test
    public void doGetWithJSP() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doGet(request, response);

        verify(servlet.getServletConfig()).getServletContext();
        verify(servlet.getServletContext()).getRequestDispatcher("/form.jsp");
    }
}