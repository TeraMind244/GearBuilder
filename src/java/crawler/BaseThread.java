
package crawler;

public class BaseThread extends Thread {
    
    protected BaseThread() {
    }
    
    private static BaseThread instance;
    private final static Object LOCK = new Object();
    
    public static BaseThread getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BaseThread();
            }
        }
        return instance;
    }
    
    private static boolean suspended = false;
    public static boolean isSuspended() {
        return suspended;
    }
    public static void setSuspended(boolean aSuspended) {
        suspended = aSuspended;
    }
    
    public void suspendThread() {
        setSuspended(true);
        System.out.println("Suspended!!!");
    }
    
    public synchronized void resumeThread() {
        setSuspended(false);
        notifyAll();
        System.out.println("Resumed!!!");
    }
    
}
