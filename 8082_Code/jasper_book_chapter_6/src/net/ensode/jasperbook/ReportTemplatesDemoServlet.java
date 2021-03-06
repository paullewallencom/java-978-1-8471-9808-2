package net.ensode.jasperbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class ReportTemplatesDemoServlet extends HttpServlet
{

    @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    ServletOutputStream servletOutputStream = response.getOutputStream();
    InputStream reportStream = getServletConfig().getServletContext()
        .getResourceAsStream("/reports/TemplateDemo.jasper");

    try
    {
        response.setContentType("application/pdf");
      JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream,
          new HashMap(), new JREmptyDataSource());

      servletOutputStream.flush();
      servletOutputStream.close();
    }
    catch (JRException e)
    {
      // display stack trace in the browser
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      e.printStackTrace(printWriter);
      response.setContentType("text/plain");
      servletOutputStream.print(stringWriter.toString());
      
    }
    finally
    {
      servletOutputStream.flush();
      servletOutputStream.close();
    }
  }
}
