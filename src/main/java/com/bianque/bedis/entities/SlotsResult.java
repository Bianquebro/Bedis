package com.bianque.bedis.entities;

public class SlotsResult {
    private Integer slotNum;
    private Integer slotSize;

    public SlotsResult(){}
    public SlotsResult(Integer slotNum, Integer slotSize){
        this.slotNum = slotNum;
        this.slotSize = slotSize;
    }

    public Integer getSlotNum() {
        return slotNum;
    }

    public void setSlotNum(Integer slotNum) {
        this.slotNum = slotNum;
    }

    public Integer getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(Integer slotSize) {
        this.slotSize = slotSize;
    }
}
