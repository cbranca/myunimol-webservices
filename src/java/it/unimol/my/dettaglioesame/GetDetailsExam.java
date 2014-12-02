package it.unimol.my.dettaglioesame;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Inserire descrizione classe
 *
 * @author Matteo Ianno
 */
@WebServlet(name = "GetExamInfo", urlPatterns = {"/getRecordBookExam"})
public class GetDetailsExam extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String url = "https://unimol.esse3.cineca.it/Guide/PaginaADErogata.do;jsessionid=5E36E5C2D23CFDEBC1F5992D516B1ADD.jvm_unimol_esse3web01?ad_er_id=2014*N0*N0*S1*6634*31306497&ANNO_ACCADEMICO=2014&mostra_percorsi=S";

        // ottengo l'estrattore
        ExtractorInterface extractor = DetailsExamManager.getRecordBookExtractor();

        // estraggo le informazioni
        DetailedExam detailedExam = extractor.getDetails(url, username, password);

        // converto in JSON
        Gson gson = new Gson();
        String json = gson.toJson(detailedExam);
        // stampo il json a video
        out.println(json);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
