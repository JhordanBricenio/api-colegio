package com.codej.controller;

import com.codej.dto.EducationLevelDTO;
import com.codej.emuns.EducationLevelType;
import com.codej.emuns.Shift;
import com.codej.mapper.EducationLevelMapper;
import com.codej.model.EducationLevel;
import com.codej.service.IEducationLevelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EducationLevelControllerTest {

    @Mock
    private IEducationLevelService educationLevelService;

    @Mock
    private EducationLevelMapper educationLevelMapper;

    @InjectMocks
    private EducationLevelController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_returnsList() throws Exception {
        EducationLevelDTO dto = new EducationLevelDTO();
        dto.setIdEducationLevel(UUID.randomUUID());
        dto.setName("PRIMARIA");
        dto.setShift(Shift.TARDE);
        dto.setLevelType(EducationLevelType.PRIMARY);
        dto.setStatus(true);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        dto.setIdManagement("1");

        EducationLevel entity = new EducationLevel();
        entity.setIdEducationLevel(dto.getIdEducationLevel());

        when(educationLevelService.findAll()).thenReturn(List.of(entity));
        when(educationLevelMapper.mapIn(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/education-levels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("PRIMARIA"));
    }

    @Test
    void getById_returnsDto() throws Exception {
        UUID id = UUID.randomUUID();
        EducationLevelDTO dto = new EducationLevelDTO();
        dto.setIdEducationLevel(id);
        dto.setName("PRIMARIA");
        dto.setShift(Shift.TARDE);
        dto.setLevelType(EducationLevelType.PRIMARY);
        dto.setStatus(true);
        dto.setIdManagement("1");

        EducationLevel entity = new EducationLevel();
        entity.setIdEducationLevel(id);

        when(educationLevelService.findById(id)).thenReturn(entity);
        when(educationLevelMapper.toEducationLevelDTO(entity)).thenReturn(dto);

        mockMvc.perform(get("/api/education-levels/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("PRIMARIA"));
    }

    @Test
    void create_returnsCreated() throws Exception {
        EducationLevelDTO request = new EducationLevelDTO();
        request.setName("Secundaria");
        request.setShift(Shift.TARDE);
        request.setLevelType(EducationLevelType.SECONDARY);
        request.setStatus(true);
        request.setIdManagement("1");

        EducationLevel entity = new EducationLevel();
        entity.setName("SECUNDARIA");

        EducationLevel saved = new EducationLevel();
        saved.setIdEducationLevel(UUID.randomUUID());
        saved.setName("SECUNDARIA");

        EducationLevelDTO created = new EducationLevelDTO();
        created.setIdEducationLevel(saved.getIdEducationLevel());
        created.setName("SECUNDARIA");
        created.setShift(Shift.TARDE);
        created.setLevelType(EducationLevelType.SECONDARY);
        created.setStatus(true);
        created.setIdManagement("1");

        when(educationLevelMapper.toEducationLevelEntity(any(EducationLevelDTO.class))).thenReturn(entity);
        when(educationLevelService.save(entity)).thenReturn(saved);
        when(educationLevelMapper.toEducationLevelDTO(saved)).thenReturn(created);

        mockMvc.perform(post("/api/education-levels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("SECUNDARIA"));
    }

    @Test
    void update_returnsOk() throws Exception {
        UUID id = UUID.randomUUID();
        EducationLevelDTO request = new EducationLevelDTO();
        request.setName("Secundaria Mod");
        request.setShift(Shift.TARDE);
        request.setLevelType(EducationLevelType.SECONDARY);
        request.setStatus(true);
        request.setIdManagement("1");

        EducationLevel entity = new EducationLevel();
        entity.setName("SECUNDARIA MOD");

        EducationLevel updated = new EducationLevel();
        updated.setIdEducationLevel(id);
        updated.setName("SECUNDARIA MOD");

        EducationLevelDTO updatedDto = new EducationLevelDTO();
        updatedDto.setIdEducationLevel(id);
        updatedDto.setName("SECUNDARIA MOD");
        updatedDto.setShift(Shift.TARDE);
        updatedDto.setLevelType(EducationLevelType.SECONDARY);
        updatedDto.setStatus(true);
        updatedDto.setIdManagement("1");

        when(educationLevelMapper.toEducationLevelEntity(any(EducationLevelDTO.class))).thenReturn(entity);
        when(educationLevelService.update(entity, id)).thenReturn(updated);
        when(educationLevelMapper.toEducationLevelDTO(updated)).thenReturn(updatedDto);

        mockMvc.perform(patch("/api/education-levels/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SECUNDARIA MOD"));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(educationLevelService).delete(id);

        mockMvc.perform(delete("/api/education-levels/" + id))
                .andExpect(status().isNoContent());
    }
}