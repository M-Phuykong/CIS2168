public class Rabbit extends Animal {


    public Rabbit(Model model, int row, int column) {
        super(model, row, column);
    }
    
    int decideMove() {
		return random(Model.MIN_DIRECTION, Model.MAX_DIRECTION);
    }
}
