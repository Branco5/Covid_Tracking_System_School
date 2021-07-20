package Servers;

import Code.Code;
import User.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Defines collections and functions to store and manipulate school data
 */
public class HealthOrgServer {
    private List<Code> infectedList;

    public HealthOrgServer(){
        infectedList = new ArrayList<>();
    }

    /**
     * Adds codes of infected user to infectedList
     * @param u
     */
    public void addInfectedList(User u) throws Exception {
        update();

        infectedList.addAll(u.getCodesEmitted());
    }

    /**
     * Retorna lista de códigos com um timestamp inferior a 7 dias associados a um utilizador infetado.
     * @return
     */
    public List<Code> getInfectedList(){
        update();
        return infectedList;
    }

    /**
     * Removes codes with timestamp > 7 days
     */
    public void update(){
        infectedList.removeIf(code -> getDifferenceDays(code.getDate(), new Date())>7);
    }

    /**
     * Returns codes of infected in the last 24 hours
     * @return list of codes
     */
    public List<Code> getTodayInfected(){
        List<Code> codes = new ArrayList<>(this.infectedList);
        codes.removeIf(code -> getDifferenceDays(code.getDate(), new Date())>=1);
        return codes;
    }

    /**
     * Prints codes of infected in last 24 hours
     */
    public void printTodayInfected(){
        List<Code> codes = new ArrayList<>(getTodayInfected());
        codes.forEach(c -> System.out.println(c.toString()));
    }

    /**
     * Gets difference in days between 2 dates
     * @param d1
     * @param d2
     * @return long
     */
    public long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * Decrements date by milliseconds in parameter
     * For testing only
     * @param number
     */
    public void decrementDates(long number){
        for (Code code : infectedList) {
            code.decrementDate(number);
        }
    }

    /**
     * Prints timestamps of codes in infected list
     */
    public void printDates(){
        for (Code code : infectedList) {
            System.out.println(code.getDateString());
        }
    }


}
