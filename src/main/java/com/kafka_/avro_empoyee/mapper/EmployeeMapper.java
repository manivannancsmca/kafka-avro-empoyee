package com.kafka_.avro_empoyee.mapper;

import com.kafka_.avro_empoyee.avro.EmployeeAvro;
import com.kafka_.avro_empoyee.dto.EmployeeDTO;
import com.kafka_.avro_empoyee.entity.Employee;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(EmployeeDTO dto);
    EmployeeDTO toDto(Employee entity);
    EmployeeAvro toAvro(Employee entity);
    Employee toEntity(EmployeeAvro avro);
}
