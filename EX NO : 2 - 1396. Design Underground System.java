import java.util.*;
class UndergroundSystem {
    private HashMap<Integer, CheckIn> ck;
    private HashMap<String, Route> rt;
    public UndergroundSystem() {
        ck = new HashMap<>();
        rt = new HashMap<>();
    }
    public void checkIn(int id, String stationName, int t) {
        ck.put(id, new CheckIn(stationName, t));
    }
    public void checkOut(int id, String stationName, int t) {
        CheckIn trip = ck.get(id);
        String key = trip.station + "->" + stationName;
        Route route = rt.getOrDefault(key, new Route());
        route.totalTime += (t - trip.time);
        route.tripCount++;
        rt.put(key, route);
        ck.remove(id);
    }
    public double getAverageTime(String startStation, String endStation) {
        Route route = rt.get(startStation + "->" + endStation);
        return (double) route.totalTime / route.tripCount;
    }
    class CheckIn {
        String station;
        int time;
        CheckIn(String station, int time) {
            this.station = station;
            this.time = time;
        }
    }
    class Route {
        int totalTime = 0;
        int tripCount = 0;
    }
}
