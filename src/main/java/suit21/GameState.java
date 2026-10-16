package suit21;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final int round;
    private final List<PlayerState> playerStates;
    private final String action;

    public GameState(int round, List<Player> players, String action) {
        this.round = round;
        this.playerStates = new ArrayList<>();

        for (Player player : players) {
            this.playerStates.add(new PlayerState(player));
        }

        this.action = action;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Round ").append(round).append(" ===\n");

        for (PlayerState state : playerStates) {
            sb.append(state.toString()).append("\n");
        }

        if (action != null && !action.isEmpty()) {
            sb.append("Action: ").append(action).append("\n");
        }

        return sb.toString();
    }
}
