package com.iocl.dispatchapplication.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iocl.dispatchapplication.model.Unit;
import com.iocl.dispatchapplication.repository.UnitRepository;

@Service
@Transactional
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Optional<Unit> getUnitById(String unitId) {
        return unitRepository.findById(unitId);
    }

    public Unit createUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public Unit updateUnit(String unitId, Unit unitDetails) {
        Unit unit = unitRepository.findById(unitId)
                                   .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));

        unit.setName(unitDetails.getName());

        return unitRepository.save(unit);
    }

    public void deleteUnit(String unitId) {
        Unit unit = unitRepository.findById(unitId)
                                   .orElseThrow(() -> new RuntimeException("Unit not found with id: " + unitId));

        unitRepository.delete(unit);
    }
}

