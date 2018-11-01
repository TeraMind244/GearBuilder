
package gear;

import generated.gear.Gear;
import util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import util.AppConstant;

public class GearDAO implements Serializable {
    
    private Session session;
    private static GearDAO instance;
    private final static Object LOCK = new Object();

    public GearDAO() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
    public static GearDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new GearDAO();
            }
        }
        return instance;
    }
    
    private void beginTransaction() {
        synchronized (LOCK) {
            if (!session.isOpen()) {
                session = HibernateUtil.getSessionFactory().openSession();
            }
        }
    }
    
    public void saveGear(Gear newGear) {
        Gear gear = getGearByHashStr(newGear.getHashStr());
        if (gear == null) {
            addGear(newGear);
            System.out.println("Add " + newGear.getType() + ": " + newGear.getGearName());
        } else {
            updateGear(newGear);
            System.out.println("Update " + newGear.getType() + ": " + newGear.getGearName());
        }
    }
    
    public synchronized void addGear(Gear gear) {
        try {
            beginTransaction();
            session.getTransaction().begin();
            session.save(gear);
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(GearDAO.class.getName()).log(Level.SEVERE, "Add Gear ERROR", ex);
        } catch (Exception ex) {
            
        }
    }
    
    public synchronized Gear getGearByHashStr(int hashStr) {
        try {
            session.getTransaction().begin();
            String sql = "FROM Gear WHERE HashStr = :hashStr";
            Query query = session.createQuery(sql);
            query.setParameter("hashStr", hashStr + "");
            Gear gear = (Gear) query.uniqueResult();
            session.flush();
            session.getTransaction().commit();
            return gear;
        } catch (HibernateException ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(GearDAO.class.getName()).log(Level.SEVERE, "Get Gear ERROR", ex);
        } catch (Exception ex) {
            
        }
        return null;
    }
    
    public synchronized void updateGear(Gear gear) {
        try {
            beginTransaction();
            session.getTransaction().begin();
            Gear oldGear = (Gear) session.get(Gear.class, gear.getHashStr());
            oldGear.setGearUrl(gear.getGearUrl());
            oldGear.setImgUrl(gear.getImgUrl());
            oldGear.setPrice(gear.getPrice());
            oldGear.setType(gear.getType());
            session.update(oldGear);
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(GearDAO.class.getName()).log(Level.SEVERE, "Update Gear ERROR", ex);
        } catch (Exception ex) {
            
        }
    }
    
    public SearchGearView getAllGear(GearFilter filter, int page) {
        SearchGearView searchGearView = new SearchGearView();
        searchGearView.setCurrentPage(page);
        try {
            beginTransaction();
            session.getTransaction().begin();
            String sql = "FROM Gear " + filter.getQueryStatement();
            Query query = session.createQuery(sql);
            
            String name = filter.getName();
            String type = filter.getType();
            
            if (!name.isEmpty()) {
                query.setParameter("name", "%" + name + "%");
            }

            if (!type.equals("all")) {
                query.setParameter("type", type);
            }
            searchGearView.setResultCount(query.list().size());
            
            query.setFirstResult(page * AppConstant.pageSize);
            query.setMaxResults(AppConstant.pageSize);
            
            searchGearView.setGears(query.list());
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(GearDAO.class.getName()).log(Level.SEVERE, "Get Gear ERROR", ex);
        }
        return searchGearView;
    }
    
    public List<Gear> getAllGearOfType(String type, int minPrice, int maxPrice) {
        List<Gear> gears = new ArrayList<>();
        try {
            beginTransaction();
            session.getTransaction().begin();
            
            String sql = "FROM Gear WHERE Type = :type AND PRICE BETWEEN :minPrice and :maxPrice";
            Query query = session.createQuery(sql);
            
            query.setParameter("type", type);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            
            gears = query.list();
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(GearDAO.class.getName()).log(Level.SEVERE, "Get Gear Type ERROR", ex);
        }
        if (gears.isEmpty()) {
            return null;
        }
        return gears;
    }
    
}
