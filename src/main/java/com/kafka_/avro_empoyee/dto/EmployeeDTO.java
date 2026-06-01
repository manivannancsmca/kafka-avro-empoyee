package com.kafka_.avro_empoyee.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {
    private String id;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;
    @NotBlank(message = "DOB is mandatory")
    private String dob;
    @Email(message = "Valid email is mandatory")
    private String email;
}