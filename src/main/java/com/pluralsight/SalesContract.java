package com.pluralsight;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;


    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, double processingFee, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = 0.05;
        this.recordingFee = 100;
        this.processingFee = processingFee;
        this.isFinanced = isFinanced;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinanced() {
        return isFinanced;
    }

    public void setFinanced(boolean financed) {
        isFinanced = financed;
    }

    @Override
    public double getMonthlyPayment(){
        return 0;
    }

    @Override
    public double getTotalPrice(){
        return 0;
    }
}
