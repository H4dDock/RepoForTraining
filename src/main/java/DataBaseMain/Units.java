package DataBaseMain;

public class Units {
    private final int id;
    private final String name;
    private final String email;
    private final long money;

    public Units(int id, String name, String email, long money) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "["+id+"] "+name+", email: "+email+", cash: "+money;
    }
}
