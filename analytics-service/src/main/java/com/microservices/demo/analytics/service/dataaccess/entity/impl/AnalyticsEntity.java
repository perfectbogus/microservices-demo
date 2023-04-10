package com.microservices.demo.analytics.service.dataaccess.entity.impl;

import com.microservices.demo.analytics.service.dataaccess.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "twitter_analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnalyticsEntity implements BaseEntity<UUID> {
    @Id
    @NotNull
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @NotNull
    @Column(name = "word")
    private String word;

    @NotNull
    @Column(name = "word_count")
    private Long wordCount;

    @NotNull
    @Column
    private LocalDateTime recordDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyticsEntity that = (AnalyticsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(word, that.word) && Objects.equals(wordCount, that.wordCount) && Objects.equals(recordDate, that.recordDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, wordCount, recordDate);
    }
}
