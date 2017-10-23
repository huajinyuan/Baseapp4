package com.example.choujiang.cj.m;

import com.example.choujiang.model.BaseInfo;

import java.io.Serializable;

/**
 * Created by gtgs on 17/10/18.
 */

public class PtReport_pt extends BaseInfo implements Serializable {
    private int cliqueNumber;
    private int groupSize;
    private int spellTogether;
    private int drivingTurnover;

    public int getCliqueNumber() {
        return cliqueNumber;
    }

    public void setCliqueNumber(int cliqueNumber) {
        this.cliqueNumber = cliqueNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public int getSpellTogether() {
        return spellTogether;
    }

    public void setSpellTogether(int spellTogether) {
        this.spellTogether = spellTogether;
    }

    public int getDrivingTurnover() {
        return drivingTurnover;
    }

    public void setDrivingTurnover(int drivingTurnover) {
        this.drivingTurnover = drivingTurnover;
    }

    @Override
    public String toString() {
        return "PtReport_pt{" +
                "cliqueNumber=" + cliqueNumber +
                ", groupSize=" + groupSize +
                ", spellTogether=" + spellTogether +
                ", drivingTurnover=" + drivingTurnover +
                '}';
    }
}
