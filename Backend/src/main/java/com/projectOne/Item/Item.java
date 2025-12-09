package com.projectOne.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "item")
public class Item {

    @Id
    private Integer itemId;

    private String itemName;

    private Double itemPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_available")
    private InStock isAvailable;
}
