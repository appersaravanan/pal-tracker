package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry=timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable  long timeEntryId) {
        TimeEntry searchedTimeEntry=timeEntryRepository.find(timeEntryId);
        return new ResponseEntity<>(searchedTimeEntry, Objects.isNull(searchedTimeEntry)? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> repoList= timeEntryRepository.list();
        return new ResponseEntity<>(repoList,HttpStatus.OK);
    }
    @PutMapping("/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedRecord=timeEntryRepository.update(timeEntryId,expected);
        return new ResponseEntity<>(updatedRecord,Objects.isNull(updatedRecord)?HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity delete( @PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
