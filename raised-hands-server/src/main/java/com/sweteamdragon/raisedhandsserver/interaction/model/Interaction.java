package com.sweteamdragon.raisedhandsserver.session.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Interaction{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long interactionId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Session session;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SessionParticipant sessionParticipant;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;  //date

    private String message;

    private int vote;

    public Interaction(String message, Session session, SessionParticipant sessionParticipant,
                        Date timestamp, int vote) {
        this.message = message;
        this.session = session;
        this.sessionParticipant = sessionParticipant;
        this.timestamp = timestamp;
        this.vote = vote;
    }
}
