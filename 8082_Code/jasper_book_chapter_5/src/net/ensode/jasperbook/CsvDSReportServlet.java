package net.ensode.jasperbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRCsvDataSource;

public class CsvDSReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        InputStream reportStream = getServletConfig().getServletContext().getResourceAsStream("/reports/AircraftReport.jasper");

        try {

            JRCsvDataSource jRCsvDataSource = new JRCsvDataSource(new InputStreamReader(
                    getServletConfig().getServletContext().getResourceAsStream(
                    "/reports/AircraftData.csv")));

            jRCsvDataSource.setUseFirstRowAsHeader(true);

            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream,
                    new HashMap(), jRCsvDataSource);

            response.setContentType("application/pdf");
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (Exception e) {
            // display stack trace in the browser
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());
        }
    }
}
