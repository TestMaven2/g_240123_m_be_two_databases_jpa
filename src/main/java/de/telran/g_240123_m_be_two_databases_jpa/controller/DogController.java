package de.telran.g_240123_m_be_two_databases_jpa.controller;

import de.telran.g_240123_m_be_two_databases_jpa.domain.entity.dog.Dog;
import de.telran.g_240123_m_be_two_databases_jpa.service.DogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dogs")
public class DogController {

    private DogService service;

    public DogController(DogService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dog> getAll() {
        return service.getAll();
    }
}