package org.example.storage.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransactionId implements Serializable {

    private Long id;
    private Long userId;

}
