
package gear;

import generated.gear.Gear;
import util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

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
            System.out.println("Add gear: " + newGear.getGearName());
        } else {
            updateGear(newGear);
            System.out.println("Update gear: " + newGear.getGearName());
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
            session.update(gear);
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
    
    public List<Gear> getAllGear(String type) {
        List<Gear> gears = null;
        try {
            beginTransaction();
            session.getTransaction().begin();
            String sql = "FROM Gear" + (type.equals("all") ? "" : " WHERE Type = :type");
            Query query = session.createQuery(sql);
            if (!type.equals("all")) {
                query.setParameter("type", type);
            }
            gears = query.list();
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(GearDAO.class.getName()).log(Level.SEVERE, "Get Gear ERROR", ex);
        } catch (Exception ex) {
            
        }
        return gears;
    }
    
}
