package com.sweteamdragon.raisedhandsserver.session.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class interactions{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String interactionEventId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Session session;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SessionParticipant sessionParticipant;

    private int timestamp;  //date

    private String text;

    private byte multimedia;

    private int flags;  //Bitmask

    private int vote;

    public interactions(String interactionEventId, Session session, SessionParticipant sessionParticipant,
                        int timestamp, String text, byte multimedia, int flags, int vote) {
        this.interactionEventId = interactionEventId;
        this.session = session;
        this.sessionParticipant = sessionParticipant;

        this.timestamp = timestamp;
        this.text = text;
        this.multimedia = multimedia;
        this.flags = flags;
        this.vote = vote;
    }
}
}