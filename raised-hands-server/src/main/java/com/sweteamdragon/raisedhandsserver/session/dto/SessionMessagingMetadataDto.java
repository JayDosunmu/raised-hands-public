package com.sweteamdragon.raisedhandsserver.session.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SessionMessagingMetadataDto {
    private String connectUrl;
    private String topicUrl;
    private String appUrl;
}
