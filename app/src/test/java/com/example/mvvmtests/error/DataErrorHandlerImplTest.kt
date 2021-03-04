package com.example.mvvmtests.error

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class DataErrorHandlerImplTest {

    @Test
    fun `handle IO exception`() {
        assertEquals(AppError.NoNetwork, DataErrorHandlerImpl().resolveError(IOException()))
    }

    @Test
    fun `handle other exceptions`() {
        assertEquals(AppError.Unknown, DataErrorHandlerImpl().resolveError(RuntimeException()))
    }
}