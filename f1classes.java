/ ==========================================
// Formula 1 Stats and Tracking App
// Beginner Version - All Classes Together
// ==========================================

import java.util.ArrayList;
import java.util.HashMap;

// ==========================================
// DRIVER CLASS
// ==========================================
class Driver {

private String name;
private String team;

public Driver(String name, String team) {
this.name = name;
this.team = team;
}

public String getName() {
return name;
}

public String getTeam() {
return team;
}

@Override
public String toString() {
return name + " - " + team;
}
}

// ==========================================
// LAP CLASS
// ==========================================
class Lap {

private int lapNumber;
private double lapTime;
private String tireType;

public Lap(int lapNumber, double lapTime, String tireType) {
this.lapNumber = lapNumber;
this.lapTime = lapTime;
this.tireType = tireType;
}

public int getLapNumber() {
return lapNumber;
}

public double getLapTime() {
return lapTime;
}

public String getTireType() {
return tireType;
}

@Override
public String toString() {
return "Lap " + lapNumber +
" | Time: " + lapTime +
" sec | Tire: " + tireType;
}
}

// ==========================================
// RACE CLASS
// ==========================================
class Race {

private String raceName;
private String date;
private ArrayList<Lap> laps;

public Race(String raceName, String date) {
this.raceName = raceName;
this.date = date;
this.laps = new ArrayList<>();
}

public void addLap(Lap lap) {
laps.add(lap);
}

public ArrayList<Lap> getLaps() {
return laps;
}

public String getRaceName() {
return raceName;
}

public String getDate() {
return date;
}
}

// ==========================================
// STATS CALCULATOR CLASS
// ==========================================
class StatsCalculator {

// Calculate average lap time
public static double calculateAverage(ArrayList<Lap> laps) {

if (laps.size() == 0) {
return 0;
}

double total = 0;

for (Lap lap : laps) {
total += lap.getLapTime();
}

return total / laps.size();
}

// Find fastest lap
public static double getBestLap(ArrayList<Lap> laps) {

if (laps.size() == 0) {
return 0;
}

double best = laps.get(0).getLapTime();

for (Lap lap : laps) {

if (lap.getLapTime() < best) {
best = lap.getLapTime();
}
}

return best;
}

// Check trend
public static String checkTrend(ArrayList<Lap> laps) {

if (laps.size() < 2) {
return "Not enough data";
}

double first = laps.get(0).getLapTime();
double last = laps.get(laps.size() - 1).getLapTime();

if (last < first) {
return "Improving";
} else if (last > first) {
return "Slowing Down";
} else {
return "Stable";
}
}
}

// ==========================================
// DATA MANAGER CLASS
// ==========================================
class DataManager {

public static ArrayList<Driver> drivers = new ArrayList<>();

public static HashMap<String, ArrayList<Race>> driverRaces =
new HashMap<>();

// Add driver
public static void addDriver(Driver driver) {

drivers.add(driver);

driverRaces.put(driver.getName(),
new ArrayList<>());
}

// Add race to driver
public static void addRace(String driverName, Race race) {

if (driverRaces.containsKey(driverName)) {

driverRaces.get(driverName).add(race);
}
}

// Get races
public static ArrayList<Race> getRaces(String driverName) {

return driverRaces.get(driverName);
}

// Display drivers
public static void displayDrivers() {

System.out.println("===== DRIVERS =====");

for (Driver driver : drivers) {
System.out.println(driver);
}
}
}

// ==========================================
// MAIN APP CLASS
// ==========================================
public class F1StatsApp {

public static void main(String[] args) {

// ==================================
// CREATE DRIVERS
// ==================================

Driver driver1 = new Driver("Max Verstappen", "Red Bull");
Driver driver2 = new Driver("Lewis Hamilton", "Ferrari");

DataManager.addDriver(driver1);
DataManager.addDriver(driver2);

// ==================================
// CREATE RACE
// ==================================

Race race1 = new Race("Monaco GP", "2026-05-07");

// Add laps
race1.addLap(new Lap(1, 78.5, "Soft"));
race1.addLap(new Lap(2, 77.9, "Soft"));
race1.addLap(new Lap(3, 77.2, "Medium"));
race1.addLap(new Lap(4, 76.8, "Medium"));

// Save race to driver
DataManager.addRace("Max Verstappen", race1);

// ==================================
// DISPLAY DATA
// ==================================

DataManager.displayDrivers();

System.out.println("\n===== RACE DATA =====");

for (Lap lap : race1.getLaps()) {
System.out.println(lap);
}

// ==================================
// CALCULATIONS
// ==================================

double average =
StatsCalculator.calculateAverage(race1.getLaps());

double bestLap =
StatsCalculator.getBestLap(race1.getLaps());

String trend =
StatsCalculator.checkTrend(race1.getLaps());

// ==================================
// DISPLAY STATS
// ==================================

System.out.println("\n===== STATISTICS =====");

System.out.println("Average Lap Time: " + average);

System.out.println("Best Lap Time: " + bestLap);

System.out.println("Trend: " + trend);
}
}

