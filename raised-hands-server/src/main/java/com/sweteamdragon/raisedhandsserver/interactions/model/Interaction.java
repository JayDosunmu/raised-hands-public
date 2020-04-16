package com.sweteamdragon.raisedhandsserver.session.model;

import lombok.*;

import javax.persistence.*;

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

    private String message;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Session session;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SessionParticipant sessionParticipant;

    @CreationTimestamp
    @TeMPOraL(TemporalType.TIMESTAMP)
    private Date timestamp;  //date

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
