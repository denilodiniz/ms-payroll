package br.com.ddev.hrworker.resources.exceptions;

import br.com.ddev.hrworker.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler resourceExceptionHandler;

    @Test
    @DisplayName("Response entity for resource not found")
    void resourceNotFoundException() {
        ResponseEntity<StandardError> result = resourceExceptionHandler
                .resourceNotFoundException(new ResourceNotFoundException("Custom error message from the exception triggering method."), new MockHttpServletRequest());

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals(ResponseEntity.class, result.getClass());
        assertEquals(StandardError.class, result.getBody().getClass());
        assertEquals("Resource not found.", result.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getBody().getStatus());
    }
}