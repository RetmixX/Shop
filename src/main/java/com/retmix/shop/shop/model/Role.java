package com.retmix.shop.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "shop", name = "roles")
@Data
public class Role {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
