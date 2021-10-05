package com.example.new_application.bean;

public class AllMessageUnReadEvenEntity {
    UnReadMessageEntity unReadMessageEntity;

    public AllMessageUnReadEvenEntity(UnReadMessageEntity unReadMessageEntity) {
        this.unReadMessageEntity = unReadMessageEntity;
    }

    public UnReadMessageEntity getUnReadMessageEntity() {
        return unReadMessageEntity;
    }

    public void setUnReadMessageEntity(UnReadMessageEntity unReadMessageEntity) {
        this.unReadMessageEntity = unReadMessageEntity;
    }
}
