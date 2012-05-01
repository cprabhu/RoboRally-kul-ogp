package roborally.model;

import java.util.Random;

import roborally.model.Energy.unitOfPower;

public class SurpriseBox extends Item {

    SurpriseBox() {
        super();
    }

    SurpriseBox(Position position) {
        super(position);
    }

    @Override
    public void hit() {
        for (Position neighbour : position.getNeighbours())
            for (Element elem : neighbour.getElements())
                elem.hit();
    }

    @Override
    public void use(Robot robot) {
        switch (new Random().nextInt(3)) {
        case 0:
            robot.hit();
            break;
        case 1:
            Board board = robot.getPosition().BOARD;
            long x = new Random().nextInt((int) Math.min(board.WIDTH,
                    Integer.MAX_VALUE));
            long y = new Random().nextInt((int) Math.min(board.HEIGHT,
                    Integer.MAX_VALUE));
            // TODO: wordt de robot verwijderd van de oude positie?
            board.putElement(Position.newPosition(x, y, board), robot);
            break;
        case 2:
            switch (new Random().nextInt(3)) {
            case 0:
                robot.pickup(new Battery(robot.getPosition()));
                break;
            case 1:
                robot.pickup(new RepairKit(robot.getPosition(), getWeight(),
                        new Energy(1000, unitOfPower.Ws)));
                break;
            case 2:
                robot.pickup(new SurpriseBox(robot.getPosition()));
                break;
            }
        }
        robot.drop(this);
        terminate();
    }
}