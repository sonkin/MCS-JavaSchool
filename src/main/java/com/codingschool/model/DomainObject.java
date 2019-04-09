package com.codingschool.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class DomainObject {

    @Id
    private int id;

    private Date lastUpdated;

}
