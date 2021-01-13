package com.demo;

import com.demo.core.data.States;
import com.demo.core.data.Transitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
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

        stateMachine.sendEvent(Transitions.T2);
        curr = stateMachine.getState();
        System.out.println(curr.getId());

        stateMachine.sendEvent(Transitions.T1);
        curr = stateMachine.getState();
        System.out.println(curr.getId());
    }

}
