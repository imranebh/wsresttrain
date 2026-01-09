package org.sid.wsrest;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bookTrain")
public class BookTrain {
    private Long id;
    private String bookNumber;
    private Train currentTrain;
    private int numberPlaces;

    public BookTrain() {
    }

    public BookTrain(Long id, String bookNumber, Train currentTrain, int numberPlaces) {
        this.id = id;
        this.bookNumber = bookNumber;
        this.currentTrain = currentTrain;
        this.numberPlaces = numberPlaces;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public Train getCurrentTrain() {
        return currentTrain;
    }

    public void setCurrentTrain(Train currentTrain) {
        this.currentTrain = currentTrain;
    }

    public int getNumberPlaces() {
        return numberPlaces;
    }

    public void setNumberPlaces(int numberPlaces) {
        this.numberPlaces = numberPlaces;
    }
}

