package com.nitesh.unique.repository;

import com.nitesh.unique.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
