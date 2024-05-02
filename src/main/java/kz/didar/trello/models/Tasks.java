package kz.didar.trello.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int status;

    @ManyToOne
    private Folders folder;

    public Tasks(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
