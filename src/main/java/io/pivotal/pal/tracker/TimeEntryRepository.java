package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry any) ;

    TimeEntry find(long timeEntryId);

    List<TimeEntry> list() ;

    TimeEntry update(long eq, TimeEntry any);

    void delete(long timeEntryId) ;
}
