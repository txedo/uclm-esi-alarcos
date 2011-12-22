package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.MarketDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MarketNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;

public class MarketManager {
    private static MarketDAO marketDao;

    public static List<Market> getAllMarkets() {
        return marketDao.getMarkets();
    }

    public static Market getMarket(int id) throws MarketNotFoundException {
        return marketDao.getMarket(id);
    }
    
    public void setMarketDao (MarketDAO marketDao) {
        MarketManager.marketDao = marketDao;
    }

}
