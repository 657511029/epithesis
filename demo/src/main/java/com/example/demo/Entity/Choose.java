package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Choose {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String chooseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChooseName() {
        return chooseName;
    }

    public void setChooseName(String chooseName) {
        this.chooseName = chooseName;
    }
}
