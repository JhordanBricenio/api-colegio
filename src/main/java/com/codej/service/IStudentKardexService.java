package com.codej.service;

import com.codej.dto.KardexProfessionalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IStudentKardexService {
    KardexProfessionalDTO getKardexByStudentId(UUID studentId) throws Exception;
    KardexProfessionalDTO getKardexByStudentDni(String dni) throws Exception;
}
