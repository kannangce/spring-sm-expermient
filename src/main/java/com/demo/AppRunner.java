package com.demo;

import com.demo.core.data.States;
import com.demo.core.data.Transitions;
import com.demo.sm.actions.SMActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

import static com.demo.core.data.Transitions.T2;

@Service
@DependsOn("SMActions")
public class AppRunner {


    @Autowired
    private StateMachine<States, Transitions> stateMachine;

    @PostConstruct
    public void init() {
        handleAndMakeTransition();
    }

    private void handleAndMakeTransition() {
        stateMachine.start();
        State<States, Transitions> curr = stateMachine.getState();
        System.out.println(curr.getId());

        stateMachine.sendEvent(Transitions.T1);
        curr = stateMachine.getState();
        System.out.println(curr.getId());

        stateMachine.sendEvent(new GenericMessage<Transitions>(T2, Map.of("val", "4")));
        curr = stateMachine.getState();
        System.out.println(curr.getId());
    }
}
