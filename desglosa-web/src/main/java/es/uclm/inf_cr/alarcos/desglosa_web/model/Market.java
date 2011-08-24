package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name="markets")
@NamedQueries ({
    @NamedQuery(
        	name = "findMostRepresentativeMarketByFactoryId",
        	query = "select m from Factory f, Subproject sp, Project p, Market m where f.id = :id and f.id = sp.factory.id and sp.project.id = p.id and p.market.id = m.id group by m.name order by count(p.id) desc limit 1"
        )
})
public class Market {
	private int id;
	@Property(type="string")
	private String name;
	@Property(type="hexcolor")
	private String color;
	
	public Market() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="color")
	public String getColor() {
		return color;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Market) {
			Market m = (Market)obj;
			if (this.id == m.getId() && this.name.equals(m.getName()) && this.color.equals(m.getColor())) equals = true;
		}
		return equals;
	}
	
}
