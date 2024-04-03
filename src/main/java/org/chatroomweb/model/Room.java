package org.chatroomweb.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "max_members")
    private Integer maxMembers;

    public Room(String name, Integer maxMembers) {
        this.name = name;
        this.maxMembers = maxMembers;
    }
}
