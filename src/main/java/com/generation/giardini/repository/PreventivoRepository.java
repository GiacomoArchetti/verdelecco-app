package com.generation.giardini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.giardini.entity.preventivo.Preventivo;

public interface PreventivoRepository extends JpaRepository<Preventivo, Long> {

}
