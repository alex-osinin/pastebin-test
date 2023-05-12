package ru.aosinin.pastebin.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "paste")
public class PasteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(columnDefinition = "text")
    @NotNull
    String text;

    @NotNull
    @Column(length = 8)
    String hash;

    @NotNull
    PasteLifetime lifetime;

    @NotNull
    LocalDateTime createdTime;

    LocalDateTime expirationTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    PasteVisibility visibility;

    public boolean isPublic() {
        return visibility == PasteVisibility.PUBLIC;
    }
}
