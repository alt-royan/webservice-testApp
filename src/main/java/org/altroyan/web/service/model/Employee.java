package org.altroyan.web.service.model;

import lombok.Data;

import javax.persistence.*;


/**
 * Модель сотрудника в базе
 */

@Entity
@Table(name = "Employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "Phone")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Department_Id", referencedColumnName = "id")
    private Department department;
}
