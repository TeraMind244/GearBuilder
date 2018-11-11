
package restful.services;

import gear.GearDAO;
import gear.builder.GearSet;
import gear.builder.ListGearSet;
import gear.generated.Gear;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import util.XMLUtil;

@Path("build")
public class BuilderResource {

    @Context
    private UriInfo context;
    
    @Context
    private ServletContext servletContext;

    public BuilderResource() {
    }

    @Path("/build")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@DefaultValue("0") @QueryParam("txtMoney") int money, 
            @DefaultValue("35") @QueryParam("txtMousePercentage") int txtMousePercentage, 
            @DefaultValue("35") @QueryParam("txtKeyboardPercentage") int txtKeyboardPercentage, 
            @DefaultValue("5") @QueryParam("txtPadPercentage") int txtPadPercentage, 
            @DefaultValue("25") @QueryParam("txtHeadsetPercentage") int txtHeadsetPercentage) {
        
        try {
            if (txtMousePercentage + txtKeyboardPercentage + txtPadPercentage + txtHeadsetPercentage == 100) {
                int minMoney = (int)Math.floor(money * 0.95);
        
                GearDAO dao = new GearDAO();
                
                int mousePercentage = txtMousePercentage * money / 100;
                int keyBoardPercentage = txtKeyboardPercentage * money / 100;
                int padPercentage = txtPadPercentage * money / 100;
                int headsetPercentage = txtHeadsetPercentage * money / 100;

                List<Gear> listMouse = dao.getAllGearOfType("chuot", mousePercentage * 90 / 100, mousePercentage * 110 / 100);
                List<Gear> listKeyBoard = dao.getAllGearOfType("ban-phim", keyBoardPercentage * 90 / 100, keyBoardPercentage * 110 / 100);
                List<Gear> listPad = dao.getAllGearOfType("pad", padPercentage * 50 / 100, padPercentage * 150 / 100);
                List<Gear> listHeadset = dao.getAllGearOfType("tai-nghe", headsetPercentage * 80 / 100, headsetPercentage * 120 / 100);

                List<GearSet> listGearSets = new ArrayList<>();

                if (listMouse != null && listPad != null && listKeyBoard != null && listHeadset != null) {
                    for (Gear mouse : listMouse) {
                        for (Gear keyBoard : listKeyBoard) {
                            for (Gear pad : listPad) {
                                for (Gear headset : listHeadset) {
                                    int value = mouse.getPrice() 
                                            + keyBoard.getPrice() 
                                            + pad.getPrice() 
                                            + headset.getPrice();
                                    if (value <= money && value >= minMoney) {
                                        listGearSets.add(new GearSet(mouse, keyBoard, pad, headset));
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
                        ListGearSet listGearSet = new ListGearSet(listGearSets.subList(0, 50));

                        return XMLUtil.marshall(listGearSet);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BuilderResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error";
    }
    
    @Path("/xsl")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXSL(@QueryParam("xslFilePath") String xslFilePath) {
        String path = servletContext.getRealPath("/") + xslFilePath;
        return XMLUtil.getXSLFileAsString(path);
    }

}
