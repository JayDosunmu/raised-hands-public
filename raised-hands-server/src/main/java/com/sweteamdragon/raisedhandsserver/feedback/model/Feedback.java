package com.sweteamdragon.raisedhandsserver.session.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Feedback { //system modeling diagram

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionFeedbackId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Session session;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SessionParticipant sessionParticipant;

    private double numericalScore;

    private String customMessage;

    public Feedback(Session session, SessionParticipant sessionParticipant, double numericalScore, String customMessage) {
        this.session = session;
        this.sessionParticipant = sessionParticipant;
        this.numericalScore = numericalScore;
        this.customMessage = customMessage;
    }
}
