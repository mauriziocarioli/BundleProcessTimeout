package com.insurance.handler;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.*;

public class ReceiveTaskHandler implements WorkItemHandler {
    
    // TODO: use correlation instead of message id
    private Map<String, Long> waiting = new HashMap<String, Long>();
    private ProcessRuntime ksession;
    
    public  ReceiveTaskHandler(KieSession ksession) {
        this.ksession = ksession;
    }
    
    public  void setKnowledgeRuntime(KieSession ksession) {
    	this.ksession = ksession;
    }

    public  void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        String messageId = (String) workItem.getParameter("MessageId");
        waiting.put(messageId, workItem.getId());
    }
    
    public  void messageReceived(String messageId, Object message) {
        Long workItemId = waiting.get(messageId);
        if (workItemId == null) {
            return;
        }
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("Message", message);
        ksession.getWorkItemManager().completeWorkItem(workItemId, results);
    }

    public  void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    	String messageId = (String) workItem.getParameter("MessageId");
        waiting.remove(messageId);
    }

}