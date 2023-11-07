package de.telran.g_240123_m_be_two_databases_jpa.repository.cat;

import de.telran.g_240123_m_be_two_databases_jpa.domain.entity.cat.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Integer> {
}