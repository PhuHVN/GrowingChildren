package com.example.GrowChild.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Children {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long childrenId;

    @NotBlank(message = "Name children not blank!")
    String childrenName;

    @Min(value = 0, message = "Age greater than 0")
    @Max(value = 20, message = "Age lower than 20")
    int age;

    @Column(nullable = false)
    String gender;

    boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    User parentId;

    @OneToMany(mappedBy = "bookId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Booking> Booking;

}
