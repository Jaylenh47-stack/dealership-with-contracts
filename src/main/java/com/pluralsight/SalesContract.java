package com.pluralsight;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;


    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleSold);
        this.salesTax = vehicleSold.getPrice() *  0.05;
        this.recordingFee = 100;
        this.processingFee = (vehicleSold.getPrice() < 10000)? 295 : 495;
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
        if (isFinanced){
            if (this.getVehicleSold().getPrice() > 10000){
                return getVehicleSold().getPrice() * 0.0425;
            }
            else{
                return getVehicleSold().getPrice() * 0.0525;
            }
        }
        else{
            return 0;
        }
    }

    @Override
    public double getTotalPrice(){
        return this.salesTax + getVehicleSold().getPrice() + this.recordingFee + this.processingFee;


    }

    public String generateYesOrNo(){
        return  (this.isFinanced)? "YES" : "NO";
    }


    public String toEncodedString(){
        Vehicle v = this.getVehicleSold();

        return String.format("SALE|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                this.getDate(),this.getCustomerName(),this.getCustomerEmail(),v.getVin(),v.getYear(),v.getMake(),v.getModel(),
                v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice(), this.salesTax, this.recordingFee, this.processingFee,
                this.getTotalPrice(), this.generateYesOrNo(), this.getMonthlyPayment() );
    }
}
