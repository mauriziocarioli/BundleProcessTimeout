package com.insurance.handler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendTaskHandler implements WorkItemHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(SendTaskHandler.class);

    public  void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        String message = (String) workItem.getParameter("Message");
        logger.debug("Sending message: {}", message);
        manager.completeWorkItem(workItem.getId(), null);
    }

    public  void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        // Do nothing, cannot be aborted
    }

}