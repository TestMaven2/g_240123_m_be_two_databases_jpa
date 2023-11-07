package de.telran.g_240123_m_be_two_databases_jpa.repository.dog;

import de.telran.g_240123_m_be_two_databases_jpa.domain.entity.dog.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Integer> {
}