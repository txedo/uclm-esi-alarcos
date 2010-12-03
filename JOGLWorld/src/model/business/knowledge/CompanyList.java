package model.business.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement ( name="companies" )
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyList {
	@XmlElement ( name="company" ) private List<Company> companyList = new ArrayList<Company>();
	
	public void addCompany (Company company) {
		companyList.add(company);
	}
	
	public List<Company> getCompanyList () {
		return companyList;
	}
	
	public int getLastId () {
		int lastId = 0;
		if (companyList.size() != 0)
			lastId = ((Company)companyList.get(companyList.size()-1)).getId();
		return ++lastId;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer();
        sb.append( " companyList\n");
        for (Company c : companyList) {
        	sb.append( "       " + c + "\n");
        }
		return sb.toString();
	}
}
