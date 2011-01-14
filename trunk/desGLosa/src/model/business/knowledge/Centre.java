package model.business.knowledge;

public class Centre {
	private boolean isMainCentre;
	private int idCompany;
	private int idFactory;
	
	/**
	 * @param idCompany
	 * @param idFactory
	 */
	public Centre(int idCompany, int idFactory) {
		this.isMainCentre = false;
		this.idCompany = idCompany;
		this.idFactory = idFactory;
	}
	
	public int getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}
	public int getIdFactory() {
		return idFactory;
	}
	public void setIdFactory(int idFactory) {
		this.idFactory = idFactory;
	}

	public boolean isMainCentre() {
		return isMainCentre;
	}

	public void setMainCentre(boolean isMainCentre) {
		this.isMainCentre = isMainCentre;
	}
	
	
}
