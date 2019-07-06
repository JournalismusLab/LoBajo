package com.future.lobajo;

import java.util.ArrayList;

public interface ISightseeingDB {

    public ArrayList<Sightseeing> getSightseeing( double longitute, double lattitude, double direction, double radius );
}
