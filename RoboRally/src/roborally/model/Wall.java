package roborally.model;

public class Wall extends Element {

    public Wall() {
        super();
    }

    public Wall(Position position) {
        super(position);
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
