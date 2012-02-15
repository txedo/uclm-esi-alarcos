package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "measures")
public class Measure {
    public final static String COMPANY_ENTITY = "Company";
    public final static String COMPANY_TABLE = "companies";
    public final static String FACTORY_ENTITY = "Factory";
    public final static String FACTORY_TABLE = "factories";
    public final static String PROJECT_ENTITY = "Project";
    public final static String PROJECT_TABLE = "projects";
    public final static String SUBPROJECT_ENTITY = "Subproject";
    public final static String SUBPROJECT_TABLE = "subprojects";
    public final static String MARKET_ENTITY = "Market";
    public final static String MARKET_TABLE = "markets";
    
    public final static String FLOAT = "float";
    public final static String INTEGER = "int";
    public final static String STRING = "string";
    public final static String BOOLEAN = "boolean";
    
    private final static String FLOAT_COLUMN = "float";
    private final static String INTEGER_COLUMN = "integer";
    private final static String STRING_COLUMN = "varchar(255)";
    private final static String BOOLEAN_COLUMN = "tinyint";
    
    private int id;
    private String entity;
    private String dbTable;
    private String name;
    private String type;
    
    public Measure () {
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @Column
    public String getEntity() {
        return entity;
    }
    
    @Column
    public String getDbTable() {
        return dbTable;
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public String getType() {
        return type;
    }
    
    @Transient
    public String getColumnType() {
        String columnType = "";
        if (this.type.equals(Measure.FLOAT)) {
            columnType = FLOAT_COLUMN;
        } else if (this.type.equals(Measure.INTEGER)) {
            columnType = INTEGER_COLUMN;
        } else if (this.type.equals(Measure.STRING)) {
            columnType = STRING_COLUMN;
        } else if (this.type.equals(Measure.BOOLEAN)) {
            columnType = BOOLEAN_COLUMN;
        }
        return columnType;
    }
    
    @Transient
    public Object getDefaultValue() {
        Object defaultValue = null;
        if (this.type.equals(Measure.FLOAT)) {
            defaultValue = new Float(0.0f);
        } else if (this.type.equals(Measure.INTEGER)) {
            defaultValue = new Integer(0);
        } else if (this.type.equals(Measure.STRING)) {
            defaultValue = new String("");
        } else if (this.type.equals(Measure.BOOLEAN)) {
            defaultValue = new Boolean(false);
        }
        return defaultValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public void setDbTable(String table) {
        this.dbTable = table;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
