package com.codingapi.db.controller.pojo;

import lombok.Getter;
import lombok.Setter;

public class ExampleCmd {

    @Setter
    @Getter
    public static class AddRelationshipRequest{

        private String sourceId;
        private String targetId;
        private int since;

    }

    @Setter
    @Getter
    public static class RemoveRelationShipRequest{

        private String sourceId;
        private String removeId;

    }
}
