package com.example.choujiang.cj.m;

import com.example.choujiang.model.BaseInfo;

import java.io.Serializable;

/**
 * Created by gtgs on 17/10/18.
 */

public class PtReport_pt extends BaseInfo implements Serializable {
    private String cliqueNumber;
    private String groupSize;
    private String spellTogether;
    private String drivingTurnover;

    public String getCliqueNumber() {
        return cliqueNumber;
    }

    public void setCliqueNumber(String cliqueNumber) {
        this.cliqueNumber = cliqueNumber;
    }

    public String getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(String groupSize) {
        this.groupSize = groupSize;
    }

    public String getSpellTogether() {
        return spellTogether;
    }

    public void setSpellTogether(String spellTogether) {
        this.spellTogether = spellTogether;
    }

    public String getDrivingTurnover() {
        return drivingTurnover;
    }

    public void setDrivingTurnover(String drivingTurnover) {
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
