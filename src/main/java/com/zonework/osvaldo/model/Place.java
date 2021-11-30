package com.zonework.osvaldo.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "place")
public class Place {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToMany(
        mappedBy = "place",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Votes> votesList;

    public void addVote(Votes votes) {
        votesList.add(votes);
        votes.setPlace(this);
    }
}
