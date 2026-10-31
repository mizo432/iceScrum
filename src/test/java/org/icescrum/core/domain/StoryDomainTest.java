package org.icescrum.core.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StoryDomainTest {

    @Test
    void newStory_defaultStateIsTodo() {
        Story story = new Story();

        assertEquals(Story.StoryState.TODO, story.getStoryState());
        assertEquals(0, story.getState());
    }

    @Test
    void newStory_defaultValues() {
        Story story = new Story();

        assertEquals(0, story.getRank());
        assertEquals(0, story.getEstimation());
        assertEquals(0, story.getStoryPoints());
        assertEquals(0, story.getRemainingEstimation());
        assertNull(story.getLabel());
        assertNull(story.getDescription());
    }

    @Test
    void setStoryState_updatesStateOrdinal() {
        Story story = new Story();

        story.setStoryState(Story.StoryState.IN_PROGRESS);
        assertEquals(Story.StoryState.IN_PROGRESS, story.getStoryState());
        assertEquals(1, story.getState());

        story.setStoryState(Story.StoryState.DONE);
        assertEquals(Story.StoryState.DONE, story.getStoryState());
        assertEquals(2, story.getState());

        story.setStoryState(Story.StoryState.CANCELLED);
        assertEquals(Story.StoryState.CANCELLED, story.getStoryState());
        assertEquals(3, story.getState());
    }

    @Test
    void setState_updatesStoryState() {
        Story story = new Story();
        story.setState(1);

        assertEquals(Story.StoryState.IN_PROGRESS, story.getStoryState());
    }

    @Test
    void prePersist_setsTimestamps() {
        Instant before = Instant.now();
        Story story = new Story();
        story.onPrePersist();
        Instant after = Instant.now();

        assertNotNull(story.getDateCreated());
        assertNotNull(story.getLastUpdated());
        assertTrue(story.getDateCreated().isAfter(before) || story.getDateCreated().equals(before));
        assertTrue(story.getDateCreated().isBefore(after) || story.getDateCreated().equals(after));
    }

    @Test
    void prePersist_doesNotOverwriteExistingTimestamps() {
        Instant fixedDate = Instant.parse("2024-01-01T00:00:00Z");
        Story story = new Story();
        story.setDateCreated(fixedDate);
        story.onPrePersist();

        assertEquals(fixedDate, story.getDateCreated());
    }

    @Test
    void preUpdate_updatesLastUpdated() {
        Instant before = Instant.now();
        Story story = new Story();
        story.onPreUpdate();
        Instant after = Instant.now();

        assertNotNull(story.getLastUpdated());
        assertTrue(story.getLastUpdated().isAfter(before) || story.getLastUpdated().equals(before));
        assertTrue(story.getLastUpdated().isBefore(after) || story.getLastUpdated().equals(after));
    }

    @Test
    void setters_andGetters() {
        Story story = new Story();
        story.setId(1L);
        story.setLabel("Test story");
        story.setRank(5);
        story.setEstimation(8);
        story.setStoryPoints(5);
        story.setRemainingEstimation(3);
        story.setDescription("A test story");
        story.setDueDate(LocalDate.of(2025, 12, 31));
        story.setUid("uuid-123");

        assertEquals(1L, story.getId());
        assertEquals("Test story", story.getLabel());
        assertEquals(5, story.getRank());
        assertEquals(8, story.getEstimation());
        assertEquals(5, story.getStoryPoints());
        assertEquals(3, story.getRemainingEstimation());
        assertEquals("A test story", story.getDescription());
        assertEquals(LocalDate.of(2025, 12, 31), story.getDueDate());
        assertEquals("uuid-123", story.getUid());
    }

    @Test
    void storyState_enumValues() {
        Story.StoryState[] states = Story.StoryState.values();
        assertEquals(4, states.length);
        assertEquals(Story.StoryState.TODO, states[0]);
        assertEquals(Story.StoryState.IN_PROGRESS, states[1]);
        assertEquals(Story.StoryState.DONE, states[2]);
        assertEquals(Story.StoryState.CANCELLED, states[3]);
    }
}
