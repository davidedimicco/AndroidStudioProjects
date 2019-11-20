package com.davidedimicco.hypertris;

import java.util.ArrayList;

public abstract class Player {

    public abstract HyperPosition chooseAction(ArrayList<HyperPosition> availablePositions, Board currentBoard);

    public abstract void appendState(String stateHash);

    public abstract void reset();

    public abstract  void feedReward(double reward);

}
