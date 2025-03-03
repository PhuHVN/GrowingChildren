package com.example.GrowChild.entity.respone;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long shareRecordId;

    @ManyToOne
    @JoinColumn(name = "consulting_id",nullable = false)
    public Consulting consulting;

    @ManyToOne
    @JoinColumn(name = "record_id",nullable = false)
    public HealthRecord healthRecord;

    public boolean isDelete = false;

    

}
