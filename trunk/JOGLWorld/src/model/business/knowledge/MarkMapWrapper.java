package model.business.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MarkMapWrapper {
	@XmlElement ( name="mark" )
	private List<Mark> innerList;

	public MarkMapWrapper() {
		this.innerList = new ArrayList<Mark>();
	}
	
	public void addMark (Mark m) {
		this.innerList.add(m);
	}

	public List getInnerList() {
		return innerList;
	}

	public void setInnerList(List innerList) {
		this.innerList = innerList;
	}
	
}
