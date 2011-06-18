package model.util;

public class Synchronizer {
	static private Synchronizer _instance = null;
	
	/**
	 * @return The unique instance of this class.
	 */
	static public Synchronizer getInstance() {
		if (null == _instance) _instance = new Synchronizer();
		return _instance;
	}
	
	private boolean ready;
	
	public Synchronizer () {
		this.ready = false;
	}

	public synchronized void solicitar() {
		try {
			if (!ready) {
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void conceder() {
		ready = true;
		notifyAll();
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	
}
