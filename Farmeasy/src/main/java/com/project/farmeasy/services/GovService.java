package com.project.farmeasy.services;

import com.project.farmeasy.entities.Apply;
import com.project.farmeasy.entities.Grievences;

public interface GovService {

    Grievences getGrievences(Integer id);
    void updateGrievences(Grievences grievences, String status, String review);

}
