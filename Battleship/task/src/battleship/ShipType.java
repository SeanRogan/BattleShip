package battleship;

public enum ShipType {

        CARRIER(5, "Aircraft Carrier"),
        BATTLESHIP(4, "Battleship"),
        SUBMARINE(3, "Submarine"),
        CRUISER(3,"Cruiser"),
        DESTROYER(2,"Destroyer");

        public final Integer size;
        public final String name;


        ShipType(Integer size, String name) {
            this.size = size;
            this.name = name;
        }

}
