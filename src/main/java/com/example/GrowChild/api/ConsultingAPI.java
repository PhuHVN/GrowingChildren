package com.example.GrowChild.api;

import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.request.ConsultingRequest;
import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.service.ConsultingSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultingAPI")
public class ConsultingAPI {
    @Autowired
    ConsultingSevice consultingSevice;

    @PostMapping("createConsulting")
    public ResponseEntity createConsulting(@Valid @RequestBody ConsultingRequest consultingRequest, @RequestParam String doctorId,
                                           @RequestParam String parentId, @RequestParam long childId, @RequestParam long bookingId) {
        Consulting consulting = consultingSevice.createConsulting(consultingRequest, doctorId, parentId, childId, bookingId);
        return new ResponseEntity<>(consulting, HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public List<ConsultingDTO> getAllConsulting() {
        return consultingSevice.getAll();
    }

    @GetMapping("getConsultingById/{consulting_id}")
    public ConsultingDTO getConsultingById(@PathVariable long consulting_id) {
        return consultingSevice.getConsultingById(consulting_id);
    }

    @PutMapping("updateConsulting/{consulting_id}")
    public ConsultingDTO updateConsulting(@PathVariable long consulting_id,
                                          @RequestBody Consulting consulting) {
        return consultingSevice.updateConsulting(consulting_id, consulting);
    }

    @DeleteMapping("deleteConsulting/{consulting_id}")
    public String deleteConsulting(@RequestParam long consulting_id) {
        return consultingSevice.deleteConsulting(consulting_id);
    }
}
