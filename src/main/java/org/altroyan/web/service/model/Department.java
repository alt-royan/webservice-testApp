package org.altroyan.web.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.persistence.*;


/**
 * Модель отдела в базе
 */

@Entity
@Table(name = "Department")
@Data
public class Department {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Department")
    @Enumerated(EnumType.STRING)
    private Type departmentType;

    public enum Type {

        ACCOUNTING("ACCOUNTING"),

        CUSTOMER("CUSTOMER"),

        DEVELOPMENT("DEVELOPMENT"),

        LEGAL("LEGAL");

        private String value;

        Type(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static Type fromValue(String value) {
            for (Type b : Type.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}
