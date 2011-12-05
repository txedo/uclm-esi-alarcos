package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.MarketNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;

public interface MarketDAO extends GenericDAO<Market, Long> {
    Market getMarket(int id) throws MarketNotFoundException;

    Market getMarket(String name) throws MarketNotFoundException;

    List<Market> getMarkets();

    void saveMarket(Market market);

    void removeMarket(int id);
}
