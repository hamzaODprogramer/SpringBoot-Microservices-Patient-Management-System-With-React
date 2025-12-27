package com.ps.patient_service.services;

import com.ps.patient_service.dto.PatientRequestDTO;
import com.ps.patient_service.dto.PatientResponseDTO;
import com.ps.patient_service.mappers.PatientMapper;
import com.ps.patient_service.models.Patient;
import com.ps.patient_service.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    // List of patients
    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTO = patients.stream().map(PatientMapper::toDTO).toList();
        return  patientResponseDTO;
    }

    // Create new patient
    public PatientResponseDTO addPatient(PatientRequestDTO patientRequestDTO){
        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO)
        );

        return PatientMapper.toDTO(newPatient);
    }

}
