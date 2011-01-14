package model.business.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement ( name="companies" )
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyWrapper {
	@XmlElement ( name="company" , required=false ) private List<Company> innerList = new ArrayList<Company>();
	
	public void addCompany (Company company) {
		innerList.add(company);
	}
	
	public void addAllCompanies (List<Company> c) {
		innerList.addAll(c);
	}
	
	public List<Company> getInnerList () {
		return innerList;
	}
	
	public int getLastId () {
		int lastId = 0;
		if (innerList.size() != 0)
			lastId = ((Company)innerList.get(innerList.size()-1)).getId();
		return ++lastId;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer();
        sb.append( " companyList\n");
        for (Company c : innerList) {
        	sb.append( "       " + c + "\n");
        }
		return sb.toString();
	}
}
