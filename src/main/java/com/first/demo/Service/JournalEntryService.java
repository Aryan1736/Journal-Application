package com.first.demo.Service;

import com.first.demo.Entity.JournalEntry;
import com.first.demo.Entity.User;
import com.first.demo.Repository.JournalEntryRepository;
import com.first.demo.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional // roll back all steps if any step fails
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUsername(username);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String username){
        try {
            User user = userService.findByUsername(username);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error Occurred " + e);
        }
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}

// controller -> service -> repository