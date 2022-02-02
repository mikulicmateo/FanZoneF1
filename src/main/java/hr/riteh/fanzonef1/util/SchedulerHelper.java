package hr.riteh.fanzonef1.util;

import lombok.Data;

@Data
public class SchedulerHelper {

    private static int race = 1;
    private static int season = 2016;

    public static int getRace() {
        return race;
    }

    public static int getSeason() {
        return season;
    }

    public static void incrementRace() {
        race++;
    }

    public static void incrementSeason() {
        season++;
    }

    public static void resetRace() {
        race = 1;
    }
}