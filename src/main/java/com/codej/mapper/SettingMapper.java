package com.codej.mapper;

import com.codej.dto.SettingDTO;
import com.codej.model.Setting;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SettingMapper {


   SettingDTO toSettingDTO(Setting post);

    Setting toSettingEntity(SettingDTO settingDTO);

    List<SettingDTO> toSettingDTOList(List<Setting> posts);






}
