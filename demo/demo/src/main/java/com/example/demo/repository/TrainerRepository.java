package com.example.demo.repository;

import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Trainer findByUser(User user);
}
