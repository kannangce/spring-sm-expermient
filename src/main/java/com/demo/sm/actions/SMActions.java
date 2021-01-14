package com.demo.sm.actions;

import com.demo.core.data.States;
import com.demo.core.data.StatesOnTransition;
import com.demo.core.data.Transitions;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

//@WithStateMachine
@Service
public class SMActions {

    int i = 0;

    public SMActions()
    {
        System.out.println("Initializing SM actions");
    }

//    @OnTransition(source = "S1)
    public void onTransitionT1(StateContext<States, Transitions> stateContext) {
        State<States, Transitions> state = stateContext.getStateMachine().getState();
        System.out.println("Event:"+stateContext.getEvent()+", Current State: "+ (state== null? "null" : state.getId()));
        i++;
        throw new RuntimeException("Custom Error");
    }

    public void onError(StateContext<States, Transitions> stateContext) {
        System.out.println(stateContext.getException());
        System.out.println(stateContext.getException().getMessage());

    }
}
