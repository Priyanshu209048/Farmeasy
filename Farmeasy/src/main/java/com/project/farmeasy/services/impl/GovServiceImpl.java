package com.project.farmeasy.services.impl;

import com.project.farmeasy.dao.GrievencesDao;
import com.project.farmeasy.entities.Grievences;
import com.project.farmeasy.exceptions.ResourceNotFoundException;
import com.project.farmeasy.services.GovService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GovServiceImpl implements GovService {

    private final GrievencesDao grievencesDao;

    @Override
    public Grievences getGrievences(Integer id) {
        return grievencesDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("Grievences", "id", String.valueOf(id)));
    }
}
