package org.ea.finance.onlinebankingapp.model.common;

import java.time.LocalDateTime;

public abstract class AuditableEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuditableEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamps() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }
}
