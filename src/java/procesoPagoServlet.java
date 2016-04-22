/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import pagar.Pagos_Service;

/**
 *
 * @author camilo
 */
@WebServlet(urlPatterns = {"/procesoPagoServlet"})
public class procesoPagoServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/webserpagos/pagos.wsdl")
    private Pagos_Service service;

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
            
            if (!request.getParameter("valor").equalsIgnoreCase("")) {

                String msj = "";
                String name = request.getParameter("nombre");
                String acc = request.getParameter("cuenta");
                int cost = Integer.parseInt(request.getParameter("valor"));

                msj = operation(name, acc, cost);

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Confirmación</title>");
                out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.css\">");
                out.println("<script defer src=\"js/bootstrap.min.js\"></script>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"container\">");
                if (!msj.equalsIgnoreCase("")) {
                    out.println("<h1 class=\"page-header\">" + msj + "</h1>");
                }
                out.println("<a href='index.jsp' class=\"btn btn-primary btn-block\">Ir a la página principal</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
                out.close();
            } else {
                response.sendRedirect("index.jsp");
            }
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

    private String operation(java.lang.String nombre, java.lang.String cuenta, int valor) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        pagar.Pagos port = service.getPagosPort();
        return port.operation(nombre, cuenta, valor);
    }

}
