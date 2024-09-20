package com.example.lab4_20210779.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.lab4_20210779.entity.Flor;

public interface FlorRepository extends JpaRepository<Flor, Integer> {
    // Método para filtro personalizado
    List<Flor> findByColorAndTipoAndOcasion(String color, String tipo, String ocasion);
}

