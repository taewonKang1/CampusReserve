package com.campusreserve.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.campusreserve.dao.ResourceDAO;
import com.campusreserve.dao.SlotDAO;
import com.campusreserve.dto.ResourceDTO;
import com.campusreserve.dto.SlotDTO;

public class ResourceService {
    private final ResourceDAO resourceDAO = new ResourceDAO();
    private final SlotDAO slotDAO = new SlotDAO();

    public List<ResourceDTO> getStudyRooms() throws SQLException {
        return resourceDAO.findActiveByType("STUDY_ROOM");
    }

    public List<ResourceDTO> getLockers() throws SQLException {
        return resourceDAO.findActiveByType("LOCKER");
    }

    public ResourceDTO getResource(long resourceId) throws SQLException {
        ResourceDTO resource = resourceDAO.findById(resourceId);
        if (resource == null || !"ACTIVE".equals(resource.getStatus())) {
            throw new IllegalArgumentException("사용할 수 없는 자원입니다.");
        }
        return resource;
    }

    public List<SlotDTO> getSlots(long resourceId, LocalDate date) throws SQLException {
        return slotDAO.findByResourceAndDate(resourceId, date);
    }
}
