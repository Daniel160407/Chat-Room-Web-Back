package org.chatroomweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "room")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "max_members")
    private Integer maxMembers;
    @Column(name = "name")
    private String name;

    public Room(Integer maxMembers, String name) {
        this.maxMembers = maxMembers;
        this.name = name;
    }
}
