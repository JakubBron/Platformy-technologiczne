import java.io.Serializable;

public class Message implements Serializable {
    private int number;
    private String content;

    Message(int number, String content){
        this.content = content;
        this.number = number;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getContent() {
        return content;
    }
}