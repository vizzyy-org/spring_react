package vizzyy.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import vizzyy.service.DoorService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DoorControllerTest {

    @Mock
    DoorService doorService;

    @InjectMocks
    DoorController doorController;

    @Test
    public void open(){
        when(doorService.openDoor()).thenReturn("200");
        assertEquals(doorController.open(), "200");
    }

    @Test
    public void close(){
        when(doorService.closeDoor()).thenReturn("200");
        assertEquals(doorController.close(), "200");
    }

}
