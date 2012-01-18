package model.util;

import java.util.logging.Logger;

public class Synchronizer {
    private final static Logger log = Logger.getAnonymousLogger();
    static private Synchronizer _instance = null;

    /**
     * @return The unique instance of this class.
     */
    static public Synchronizer getInstance() {
        if (null == _instance)
            _instance = new Synchronizer();
        return _instance;
    }

    private boolean ready;

    public Synchronizer() {
        this.ready = false;
    }

    public synchronized void solicitar() {
        log.info("A thread ask for permission to use GLDrawer");
        try {
            if (!ready) {
                log.warning("GLDrawer is not ready yet");
                wait();
                log.info("Permission to use GLDrawer granted.");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized void conceder() {
        ready = true;
        log.info("GLDrawer is now ready");
        notifyAll();
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

}
