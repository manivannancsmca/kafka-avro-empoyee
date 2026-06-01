package com.kafka_.avro_empoyee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.kafka_.avro_empoyee.dto.EmployeeDTO;
import com.kafka_.avro_empoyee.entity.Employee;
import com.kafka_.avro_empoyee.exception.ResourceNotFoundException;
import com.kafka_.avro_empoyee.mapper.EmployeeMapper;
import com.kafka_.avro_empoyee.repository.EmployeeRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final KafkaProducerService kafkaProducerService;
    private final EmployeeMapper employeeMapper;

    public EmployeeDTO processEmployeeCreation(EmployeeDTO dto) {
        dto.setId(UUID.randomUUID().toString());
        Employee employee = employeeMapper.toEntity(dto);
        
        // Push raw structural event message onto downstream bus pipeline
        kafkaProducerService.sendEmployeeEvent(employeeMapper.toAvro(employee));
        
        return employeeMapper.toDto(employee);
    }

    public EmployeeDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee target missing database matching key record: " + id));
        return employeeMapper.toDto(employee);
    }
}
