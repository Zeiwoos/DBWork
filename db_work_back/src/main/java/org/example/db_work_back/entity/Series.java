package org.example.db_work_back.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seriesID;

    @Column(name = "Seriesname", nullable = false, length = 200)
    private String seriesName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    // Getters and Setters
    public Integer getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(Integer seriesID) {
        this.seriesID = seriesID;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
