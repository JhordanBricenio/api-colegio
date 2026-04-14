package com.codej.controller;


import com.codej.dto.SettingDTO;
import com.codej.mapper.SettingMapper;
import com.codej.model.Setting;
import com.codej.service.ISettingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(SETTING_BASE)
@AllArgsConstructor
public class SettingController {

    private final ISettingService settingService;
    private final SettingMapper settingMapper;


    @GetMapping
    public ResponseEntity< List<SettingDTO>> findAll() throws Exception {
        return ResponseEntity.ok(settingMapper.toSettingDTOList(settingService.findAll()));
    }
    @PostMapping
    public ResponseEntity<SettingDTO> save(@Valid @RequestBody SettingDTO settingDTO) throws Exception {
        Setting setting= settingMapper.toSettingEntity(settingDTO);
        Setting savedSetting = settingService.save(setting);
        return ResponseEntity.status(HttpStatus.CREATED).body(settingMapper.toSettingDTO(savedSetting));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<SettingDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(settingMapper.toSettingDTO(settingService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<SettingDTO> update(@Valid @RequestBody SettingDTO settingDTO, @PathVariable UUID id) throws Exception {
        Setting setting = settingMapper.toSettingEntity(settingDTO);
        Setting updatedSetting = settingService.update(setting, id);
        return ResponseEntity.ok(settingMapper.toSettingDTO(updatedSetting));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        settingService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
