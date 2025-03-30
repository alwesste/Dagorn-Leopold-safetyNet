package com.openclassrooms.safetyNet.interfaces;

import com.openclassrooms.safetyNet.result.FloodHabitant;

import java.io.IOException;
import java.util.List;

public interface IFloodService {
    List<FloodHabitant> getHomeByStation(List<String> stations) throws IOException;
}
