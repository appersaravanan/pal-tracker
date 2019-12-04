package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.DistributionSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final  Counter actionCounter;
    private final DistributionSummary timeEntrySummary;
    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }
    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }
    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable  long timeEntryId) {
        TimeEntry searchedTimeEntry=timeEntryRepository.find(timeEntryId);
        if(Objects.nonNull(searchedTimeEntry)){
            actionCounter.increment();
            return new ResponseEntity<>(searchedTimeEntry,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(searchedTimeEntry,HttpStatus.NOT_FOUND);
        }


    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        actionCounter.increment();
        List<TimeEntry> repoList= timeEntryRepository.list();
        return new ResponseEntity<>(repoList,HttpStatus.OK);
    }
    @PutMapping("/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedRecord=timeEntryRepository.update(timeEntryId,expected);
        if(Objects.nonNull(updatedRecord)){
            actionCounter.increment();
            return new ResponseEntity<>(updatedRecord,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(updatedRecord,HttpStatus.NOT_FOUND);
        }


    }
    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity delete( @PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
