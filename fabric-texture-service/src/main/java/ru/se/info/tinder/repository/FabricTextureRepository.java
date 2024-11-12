package ru.se.info.tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.se.info.tinder.model.FabricTexture;

@Repository
public interface FabricTextureRepository extends JpaRepository<FabricTexture, Long> {
}
