package vizzyy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DoorServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    DoorService doorService;

    @Test
    public void openDoor(){
        when(restTemplate.getForObject(anyString(), any())).thenReturn("Opened");
        assertEquals(doorService.openDoor(), "Opened");
    }

    @Test
    public void closeDoor(){
        when(restTemplate.getForObject(anyString(), any())).thenReturn("Closed");
        assertEquals(doorService.closeDoor(), "Closed");
    }

}
