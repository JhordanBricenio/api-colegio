package com.codej.controller;


import com.codej.dto.DniRequest;
import com.codej.dto.PaymentByStudentDTO;
import com.codej.dto.StudentDTO;
import com.codej.mapper.StudentMapper;
import com.codej.model.Student;
import com.codej.service.IPaymentService;
import com.codej.service.IStudentKardexService;
import com.codej.service.IStudentService;
import com.codej.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.STUDENT_BASE;


@RestController
@RequestMapping(STUDENT_BASE)
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService studentService;
    private final StudentMapper studentMapper;
    private final IPaymentService paymentService;
    private final IUserService userService;

    @Autowired(required = false)
    private IStudentKardexService studentKardexService;

    @GetMapping
    public ResponseEntity< List<StudentDTO>> findAll() throws Exception {
        studentMapper.toStudentDTOList(studentService.findAll());
        return ResponseEntity.ok(studentMapper.toStudentDTOList(studentService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page< StudentDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Student> studentPage = studentService.findAllPaged(pageable);
        return studentPage.map(studentMapper::toStudentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> save(@Valid @RequestBody StudentDTO studentDTO) throws Exception {
        Student student= studentMapper.toStudentEntity(studentDTO);
        student.setCode("AR"+studentDTO.getUser().getDni());
        student.getUser().setPassword(studentDTO.getUser().getDni());
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentMapper.toStudentDTO(savedStudent));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<StudentDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(studentMapper.toStudentDTO(studentService.findById(id)));
    }
    @PostMapping("/dni")
    public ResponseEntity<StudentDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
        return ResponseEntity.ok(studentMapper.toStudentDTO(studentService.findByDni(dniRequest.getDni())));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<StudentDTO> update(@Valid @RequestBody StudentDTO studentDTO,@PathVariable UUID id) throws Exception {
        Student student = studentMapper.toStudentEntity(studentDTO);
        student.getUser().setPassword(studentDTO.getUser().getDni());
        Student updatedStudent = studentService.update(student, id);
        return ResponseEntity.ok(studentMapper.toStudentDTO(updatedStudent));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        studentService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/dni/{numero}")
    public ResponseEntity<String> searchByDni(@PathVariable String numero) throws Exception {
       return userService.searchByDni(numero);
    }

    @GetMapping(ID_IN_PATH + "/payments/paged/{page}")
    public Page<PaymentByStudentDTO> getPaymentsByStudent(@PathVariable UUID id, @PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        return paymentService.findPaymentsByStudent(id, pageable);
    }

    @GetMapping(ID_IN_PATH + "/kardex-professional")
    public ResponseEntity<?> getKardexByStudentId(@PathVariable UUID id) throws Exception {
        if (studentKardexService == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Kardex service not available");
        }
        return ResponseEntity.ok(studentKardexService.getKardexByStudentId(id));
    }

    @GetMapping("/kardex-by-dni")
    public ResponseEntity<?> getKardexByDni(@RequestParam String dni) throws Exception {
        if (studentKardexService == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Kardex service not available");
        }
        return ResponseEntity.ok(studentKardexService.getKardexByStudentDni(dni));
    }


}
