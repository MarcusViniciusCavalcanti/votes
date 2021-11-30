package com.zonework.osvaldo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {

    private String placeName;
    private String description;
    private String image;
    private Integer amountVotes = 0;
}
