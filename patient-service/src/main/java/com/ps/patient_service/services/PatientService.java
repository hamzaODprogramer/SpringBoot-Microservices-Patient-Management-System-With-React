package com.ps.patient_service.services;

import com.ps.patient_service.dto.PatientRequestDTO;
import com.ps.patient_service.dto.PatientResponseDTO;
import com.ps.patient_service.exceptions.EmailAlreadyExistException;
import com.ps.patient_service.exceptions.PatientNotFoundException;
import com.ps.patient_service.mappers.PatientMapper;
import com.ps.patient_service.models.Patient;
import com.ps.patient_service.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;


    // List of patients
    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTO = patients.stream().map(PatientMapper::toDTO).toList();
        return  patientResponseDTO;
    }

    // Create new patient
    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistException("this email "+patientRequestDTO.getEmail()+" already exist");
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO)
        );

        return PatientMapper.toDTO(newPatient);
    }

    // Update existing patient
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found")
        );

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    // Delete Patient
    public Map<String,String> deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found")
        );
        patientRepository.delete(patient);

        Map<String,String> message = new HashMap<>();
        message.put("message","patient deleted");
        return message;
    }

}
