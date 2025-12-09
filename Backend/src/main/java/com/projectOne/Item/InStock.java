package com.projectOne.Item;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InStock {

    
    @JsonProperty("AVAILABLE")
    AVAILABLE,
    
    @JsonProperty("NOT-AVAILABLE")
    NOT_AVAILABLE;
}
