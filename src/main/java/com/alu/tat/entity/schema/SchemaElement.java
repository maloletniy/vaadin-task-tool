package com.alu.tat.entity.schema;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by
 * User: vkhodyre
 * Date: 7/8/2015
 */
@Embeddable
public class SchemaElement {
    @Enumerated(EnumType.STRING)
    private ElemType type = ElemType.BOOLEAN;

    @Column(name = "name")
    private String name = "No name";

    @Column(name = "desc")
    private String description = "No description";

    @Column(name = "multiplier")
    private Integer multiplier = 1;

    public SchemaElement() {

    }

    public SchemaElement(String name, String desc, SchemaElement.ElemType type, Integer multi) {
        this.name = name;
        this.description = desc;
        this.type = type;
        this.multiplier = multi;
    }

    public enum ElemType {
        BOOLEAN("BOOLEAN"), STRING("STRING"), INTEGER("INTEGER"), DOMAIN("DOMAIN");

        private String type;

        ElemType(String type) {
            this.type = type;
        }

        public String getElemType() {
            return type;
        }
    }

    public ElemType getType() {
        return type;
    }

    public void setType(ElemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }
}
