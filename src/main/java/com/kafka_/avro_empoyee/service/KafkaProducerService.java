package com.kafka_.avro_empoyee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kafka_.avro_empoyee.avro.EmployeeAvro;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, EmployeeAvro> kafkaTemplate;

    @Value("${app.kafka.topic.employee-mutations}")
    private String topicName;

    public void sendEmployeeEvent(EmployeeAvro employeeAvro) {
        log.info("Publishing Avro event payload for employee ID: {}", employeeAvro.getId());
        
        CompletableFuture<SendResult<String, EmployeeAvro>> future = 
                kafkaTemplate.send(topicName, employeeAvro.getId().toString(), employeeAvro);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Successfully produced message to topic {} partitions metadata: offset {}", 
                        topicName, result.getRecordMetadata().offset());
            } else {
                log.error("Failed to route Avro message execution plan downstream", ex);
            }
        });
    }
}
