import java.util.Comparator;

public class WorkstationNode {
    private String name;
    private boolean sender;

    public WorkstationNode() {
        name = null;
        sender = false;
    }

    public WorkstationNode(String name, boolean sender) {
        this.name = name;
        this.sender = sender;
    }

    public String getName() {
        return name;
    }

    public boolean isSender() {
        return sender;
    }
}
