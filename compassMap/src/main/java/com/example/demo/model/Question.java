package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    private String id;
    private String name;
    private String text;
    @Enumerated(EnumType.STRING)
    private Dimension dimension;
    // order of the question
    // order is a reserved keyword in H2,
    // so Hibernate's generated DDL will fail at: order integer not null.
    @Column(name = "question_order")
    private int order;
    private double weight;

    public String toString() {
        return String.format(
                "Question[id=%s, name='%s', text='%s', dimension='%s', order='%s', weight='%s']",
                id, name, text, dimension, order, weight);
    }
}
