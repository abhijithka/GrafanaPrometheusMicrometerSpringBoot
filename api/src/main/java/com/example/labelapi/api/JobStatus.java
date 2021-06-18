package com.example.labelapi.api;

public enum JobStatus {

    AWAITING_FOR_SCHEDULING,
    EXECUTING,
    SUCCEEDED,
    DISCARDED,
    AWAITING_RETRY,
    ARCHIVING,
    ARCHIVED
}
