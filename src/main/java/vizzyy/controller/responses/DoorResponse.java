package vizzyy.controller.responses;

public class DoorResponse {

    private boolean state;

    public DoorResponse(){
        this.state = false;
    }

    public DoorResponse(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
