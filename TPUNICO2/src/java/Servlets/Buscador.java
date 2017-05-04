package Servlets;

import Logica.DocumentoRan;
import Logica.Gestor;
import Logica.ListaDocumentos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Buscador"})
public class Buscador extends HttpServlet {

    private ListaDocumentos resultado;
    Gestor gestor;

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String consulta;
            consulta = request.getParameter("txtConsulta");
            resultado.clear();
            gestor = new Gestor();
            gestor.rankearDocumentos(consulta);
            for (int i = 0; i < gestor.getDocumentos().length; i++) {
                System.out.println((((DocumentoRan) gestor.getDocumentos()[i])).getNombre());
            resultado.agregarDocumento(((DocumentoRan) gestor.getDocumentos()[i]).getNombre());
            }
            RequestDispatcher rd = request.getRequestDispatcher("/Listado.jsp");
            rd.include(request, response);
            
//            out.println("<html><body><h1>Gracias!!!!</h1><br><a href='Resultado'>listado</a></body></html>");

        }
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

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        resultado = new ListaDocumentos();
    }
}
