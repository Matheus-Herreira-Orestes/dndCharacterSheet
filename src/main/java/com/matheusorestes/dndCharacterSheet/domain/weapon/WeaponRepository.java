package com.matheusorestes.dndCharacterSheet.domain.weapon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusorestes.dndCharacterSheet.domain.catalog.CatalogSource;

public interface WeaponRepository extends JpaRepository<Weapon, String>{
    List<Weapon> findByIspublicTrueOrCreatedById(String userId);
    List<Weapon> findBySource(CatalogSource source);
    boolean existsBySource(CatalogSource source);
}
