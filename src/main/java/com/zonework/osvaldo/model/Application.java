package com.zonework.osvaldo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "application")
public class Application {

    @Id
    private long id;

    @Column(name = "value")
    private Boolean value;
}
