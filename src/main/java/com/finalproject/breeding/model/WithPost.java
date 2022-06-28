package com.finalproject.breeding.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class WithPost {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @ManyToOne
    private BoardMain boardMain;

    @JoinColumn(name = "WITHPOSTCATEGORY_ID")
    @ManyToOne
    private WhitPostCategory whitPostCategory;

    @JoinColumn(name = "PROVINCEAREA_ID")
    @ManyToOne
    private ProvinceAreas provinceAreas;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String date;

    @Column
    @NotNull
    private Long limitPeople;

    @Column
    @NotNull
    private String location;

    @Column
    @NotNull
    private Long peopleCnt;
}
