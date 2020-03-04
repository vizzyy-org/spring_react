package vizzyy.domain;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Motion {

    @Lob
    @Column(name="Image", length=100000)
    private byte[] Image;

    @Id
    private String Time;

    public Motion(){}

    public Motion(byte[] Image, String Time){
        this.Image = Image;
        this.Time = Time;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] Image) {
        this.Image = Image;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
