package ru.se.info.tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.se.info.tinder.model.SpacesuitData;

import java.util.List;

@Repository
public interface SpacesuitDataRepository extends JpaRepository<SpacesuitData, Long> {
    List<SpacesuitData> findAllUserSpacesuitDataByOwnerUserUsername(String username);
}
