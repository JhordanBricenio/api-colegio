package com.codej.service;


import com.codej.model.Setting;

import java.util.UUID;

public interface ISettingService extends ICRUDService<Setting, UUID> {
    Setting save(Setting post) throws  Exception;
}
