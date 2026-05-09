package com.example.demo.service;

import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public String register(User user) {

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing != null) {
            return "Email already exists";
        }

        User savedUser = userRepository.save(user);

        if (savedUser.isTrainer()) {
            Trainer trainerProfile = new Trainer();
            trainerProfile.setUser(savedUser);
            trainerProfile.setEmail(savedUser.getEmail());
            trainerProfile.setFirstname(savedUser.getFirstName());
            trainerProfile.setLastname(savedUser.getLastName());
            trainerProfile.setUsername(savedUser.getEmail());

            trainerRepository.save(trainerProfile);
        }

        return "success";
    }

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getTraineesForTrainer(User trainer) {
        return userRepository.findByTrainerFalseAndSelectedTrainer(trainer);
    }

    public void chooseTrainer(User trainee, User trainer) {
        if (trainee == null || trainer == null || trainee.isTrainer() || !trainer.isTrainer()) {
            return;
        }

        trainee.setSelectedTrainer(trainer);
        userRepository.save(trainee);
    }

    public boolean isTraineeAssignedToTrainer(User trainee, User trainer) {
        if (trainee == null || trainer == null || trainee.isTrainer() || !trainer.isTrainer()) {
            return false;
        }

        User selectedTrainer = trainee.getSelectedTrainer();

        return selectedTrainer != null && selectedTrainer.getId().equals(trainer.getId());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
