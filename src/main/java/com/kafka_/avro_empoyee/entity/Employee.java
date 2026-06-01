package com.kafka_.avro_empoyee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String dob;
    private String email;
}