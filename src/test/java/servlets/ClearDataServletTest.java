package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClearDataServletTest {
    private final ClearDataServlet servlet = new ClearDataServlet();
    private final HttpSession session = mock(HttpSession.class);

    @BeforeEach
    public void setUp() throws ServletException {
        ServletConfig config = mock(ServletConfig.class);

        servlet.init(config);
    }

  //  @Test
    void doGet() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getSession()).thenReturn(session);

        servlet.doGet(request, response);

        verify(request).getSession();
        verify(session).removeAttribute("hitsData");
        verifyNoMoreInteractions(session);
    }
}