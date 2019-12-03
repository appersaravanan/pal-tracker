package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements  TimeEntryRepository {
    private Map<Long, TimeEntry> repository= new HashMap<>();

    private long id = 0l;

    public TimeEntry create(TimeEntry timeEntry) {
        ++id;
        timeEntry.setId(id);
        repository.put(timeEntry.getId() ,timeEntry);

        return timeEntry;
    }

    public TimeEntry find(long id) {
        return repository.get(id);
    }

    public List<TimeEntry> list() {
        return repository.values().stream().collect(Collectors.toList());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(repository.get(id)!=null) {
          repository.put(id, timeEntry);
            timeEntry.setId(id);
          return timeEntry;
        }
        return null;
    }

    public void delete(long id) {
        repository.remove(id);
    }
}
