
package restful.services;

import gear.GearDAO;
import gear.search.GearFilter;
import gear.search.SearchGearView;
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

@Path("search")
public class SearchResource {

    @Context
    private UriInfo context;
    
    @Context
    private ServletContext servletContext;

    public SearchResource() {
    }

    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@DefaultValue("") @QueryParam("txtGearName") String nameParam, 
            @DefaultValue("all") @QueryParam("ddlType") String type, 
            @DefaultValue("nameAsc") @QueryParam("ddlSortBy") String sortBy, 
            @DefaultValue("0") @QueryParam("page") int page) {
        try {
            String name = nameParam.trim();

            GearFilter filter = new GearFilter(name, type, sortBy);
            
            if (page >= 0) {
                GearDAO dao = new GearDAO();
                SearchGearView gearsView = dao.getAllGear(filter, page);

                return XMLUtil.marshall(gearsView);
            }
        } catch (NumberFormatException ex) {
            Logger.getLogger(SearchResource.class.getName()).log(Level.SEVERE, null, ex);
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
