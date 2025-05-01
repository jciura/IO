package pl.agh.edu.io.Classroom;

import java.util.List;

public record ClassroomDto(int id, String building, int number, int floor, int capacity,
                           boolean hasComputers, List<String> softwareNames) {
}
