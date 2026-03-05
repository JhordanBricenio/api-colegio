package com.codej.service;


import com.codej.model.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IParentService extends ICRUDService<Parent, UUID> {

    Parent saveParent(Parent user) throws  Exception;

    Parent findByDni(String dni) throws Exception;

    Page<Parent> findAllPaged(Pageable pageable) throws Exception;

}
