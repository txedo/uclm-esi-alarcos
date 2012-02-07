package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.control.MarketManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Market;
import es.uclm.inf_cr.alarcos.desglosa_web.util.Utilities;

public class MarketAction extends ActionSupport {
    private static final long serialVersionUID = -7444770465628489923L;
    int id;
    Market market;
    List<Market> markets;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * @return the markets
     */
    public List<Market> getMarkets() {
        return markets;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param market the market to set
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * @param markets the markets to set
     */
    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }

    @Override
    public String execute() throws Exception {
        markets = MarketManager.getAllMarkets();
        return SUCCESS;
    }
    
    public void validateDoGet() {
        try {
            Utilities.checkValidId(ServletActionContext.getRequest().getParameter("id"));
        } catch (NullIdParameterException e) {
            addActionError(getText("error.general"));
        } catch (NotValidIdParameterException e) {
            addActionError(getText("error.general"));
        }
    }
    
    public String get() throws Exception {
        market = MarketManager.getMarket(id);
        return SUCCESS;
    }
    
}
