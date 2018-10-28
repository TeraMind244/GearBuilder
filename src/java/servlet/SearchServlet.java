
package servlet;

import gear.GearDAO;
import gear.GearFilter;
import generated.gear.Gear;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String url = "search.jsp";
        
        String nameParam = request.getParameter("txtGearName");
        String typeParam = request.getParameter("ddlType");
        String sortByParam = request.getParameter("ddlSortBy");
        
        try {
            String name = nameParam == null ? "" : nameParam.trim();
            String type = typeParam == null ? "all" : typeParam;
            String sortBy = sortByParam == null ? "" : sortByParam;

            GearFilter filter = new GearFilter(name, type, sortBy);
            
            GearDAO dao = new GearDAO();
            List<Gear> gears = dao.getAllGear(filter);
            
            if (gears != null) {
                int gearCount = gears.size();
                request.setAttribute("GEARS", gears);
                request.setAttribute("GEARCOUNT", gearCount);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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

}
