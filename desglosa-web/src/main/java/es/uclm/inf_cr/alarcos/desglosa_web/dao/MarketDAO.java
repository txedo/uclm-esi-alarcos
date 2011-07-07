package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.MarketNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;

public interface MarketDAO extends GenericDAO<Market, Long> {
	
	public Market getMarket(int id) throws MarketNotFoundException;
	
	public Market getMarket(String name) throws MarketNotFoundException;
	
	public List<Market> getMarkets();
	
	public void saveMarket(Market market);
	
	public void removeMarket(int id);
}
