package com.myencurter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urls")
public class Url {

    @Id
    private String id;

    @NotEmpty
    private String url;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
