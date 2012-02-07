package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.MarketDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MarketNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;

public class MarketDAOHibernate extends GenericDAOHibernate<Market, Long>
        implements MarketDAO {

    public MarketDAOHibernate(Class<Market> persistentClass) {
        super(persistentClass);
    }

    public Market getMarket(int id) throws MarketNotFoundException {
        Market m = (Market) getHibernateTemplate().get(Market.class, id);
        if (m == null)
            throw new MarketNotFoundException("market '" + id
                    + "' not found...");
        return m;
    }

    @SuppressWarnings("rawtypes")
    public Market getMarket(String name) throws MarketNotFoundException {
        List markets = getHibernateTemplate().find("from Market where name=?",
                name);
        if (markets == null || markets.isEmpty()) {
            throw new MarketNotFoundException("market '" + name
                    + "' not found...");
        } else {
            return (Market) markets.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Market> getMarkets() {
        return getHibernateTemplate().find(
                "from Market f order by upper(f.name)");
    }

    public void saveMarket(Market market) {
        getHibernateTemplate().saveOrUpdate(market);
        getHibernateTemplate().flush();
    }

    public void removeMarket(int id) {
        Object market = getHibernateTemplate().load(Market.class, id);
        getHibernateTemplate().delete(market);
    }

}
