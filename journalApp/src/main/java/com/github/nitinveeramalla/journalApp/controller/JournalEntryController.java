package com.github.nitinveeramalla.journalApp.controller;

import com.github.nitinveeramalla.journalApp.entity.JournalEntry;
import com.github.nitinveeramalla.journalApp.entity.User;
import com.github.nitinveeramalla.journalApp.service.JournalEntryService;
import com.github.nitinveeramalla.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@PathVariable String userName, @RequestBody JournalEntry myEntry) {
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(journalId);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName,@PathVariable ObjectId journalId) {
        journalEntryService.deleteById(journalId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{journalId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String userName,
                                                    @PathVariable ObjectId journalId,
                                                    @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.findById(journalId).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle((newEntry.getTitle() != null  && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle()));
            oldEntry.setContent((newEntry.getContent() != null  && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent()));
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


