package com.cf.cfteam.test.services.interfaces;

import com.cf.cfteam.test.transfer.payloads.TaskPayload;
import com.cf.cfteam.test.transfer.responses.TaskResponse;

public interface ITaskService {

    TaskResponse add(TaskPayload TaskPayload taskPayload)
}
