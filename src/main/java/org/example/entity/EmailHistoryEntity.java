package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity extends BaseEntity{

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "email")
    private String email;

}
