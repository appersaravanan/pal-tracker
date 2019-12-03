package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    public ResponseEntity<TimeEntry> create(TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry=timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    public ResponseEntity<TimeEntry> read(long timeEntryId) {
        TimeEntry searchedTimeEntry=timeEntryRepository.find(timeEntryId);
        return new ResponseEntity<>(searchedTimeEntry, Objects.isNull(searchedTimeEntry)? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> repoList= timeEntryRepository.list();
        return new ResponseEntity<>(repoList,HttpStatus.OK);
    }

    public ResponseEntity update(long timeEntryId, TimeEntry expected) {
        TimeEntry updatedRecord=timeEntryRepository.update(timeEntryId,expected);
        return new ResponseEntity<>(updatedRecord,Objects.isNull(updatedRecord)?HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    public ResponseEntity delete(long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
