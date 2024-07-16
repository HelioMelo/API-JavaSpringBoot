package com.rocketseat.planner.dto;

import java.util.UUID;

public record ParticipantRequestPayload(UUID id,String name, String email, Boolean isConfirmed) {

}
