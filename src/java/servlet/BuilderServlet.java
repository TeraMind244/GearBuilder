
package servlet;

import gear.GearDAO;
import gear.GearSet;
import generated.gear.Gear;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.PathLookup;

public class BuilderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String url = PathLookup.BUILDER_PAGE;
        
        String moneyParam = request.getParameter("txtMoney");
        
        try {
            int money = moneyParam == null ? 0 : Integer.parseInt(moneyParam.trim());

            int mousePercentage = 35 * money / 100;
            int keyBoardPercentage = 35 * money / 100;
            int padPercentage = 5 * money / 100;
            int headsetPercentage = 25 * money / 100;
            
            int minMoney = (int)Math.floor(money * 0.98);
            
            GearDAO dao = new GearDAO();
            
            List<Gear> listMouse = dao.getAllGearOfType("chuot", mousePercentage * 90 / 100, mousePercentage * 110 / 100);
            List<Gear> listKeyBoard = dao.getAllGearOfType("ban-phim", keyBoardPercentage * 90 / 100, keyBoardPercentage * 110 / 100);
            List<Gear> listPad = dao.getAllGearOfType("pad", padPercentage * 50 / 100, padPercentage * 150 / 100);
            List<Gear> listHeadset = dao.getAllGearOfType("tai-nghe", headsetPercentage * 80 / 100, headsetPercentage * 110 / 100);
            
//            List<Gear> listPad = listMouse;
//            List<Gear> listKeyBoard = listMouse;
//            List<Gear> listHeadset = listMouse;
            
            List<GearSet> listGearSets = new ArrayList<>();
            
            if (listMouse != null && listPad != null && listKeyBoard != null && listHeadset != null) {
                for (Gear mouse : listMouse) {
                    for (Gear keyBoard : listKeyBoard) {
                        for (Gear pad : listPad) {
                            for (Gear headset : listHeadset) {
//                                if (listGearSets.size() >= 50) {
//                                    break;
//                                }
                                int value = mouse.getPrice() 
                                        + pad.getPrice() 
                                        + keyBoard.getPrice() 
                                        + headset.getPrice();
                                if (value <= money && value >= minMoney) {
                                    listGearSets.add(new GearSet(mouse, pad, keyBoard, headset));
                                }
                            }
                        }
                    }
                }
                if (!listGearSets.isEmpty()) {
                    listGearSets.sort(new Comparator<GearSet>() {
                        @Override
                        public int compare(GearSet o1, GearSet o2) {
                            return o1.getValue() - o2.getValue();
                        }
                    });
                    Collections.reverse(listGearSets);
                    request.setAttribute("GEARSETS", listGearSets.subList(0, 50));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BuilderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
