import sac.StateFunction;
import sac.State;

public class ratingFunc extends StateFunction{
    @Override
    public double calculate(State state) {
        Connect4 c4Game = (Connect4) state;

        if(c4Game.isSolution() && c4Game.isMaximizingTurnNow()){
            return Double.POSITIVE_INFINITY;
        } else if (c4Game.isSolution()) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return c4Game.myRating();
        }
    }
}
