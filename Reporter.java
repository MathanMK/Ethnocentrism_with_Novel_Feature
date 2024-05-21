import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reporter {
    public static void writeStatsToCsvFile(String csvFilename, Simulation.RunStats[] rses) {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[] {});
        dataLines.add(new String[] {});
        dataLines.add(new String[] {});
        dataLines.add(new String[] {});
        dataLines.add(new String[] {});
        dataLines.add(new String[] {});

        dataLines.add(new String[] 
            { 
                "[run number]",
                "gain-of-receiving",
                "initial-ptr",
                "immigrants-per-day",
                "immigrant-chance-cooperate-with-same",
                "mutation-rate",
                "cost-of-giving",
                "immigrant-chance-cooperate-with-different",
                "death-rate",
                "max-pxcor",
                "max-pycor",
                "[step]",
                "mean(coopown-percent)", 
                "std(coopown-percent)",
                "mean(defother-percent)", 
                "std(defother-percent)",
                "mean(consist-ethno-percent)", 
                "std(consist-ethno-percent)",
                "mean(meetown-percent)", 
                "std(meetown-percent)",
                "mean(coop-percent)", 
                "std(coop-percent)",
                "mean(last100coopown-percent)", 
                "std(last100coopown-percent)",
                "mean(last100defother-percent)", 
				"std(last100defother-percent)",
                "mean(last100consist-ethno-percent)", 
				"std(last100consist-ethno-percent)",
                "mean(last100meetown-percent)", 
				"std(last100meetown-percent)",
                "mean(last100coop-percent)", 
				"std(last100coop-percent)",
                "mean(cc-percent)", 
				"std(cc-percent)",
                "mean(cd-percent)", 
				"std(cd-percent)",
                "mean(dc-percent)", 
				"std(dc-percent)",
                "mean(dd-percent)", 
				"std(dd-percent)",
                "count-cc", 
                "count-cd", 
                "count-dc", 
                "count-dd"
            });

        for(int i=0; i<rses.length; i++) {
            Simulation.RunStats rs = rses[i];

            dataLines.add(new String[] 
            { 
                String.valueOf(rs.runNumber),
                String.valueOf(rs.gainOfReceiving),
                String.valueOf(rs.initialPtr ),
                String.valueOf(rs.immigrantsPerDay),
                String.valueOf(rs.immigrantChanceCooperateWithSame),
                String.valueOf(rs.mutationRate),
                String.valueOf(rs.costOfGiving),
                String.valueOf(rs.immigrantChanceCooperateWithDifferent),
                String.valueOf(rs.deathRate),
                String.valueOf(rs.maxPxcor),
                String.valueOf(rs.maxPycor),
                String.valueOf(rs.step),
                String.valueOf(calculateMean(rs.sumCoopownPercent, rs.runNumber) * 10),
                String.valueOf(calculateStd(rs.sumCoopownPercent, rs.sumSqCoopownPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumDefotherPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumDefotherPercent, rs.sumSqDefotherPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumConsistEthnoPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumConsistEthnoPercent, rs.sumSqConsistEthnoPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumMeetownPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumMeetownPercent, rs.sumSqMeetownPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumCoopPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumCoopPercent, rs.sumSqCoopPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumLast100coopownPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumLast100coopownPercent, rs.sumSqLast100coopownPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumLast100defotherPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumLast100defotherPercent, rs.sumSqLast100defotherPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumLast100consistEthnoPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumLast100consistEthnoPercent, rs.sumSqLast100consistEthnoPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumLast100meetownPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumLast100meetownPercent, rs.sumSqLast100meetownPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumLast100coopPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumLast100coopPercent, rs.sumSqLast100coopPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumCcPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumCcPercent, rs.sumSqCcPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumCdPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumCdPercent, rs.sumSqCdPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumDcPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumDcPercent, rs.sumSqDcPercent, rs.runNumber)),
                String.valueOf(calculateMean(rs.sumDdPercent, rs.runNumber)* 10),
                String.valueOf(calculateStd(rs.sumDdPercent, rs.sumSqDdPercent, rs.runNumber)),
                String.valueOf(rs.countCC),
                String.valueOf(rs.countCD),
                String.valueOf(rs.countDC),
                String.valueOf(rs.countDD)
            });
        }
        
        
        File csvOutputFile = new File(csvFilename);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
            .map(Reporter::convertToCSV)
            .forEach(pw::println);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateMean(double sum, int count) {
    	return (sum / count);
	}

    private static double calculateStd(double sum, double sumSquares, int count) {
    	double mean = calculateMean(sum, count);
    	double variance = sumSquares / count - mean * mean;
    	return Math.sqrt(variance);
    }
    
	private static String convertToCSV(String[] data) {
        return Stream.of(data)
          .collect(Collectors.joining(","));
    }
}