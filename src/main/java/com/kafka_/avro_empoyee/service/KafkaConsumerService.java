package com.kafka_.avro_empoyee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafka_.avro_empoyee.avro.EmployeeAvro;
import com.kafka_.avro_empoyee.entity.Employee;
import com.kafka_.avro_empoyee.mapper.EmployeeMapper;
import com.kafka_.avro_empoyee.repository.EmployeeRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    @KafkaListener(topics = "${app.kafka.topic.employee-mutations}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEmployeeEvent(ConsumerRecord<String, EmployeeAvro> record) {
        log.info("Received Kafka Avro message payload: Key = {}, Value = {}", record.key(), record.value());
        
        EmployeeAvro avroPayload = record.value();
        Employee employee = employeeMapper.toEntity(avroPayload);
        
        employeeRepository.save(employee);
        log.info("Successfully persisted processing engine data model state changes for ID: {}", employee.getId());
    }
}
