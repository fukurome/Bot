package org.example.bot.botLogic;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserDataRepositoryTest {

    @Test
    void presenceOfAnAnecdote() throws IOException {
        UserDataRepository repository = new UserDataRepository();
        assertEquals(false, repository.presenceOfAnAnecdote("123", "anecdote"));
    }
}